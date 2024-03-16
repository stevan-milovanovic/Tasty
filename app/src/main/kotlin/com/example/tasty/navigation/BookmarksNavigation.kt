package com.example.tasty.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
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
        val recipesLazyPagingItems = viewModel.recipesPagingDataFlow.collectAsLazyPagingItems()
        BookmarksScreen(
            recipesLazyPagingItems = recipesLazyPagingItems,
            onRecipeClick = onRecipeClick,
        )
    }
}
