package com.example.tasty.ui.screen.foryou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tasty.ui.recipe.RecipesList
import com.example.tasty.ui.recipe.RecipesUiState
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen

@Composable
fun ForYouScreen(
	onRecipeClick: (Int) -> Unit,
	viewModel: ForYouViewModel = hiltViewModel(),
) {
	val uiState: RecipesUiState by viewModel.uiState.collectAsStateWithLifecycle()

	when (uiState) {
		is RecipesUiState.Error -> ErrorScreen()
		is RecipesUiState.Loading -> LoadingScreen()
		is RecipesUiState.Success -> {
			RecipesList(
				uiState = uiState as RecipesUiState.Success,
				onRecipeClick = onRecipeClick
			)
		}
	}
}
