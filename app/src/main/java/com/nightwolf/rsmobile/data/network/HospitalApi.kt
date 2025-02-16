package com.nightwolf.rsmobile.data.network

import com.nightwolf.rsmobile.data.model.Hospital
import retrofit2.http.GET

interface HospitalApi {
    @GET("https://dekontaminasi.com/api/id/covid19/hospitals")
    suspend fun getHospitals(): List<Hospital>

    companion object {
        const val BASE_URL = "https://dekontaminasi.com/api/id/covid19/"
    }
}
