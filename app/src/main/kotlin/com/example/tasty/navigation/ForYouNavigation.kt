package com.example.tasty.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tasty.ui.screen.foryou.ForYouScreen
import com.example.tasty.ui.screen.foryou.ForYouViewModel

const val FOR_YOU_ROUTE = "for_you_route"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(onRecipeClick: (Int) -> Unit) {
	composable(
		route = FOR_YOU_ROUTE
	) {
		val viewModel: ForYouViewModel = hiltViewModel()
        val recipesLazyPagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

		ForYouScreen(
            recipesLazyPagingItems = recipesLazyPagingItems,
			onRecipeClick = onRecipeClick
		)
	}
}
