package com.nightwolf.rsmobile.data.repository

import com.nightwolf.rsmobile.data.network.LocationApi
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationApi: LocationApi
) {
    suspend fun getUserLocation(): String {
        return try {
            val response = locationApi.getUserLocation()
            "${response.city}, ${response.regionName}"
        } catch (e: Exception) {
            "Unknown Location"
        }
    }
}
