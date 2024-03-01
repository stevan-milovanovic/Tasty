package com.example.tasty.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.tasty.ui.screen.foryou.ForYouScreen

const val FOR_YOU_ROUTE = "for_you_route"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(onRecipeClick: (Int) -> Unit) {
	composable(
		route = FOR_YOU_ROUTE
	) {
		ForYouScreen(onRecipeClick)
	}
}
