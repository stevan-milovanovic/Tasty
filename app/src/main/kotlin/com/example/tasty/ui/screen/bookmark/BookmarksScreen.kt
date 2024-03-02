package com.example.tasty.ui.screen.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.recipe.RecipesList
import com.example.tasty.ui.recipe.RecipesUiState
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun BookmarksScreen(
	uiState: RecipesUiState,
	onRecipeClick: (Int) -> Unit,
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.secondaryContainer)
	) {
		Column {
			when (uiState) {
				RecipesUiState.Error -> ErrorScreen()
				RecipesUiState.Loading -> LoadingScreen()
				is RecipesUiState.Success -> {
					RecipesList(
						uiState = uiState,
						onRecipeClick = onRecipeClick
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun RecipesListPreview() {
	TastyTheme {
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
		BookmarksScreen(
			uiState = uiState,
			onRecipeClick = {}
		)
	}
}
