// ui/home/HomeScreenState.kt
package com.nightwolf.rsmobile.ui.home

import com.nightwolf.rsmobile.data.model.Hospital
import java.time.LocalDate

data class HomeScreenState(
    val hospitals: List<Hospital> = emptyList(),
    val currentDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userLocation: String = "Unknown Location"
)
