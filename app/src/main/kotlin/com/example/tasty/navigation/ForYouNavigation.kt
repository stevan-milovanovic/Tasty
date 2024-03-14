package com.example.tasty.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.tasty.ui.component.recipe.RecipesUiState
import com.example.tasty.ui.screen.foryou.ForYouScreen
import com.example.tasty.ui.screen.foryou.ForYouViewModel

const val FOR_YOU_ROUTE = "for_you_route"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(onRecipeClick: (Int) -> Unit) {
	composable(
		route = FOR_YOU_ROUTE
	) {
		val viewModel: ForYouViewModel = hiltViewModel()
		val uiState: RecipesUiState by viewModel.uiState.collectAsStateWithLifecycle()

		ForYouScreen(
			uiState = uiState,
			onRecipeClick = onRecipeClick
		)
	}
}
