package com.example.tasty.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleOut
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasty.ui.screen.recipe.RecipeScreen

@VisibleForTesting
internal const val RECIPE_ID_ARG = "recipeId"

const val RECIPE_ROUTE = "recipe_route"

internal class RecipeArgs(val recipeId: Int) {
	constructor(savedStateHandle: SavedStateHandle) :
			this(checkNotNull<Int>(savedStateHandle[RECIPE_ID_ARG]))
}

fun NavController.navigateToRecipe(topicId: Int) {
	navigate("$RECIPE_ROUTE/$topicId") {
		launchSingleTop = true
	}
}

fun NavGraphBuilder.recipeScreen(
	onBackClick: () -> Unit
) {
	composable(
		route = "$RECIPE_ROUTE/{$RECIPE_ID_ARG}",
		arguments = listOf(
			navArgument(RECIPE_ID_ARG) { type = NavType.IntType },
		),
		exitTransition = { scaleOutOfContainer() },
		popExitTransition = { scaleOutOfContainer() }
	) {
		RecipeScreen(onBackClick = onBackClick)
	}
}

private fun scaleOutOfContainer(): ExitTransition = scaleOut(animationSpec = spring())
