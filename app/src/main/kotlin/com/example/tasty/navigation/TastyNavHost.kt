package com.example.tasty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.tasty.ui.TastyAppState

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun TastyNavHost(
    appState: TastyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = ONBOARDING_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        onboardingScreen { appState.navigateToTopLevelDestination(TopLevelDestination.FOR_YOU) }
        forYouScreen(
            onRecipeClick = navController::navigateToRecipe
        )
        bookmarksScreen(
            onRecipeClick = navController::navigateToRecipe
        )
        recipeScreen(
            onBackClick = navController::popBackStack
        )
    }
}
