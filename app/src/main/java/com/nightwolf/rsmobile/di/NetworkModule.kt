package com.nightwolf.rsmobile.di

import com.nightwolf.rsmobile.data.network.LocationApi
import com.nightwolf.rsmobile.data.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideLocationRepository(
        locationApi: LocationApi
    ): LocationRepository {
        return LocationRepository(locationApi)
    }
}
