package com.example.tasty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import com.example.tasty.ui.screen.foryou.ForYouScreen
import com.example.tasty.ui.screen.foryou.ForYouViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

private const val TAG_ID_ARGUMENT = "tagId"
private const val FOR_YOU_ROUTE = "for_you_route"
private const val FOR_YOU_ROUTE_WITH_TAG_ID = "$FOR_YOU_ROUTE/$TAG_ID_ARGUMENT"

fun NavController.navigateToForYou(navOptions: NavOptions, tagId: Int?) = navigate(
    if (tagId != null) FOR_YOU_ROUTE_WITH_TAG_ID.replace(TAG_ID_ARGUMENT, tagId.toString()) else FOR_YOU_ROUTE,
    navOptions
)

fun NavGraphBuilder.forYouScreen(onRecipeClick: (Int) -> Unit) {
    composable(
        route = FOR_YOU_ROUTE
    ) {
        val viewModel = hiltViewModel<ForYouViewModel>()

        ForYouComposableScreen(
            tagsFlow = viewModel.tagsFlow,
            tagStateFlow = viewModel.tagStateFlow,
            tagWithRecipesPagingFlow = viewModel.pagingDataFlow,
            onRecipeClick = onRecipeClick,
            updateActiveTag = viewModel::updateActiveTag
        )
    }
}

fun NavGraphBuilder.forYouScreenWithTagId(onRecipeClick: (Int) -> Unit) {
    composable(
        route = "$FOR_YOU_ROUTE/{$TAG_ID_ARGUMENT}"
    ) { backStackEntry ->
        val tagId = backStackEntry.arguments?.getString(TAG_ID_ARGUMENT)?.toInt()
        val viewModel = hiltViewModel<ForYouViewModel>().apply { if (tagId != null) setInitialActiveTag(tagId) }

        ForYouComposableScreen(
            tagsFlow = viewModel.tagsFlow,
            tagStateFlow = viewModel.tagStateFlow,
            tagWithRecipesPagingFlow = viewModel.pagingDataFlow,
            onRecipeClick = onRecipeClick,
            updateActiveTag = viewModel::updateActiveTag
        )
    }
}

@Composable
private fun ForYouComposableScreen(
    tagsFlow: Flow<List<Tag>>,
    tagStateFlow: StateFlow<Tag?>,
    tagWithRecipesPagingFlow: Flow<PagingData<TagWithRecipes>>,
    onRecipeClick: (Int) -> Unit,
    updateActiveTag: (Int) -> Unit
) {
    val tags by tagsFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    val activeTag by tagStateFlow.collectAsStateWithLifecycle()
    val tagWithRecipesLazyPagingItems = tagWithRecipesPagingFlow.collectAsLazyPagingItems()

    ForYouScreen(
        tags = tags,
        activeTag = activeTag,
        tagWithRecipesLazyPagingItems = tagWithRecipesLazyPagingItems,
        onRecipeClick = onRecipeClick,
        onTagSelected = updateActiveTag
    )
}
