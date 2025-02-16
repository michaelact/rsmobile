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
            .map { hospital ->
                hospital.copy(
                    distance = hospital.coordinates?.let { coords ->
                        calculateDistance(
                            userCoordinates.latitude,
                            userCoordinates.longitude,
                            coords.latitude,
                            coords.longitude
                        )
                    }
                )
            }
            .sortedWith(compareBy(
                // Sort by coordinates existence
                { it.coordinates == null },
                // Sort by calculated distance
                { it.distance ?: Double.MAX_VALUE }
            ))

        emit(sortedHospitals)
    }.catch { e ->
        emit(emptyList())
    }
}

// calculateDistance function
private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371 // Radius of the earth in km
    val dLat = deg2rad(lat2 - lat1)
    val dLon = deg2rad(lon2 - lon1)
    val a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return R * c
}

private fun deg2rad(deg: Double): Double {
    return deg * (Math.PI / 180)
}
