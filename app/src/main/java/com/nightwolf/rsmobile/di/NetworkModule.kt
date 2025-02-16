package com.nightwolf.rsmobile.di

import android.content.Context
import androidx.room.Room
import com.nightwolf.rsmobile.data.local.AppDatabase
import com.nightwolf.rsmobile.data.network.GeocodingApi
import com.nightwolf.rsmobile.data.network.HospitalApi
import com.nightwolf.rsmobile.data.network.LocationApi
import com.nightwolf.rsmobile.data.repository.HospitalRepository
import com.nightwolf.rsmobile.data.repository.LocationRepository
import dagger.Module
import dagger.Provides
import  dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLocationApi(): LocationApi {
        return Retrofit.Builder()
            .baseUrl(LocationApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHospitalApi(): HospitalApi {
        return Retrofit.Builder()
            .baseUrl(HospitalApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HospitalApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationApi: LocationApi
    ): LocationRepository {
        return LocationRepository(locationApi)
    }

    @Provides
    @Singleton
    fun provideHospitalRepository(
        hospitalApi: HospitalApi,
        geocodingApi: GeocodingApi,
        database: AppDatabase
    ): HospitalRepository {
        return HospitalRepository(hospitalApi, geocodingApi, database)
    }

    @Provides
    @Singleton
    fun provideGeocodingApi(): GeocodingApi {
        return Retrofit.Builder()
            .baseUrl(GeocodingApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nightwolf_hospital"
        ).build()
    }
}
