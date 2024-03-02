package com.example.tasty.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.tasty.ui.recipe.RecipesUiState
import com.example.tasty.ui.screen.bookmark.BookmarksScreen
import com.example.tasty.ui.screen.bookmark.BookmarksViewModel

const val BOOKMARKS_ROUTE = "bookmarks"

fun NavController.navigateToBookmarks(navOptions: NavOptions) =
	navigate(BOOKMARKS_ROUTE, navOptions)

fun NavGraphBuilder.bookmarksScreen(
	onRecipeClick: (Int) -> Unit
) {
	composable(
		route = BOOKMARKS_ROUTE
	) {
		val viewModel: BookmarksViewModel = hiltViewModel()
		val recipesUiState: RecipesUiState by viewModel.uiState.collectAsStateWithLifecycle()
		BookmarksScreen(
			uiState = recipesUiState,
			onRecipeClick = onRecipeClick,
		)
	}
}
