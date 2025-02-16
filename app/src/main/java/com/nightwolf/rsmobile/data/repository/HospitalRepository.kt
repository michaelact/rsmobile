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

class HospitalRepository @Inject constructor(
    private val hospitalApi: HospitalApi,
    private val geocodingApi: GeocodingApi,
    private val database: AppDatabase
) {
    fun getNearbyHospitals(): Flow<List<Hospital>> = flow {
        // Try to get from local storage first
        val localHospitals = database.hospitalDao().getAllHospitals()
        if (localHospitals.isNotEmpty()) {
            emit(localHospitals)
            return@flow
        }

        // Fetch from API and geocode
        val hospitals = hospitalApi.getHospitals().map { hospital ->
            val coordinates = try {
                val response = geocodingApi.getCoordinates(hospital.name).also { response ->
                    println("Geocoding response for ${hospital.name}: $response")
                }

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
        database.hospitalDao().insertAll(hospitals)
        emit(hospitals)
    }.catch { e ->
        emit(emptyList())
    }
}
