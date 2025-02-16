// ui/home/HomeScreen.kt
package com.nightwolf.rsmobile.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nightwolf.rsmobile.data.model.Hospital
import com.nightwolf.rsmobile.ui.components.ErrorView
import com.nightwolf.rsmobile.ui.components.LoadingIndicator
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hospitals Near You") },
                actions = {
                    IconButton(onClick = { viewModel.loadHospitals() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingIndicator()
            state.error != null -> ErrorView(
                message = state.error ?: "Unknown error occurred",
                onRetry = { viewModel.loadHospitals() }
            )
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    item {
                        WelcomeSection(
                            date = state.currentDate.format(
                                DateTimeFormatter.ofPattern("EEEE, MMMM d")
                            ),
                            location = state.userLocation
                        )
                    }

                    items(state.hospitals) { hospital ->
                        HospitalCard(
                            hospital = hospital,
                            onAddressClick = {
                                val encodedAddress = Uri.encode(hospital.address)
                                val uri = "https://www.google.com/maps/place/$encodedAddress"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeSection(
    date: String,
    location: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "How is your health today?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun HospitalCard(
    hospital: Hospital,
    onAddressClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = hospital.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = onAddressClick,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(text = hospital.address)
            }

            Text(
                text = hospital.phone,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            hospital.distance?.let { distance ->
                Text(
                    text = "%.1f km away".format(distance / 1000),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
