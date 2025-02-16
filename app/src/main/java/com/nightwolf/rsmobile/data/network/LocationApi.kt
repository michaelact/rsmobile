package com.nightwolf.rsmobile.data.network

import retrofit2.http.GET

data class IpApiResponse(
    val status: String,
    val country: String,
    val city: String,
    val regionName: String,
    val lat: Double,
    val lon: Double
)

interface LocationApi {
    @GET("http://ip-api.com/json")
    suspend fun getUserLocation(): IpApiResponse

    companion object {
        const val BASE_URL = "http://ip-api.com/"
    }
}
