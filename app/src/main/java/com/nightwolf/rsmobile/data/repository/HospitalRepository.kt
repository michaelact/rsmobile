// data/repository/HospitalRepository.kt
package com.nightwolf.rsmobile.data.repository

//import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.nightwolf.rsmobile.data.model.Coordinates
import com.nightwolf.rsmobile.data.dummy.DummyData
import com.nightwolf.rsmobile.data.model.Hospital
//import com.nightwolf.rsmobile.data.network.HospitalApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HospitalRepository @Inject constructor(
//    private val api: HospitalApi,
//    private val context: Context
) {
//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getNearbyHospitals(): Flow<List<Hospital>> = flow {
        // Simulate network delay
        delay(1000)
        emit(DummyData.hospitals)
//        try {
//            val hospitals = api.getHospitals()
//            val currentLocation = getCurrentLocation()
//
//            val hospitalsWithDistance = hospitals.map { hospital ->
//                // Get coordinates for hospital if not cached
//                if (hospital.coordinates == null) {
//                    val geocoding = api.getHospitalLocation(hospital.name)
//                    if (geocoding.features.isNotEmpty()) {
//                        val coords = geocoding.features[0].geometry.coordinates
//                        hospital.coordinates = Coordinates(coords[1], coords[0])
//                    }
//                }
//
//                // Calculate distance
//                hospital.coordinates?.let {
//                    val hospitalLocation = Location("").apply {
//                        latitude = it.latitude
//                        longitude = it.longitude
//                    }
//                    hospital.distance = currentLocation?.distanceTo(hospitalLocation)?.toDouble()
//                }
//
//                hospital
//            }.sortedBy { it.distance }
//
//            emit(hospitalsWithDistance)
//        } catch (e: Exception) {
//            emit(emptyList())
//        }
    }

//    private suspend fun getCurrentLocation(): Location? {
//        return try {
//            // Check for location permissions first
//            if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED &&
//                context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED
//            ) {
//                return null
//            }
//
//            fusedLocationClient.lastLocation.await()
//        } catch (e: SecurityException) {
//            // Handle permission denial
//            null
//        } catch (e: Exception) {
//            null
//        }
//    }
}
