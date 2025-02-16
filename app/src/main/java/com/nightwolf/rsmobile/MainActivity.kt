// MainActivity.kt
package com.nightwolf.rsmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nightwolf.rsmobile.ui.home.HomeScreen
import com.nightwolf.rsmobile.ui.theme.RsmobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RsmobileTheme {
                HomeScreen()
            }
        }
    }
}
