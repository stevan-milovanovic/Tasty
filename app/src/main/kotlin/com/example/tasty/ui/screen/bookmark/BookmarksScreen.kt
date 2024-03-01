package com.example.tasty.ui.screen.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tasty.ui.recipe.RecipesList
import com.example.tasty.ui.recipe.RecipesUiState
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen

@Composable
fun BookmarksScreen(
	onRecipeClick: (Int) -> Unit,
	viewModel: BookmarksViewModel = hiltViewModel(),
) {
	val recipesUiState: RecipesUiState by viewModel.uiState.collectAsStateWithLifecycle()

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.secondaryContainer)
	) {
		Column {
			when (recipesUiState) {
				RecipesUiState.Error -> ErrorScreen()
				RecipesUiState.Loading -> LoadingScreen()
				is RecipesUiState.Success -> {
					RecipesList(
						uiState = recipesUiState as RecipesUiState.Success,
						onRecipeClick = onRecipeClick
					)
				}
			}
		}
	}
}
