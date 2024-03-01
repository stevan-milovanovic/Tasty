package com.example.tasty.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tasty.ui.screen.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "onboarding_route"

fun NavGraphBuilder.onboardingScreen(onContinueClick: () -> Unit) {
	composable(
		route = ONBOARDING_ROUTE
	) {
		OnboardingScreen(onContinueClick)
	}
}
