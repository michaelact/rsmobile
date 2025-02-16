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
        loadUserLocation()
        loadHospitals()
    }

    private fun loadUserLocation() {
        viewModelScope.launch {
            try {
                val location = locationRepository.getUserLocation()
                _state.update { it.copy(userLocation = location) }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Could not determine location") }
            }
        }
    }

    fun loadHospitals() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                hospitalRepository.getNearbyHospitals().collect { hospitals ->
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
}
