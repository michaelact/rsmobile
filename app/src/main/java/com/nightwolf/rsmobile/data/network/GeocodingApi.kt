package com.nightwolf.rsmobile.data.network

import com.nightwolf.rsmobile.data.model.GeocodingResponse
import com.nightwolf.rsmobile.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeocodingApi {
    @GET("{query}.json")
    suspend fun getCoordinates(
        @Path("query") query: String,
        @Query("types") types: String = "poi",
        @Query("country") country: String = "id",
        @Query("limit") limit: Int = 1,
        @Query("key") key: String = BuildConfig.MAPTILER_API_KEY
    ): GeocodingResponse

    companion object {
        const val BASE_URL = "https://api.maptiler.com/geocoding/"
    }
}