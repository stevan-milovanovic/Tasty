package com.example.tasty.ui.screen.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tasty.R
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen
import com.example.tasty.ui.theme.TastyIcons.ArrowBack
import com.example.tasty.ui.theme.TastyIcons.Bookmark
import com.example.tasty.ui.theme.TastyIcons.BookmarkBorder
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun RecipeScreen(
	onBackClick: () -> Unit,
	viewModel: RecipeViewModel = hiltViewModel()
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	when (uiState) {
		RecipeUiState.Error -> ErrorScreen()
		RecipeUiState.Loading -> LoadingScreen()
		is RecipeUiState.Success -> {
			val successUiState = (uiState as RecipeUiState.Success)

			RecipeScreenContent(
				recipe = successUiState.recipe,
				isRecipeBookmarked = successUiState.isRecipeBookmarked,
				onBackClick = onBackClick,
				onBookmarkClick = viewModel::updateBookmarkedList
			)
		}
	}
}

@Composable
private fun RecipeScreenContent(
	recipe: Recipe,
	isRecipeBookmarked: Boolean,
	onBackClick: () -> Unit,
	onBookmarkClick: (Int, Boolean) -> Unit,
) {
	Scaffold(
		topBar = {
			RecipeDetailsTopBar(
				isRecipeBookmarked = isRecipeBookmarked,
				onBackClick = onBackClick,
				onBookmarkClick = { onBookmarkClick(recipe.id, it) }
			)
		}
	) { contentPadding ->
		Column(
			modifier = Modifier
				.padding(contentPadding)
		) {
			RecipeTutorial(recipe)
			Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
				Text(
					text = recipe.name,
					modifier = Modifier
						.padding(vertical = 8.dp)
						.padding(horizontal = 16.dp)
						.fillMaxWidth(),
					style = MaterialTheme.typography.titleLarge
				)
				Text(
					text = recipe.description,
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 16.dp)
						.padding(bottom = 8.dp),
					style = MaterialTheme.typography.bodySmall
				)
				RecipeKeywords(
					keywords = recipe.keywords,
					modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
				)
				RecipeInstructions(
					instructions = recipe.instructions,
					modifier = Modifier.padding(horizontal = 16.dp)
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeDetailsTopBar(
	isRecipeBookmarked: Boolean,
	onBackClick: () -> Unit,
	onBookmarkClick: (Boolean) -> Unit
) {
	TopAppBar(
		title = {
			Text(
				text = stringResource(id = R.string.recipe_details),
				maxLines = 1
			)
		},
		navigationIcon = {
			IconButton(onClick = onBackClick) {
				Icon(
					ArrowBack,
					stringResource(id = R.string.back),
				)
			}
		},
		actions = {
			IconButton(onClick = { onBookmarkClick(!isRecipeBookmarked) }) {
				Icon(
					imageVector = if (isRecipeBookmarked) Bookmark else BookmarkBorder,
					contentDescription = stringResource(R.string.bookmark_recipe)
				)
			}
		},
	)
}

@Preview
@Composable
fun RecipeDetailsTopBarPreview() {
	TastyTheme {
		RecipeDetailsTopBar(false, {}) {}
	}
}
