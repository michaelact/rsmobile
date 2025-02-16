package com.nightwolf.rsmobile.data.repository

import com.nightwolf.rsmobile.data.model.Hospital
import com.nightwolf.rsmobile.data.network.HospitalApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HospitalRepository @Inject constructor(
    private val hospitalApi: HospitalApi
) {
    fun getNearbyHospitals(): Flow<List<Hospital>> = flow {
        emit(hospitalApi.getHospitals().map { hospital ->
            Hospital(
                name = hospital.name.orEmpty(),
                address = hospital.address.orEmpty(),
                region = hospital.region.orEmpty(),
                phone = hospital.phone.orEmpty(),
                province = hospital.province.orEmpty()
            )
        })
    }.catch { e ->
        emit(emptyList())
    }
}
