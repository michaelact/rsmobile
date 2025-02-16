// ui/home/HomeViewModel.kt
package com.nightwolf.rsmobile.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightwolf.rsmobile.data.repository.HospitalRepository
import com.nightwolf.rsmobile.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hospitalRepository: HospitalRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadUserLocation()
            loadHospitals()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            loadUserLocation()
            loadHospitals()
        }
    }

    private suspend fun loadUserLocation() {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val location = locationRepository.getUserLocation()
            _state.update { it.copy(userCity = location?.city ?: "Unknown") }
            _state.update { it.copy(userRegionName = location?.regionName ?: "Unknown") }
            val coordinates = locationRepository.getUserCoordinates()
            _state.update { it.copy(userCoordinates = coordinates) }
            println("User location: ${location?.city}, ${location?.regionName}, ${coordinates.latitude}, ${coordinates.longitude}")
        } catch (e: Exception) {
            _state.update { it.copy(error = "Could not determine location") }
        }
    }

    private suspend fun loadHospitals() {
        try {
            println("Loading hospitals near ${state.value.userCity} and coordinates ${state.value.userCoordinates}")
            hospitalRepository.getNearbyHospitals(state.value.userCity, state.value.userCoordinates).collect { hospitals ->
                _state.update {
                    it.copy(
                        hospitals = hospitals,
                        isLoading = false
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}
