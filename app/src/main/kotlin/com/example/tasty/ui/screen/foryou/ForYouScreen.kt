package com.example.tasty.ui.screen.foryou

import androidx.compose.runtime.Composable
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.recipe.RecipesList
import com.example.tasty.ui.recipe.RecipesUiState
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen
import com.example.tasty.ui.theme.TastyTheme
import com.example.tasty.ui.theme.ThemePreviews

@Composable
fun ForYouScreen(
	uiState: RecipesUiState,
	onRecipeClick: (Int) -> Unit
) {
	when (uiState) {
		is RecipesUiState.Error -> ErrorScreen()
		is RecipesUiState.Loading -> LoadingScreen()
		is RecipesUiState.Success -> {
			RecipesList(
				uiState = uiState,
				onRecipeClick = onRecipeClick
			)
		}
	}
}

@ThemePreviews
@Composable
private fun ForYouScreenPreview() {
	val uiState = RecipesUiState.Success(
		recipes = listOf(
			Recipe(
				1,
				"Tasty Recipe",
				"Delicious meal",
				"test",
				"",
				"",
				"Under 30 minutes",
				listOf()
			),
			Recipe(
				2,
				"Tasty Recipe",
				"Delicious meal",
				"test",
				"",
				"",
				"Under 30 minutes",
				listOf()
			)
		)
	)

	TastyTheme {
		ForYouScreen(
			uiState = uiState,
			onRecipeClick = {}
		)
	}
}
