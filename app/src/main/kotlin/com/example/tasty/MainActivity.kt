package com.example.tasty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tasty.data.network.NetworkMonitor
import com.example.tasty.navigation.ONBOARDING_ROUTE
import com.example.tasty.ui.TastyApp
import com.example.tasty.ui.theme.TastyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TastyTheme {
                TastyApp(
                    networkMonitor = networkMonitor,
                    startDestination = ONBOARDING_ROUTE
                )
            }
        }
    }
}
