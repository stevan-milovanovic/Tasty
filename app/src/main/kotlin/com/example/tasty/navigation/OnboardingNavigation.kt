package com.example.tasty.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tasty.ui.screen.onboarding.OnboardingScreen
import com.example.tasty.ui.screen.onboarding.OnboardingViewModel

const val ONBOARDING_ROUTE = "onboarding_route"

fun NavGraphBuilder.onboardingScreen(onContinueClick: () -> Unit) {
    composable(
        route = ONBOARDING_ROUTE
    ) {
        val viewModel: OnboardingViewModel = hiltViewModel()
        val shouldProceedToHome = viewModel.shouldProceedToHome.collectAsStateWithLifecycle()

        if (shouldProceedToHome.value) onContinueClick() else OnboardingScreen(onContinueClick)
    }
}
