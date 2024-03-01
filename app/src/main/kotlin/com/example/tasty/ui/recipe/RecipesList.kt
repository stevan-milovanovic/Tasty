package com.example.tasty.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun RecipesList(
	uiState: RecipesUiState.Success,
	onRecipeClick: (Int) -> Unit,
) {
	val state = rememberLazyGridState()
	val recipes = uiState.recipes
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		state = state,
		horizontalArrangement = Arrangement.Center,
	) {
		items(recipes) { recipe ->
			RecipeCard(
				recipe = recipe,
				onRecipeClick = onRecipeClick
			)
		}
	}
}

@Preview
@Composable
private fun RecipesListPreview() {
	TastyTheme {
		RecipesList(
			RecipesUiState.Success(
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
			),
			onRecipeClick = {}
		)
	}
}
