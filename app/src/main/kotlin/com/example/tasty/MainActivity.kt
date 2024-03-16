package com.example.tasty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tasty.data.network.NetworkMonitor
import com.example.tasty.ui.TastyApp
import com.example.tasty.ui.screen.common.LoadingScreen
import com.example.tasty.ui.theme.TastyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TastyTheme {
                val destination by mainActivityViewModel.initialDestination.collectAsStateWithLifecycle()

                destination?.let {
                    TastyApp(
                        networkMonitor = networkMonitor,
                        startDestination = it
                    )
                    return@TastyTheme
                }

                LoadingScreen()
            }
        }
    }
}
