package com.example.tasty.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.tasty.ui.screen.bookmark.BookmarksScreen

const val BOOKMARKS_ROUTE = "bookmarks"

fun NavController.navigateToBookmarks(navOptions: NavOptions) =
	navigate(BOOKMARKS_ROUTE, navOptions)

fun NavGraphBuilder.bookmarksScreen(
	onRecipeClick: (Int) -> Unit
) {
	composable(
		route = BOOKMARKS_ROUTE
	) {
		BookmarksScreen(
			onRecipeClick
		)
	}
}
