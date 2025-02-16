package com.nightwolf.rsmobile.data.repository

import android.location.Location
import com.nightwolf.rsmobile.data.model.Coordinates
import com.nightwolf.rsmobile.data.network.IpApiResponse
import com.nightwolf.rsmobile.data.network.LocationApi
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationApi: LocationApi
) {
    suspend fun getUserLocation(): IpApiResponse? {
        return try {
            locationApi.getUserLocation()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserCoordinates(): Coordinates {
        return try {
            val response = locationApi.getUserLocation()
            Coordinates(response.lat, response.lon)
        } catch (e: Exception) {
            Coordinates(0.0, 0.0)
        }
    }
}
