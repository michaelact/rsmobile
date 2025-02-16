package com.nightwolf.rsmobile.data.repository

import com.nightwolf.rsmobile.data.local.AppDatabase
import com.nightwolf.rsmobile.data.model.Coordinates
import com.nightwolf.rsmobile.data.model.Hospital
import com.nightwolf.rsmobile.data.network.GeocodingApi
import com.nightwolf.rsmobile.data.network.HospitalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.abs

class HospitalRepository @Inject constructor(
    private val hospitalApi: HospitalApi,
    private val geocodingApi: GeocodingApi,
    private val database: AppDatabase
) {
    fun getNearbyHospitals(userCity: String, userCoordinates: Coordinates): Flow<List<Hospital>> = flow {
        // Try to get from local storage first
        val localHospitals = database.hospitalDao().getAllHospitals()
        val hospitals = if (localHospitals.isNotEmpty()) {
            localHospitals
        } else {
            // Fetch from API and geocode
            val fetchedHospitals = hospitalApi.getHospitals().map { hospital ->
                val coordinates = try {
                    val response = geocodingApi.getCoordinates(hospital.name)
                    println("Geocoding response for ${hospital.name}: $response")
                    response.features.firstOrNull()?.let {
                        Coordinates(
                            latitude = it.geometry.coordinates[1],
                            longitude = it.geometry.coordinates[0]
                        )
                    }
                } catch (e: Exception) {
                    println("Geocoding failed for ${hospital.name}: ${e.message}")
                    null
                }

                Hospital(
                    name = hospital.name.orEmpty(),
                    address = hospital.address.orEmpty(),
                    region = hospital.region.orEmpty(),
                    phone = hospital.phone.orEmpty(),
                    province = hospital.province.orEmpty(),
                    coordinates = coordinates
                )
            }
            // Save to local storage
            database.hospitalDao().insertAll(fetchedHospitals)
            fetchedHospitals
        }

        // Filter and sort hospitals
        val sortedHospitals = hospitals
            .filter { hospital ->
                hospital.region.contains(userCity, ignoreCase = true).also {
                    println("Filtering hospital ${hospital.name} with region: ${hospital.region}, matches user city $userCity")
                }
            }
            .sortedWith(compareBy(
                // Sort by coordinates existence (hospitals with coordinates first)
                { it.coordinates == null },
                // Sort by distance to user
                { it.coordinates?.let { coordinates ->
                    abs(coordinates.latitude - userCoordinates.latitude) + Math.abs(coordinates.longitude - userCoordinates.longitude)
                } ?: Double.MAX_VALUE },
            ))

        emit(sortedHospitals)
    }.catch { e ->
        emit(emptyList())
    }
}
