// ui/home/HomeViewModel.kt
package com.nightwolf.rsmobile.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightwolf.rsmobile.data.repository.HospitalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HospitalRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    init {
        loadHospitals()
    }

    fun loadHospitals() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getNearbyHospitals().collect { hospitals ->
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
