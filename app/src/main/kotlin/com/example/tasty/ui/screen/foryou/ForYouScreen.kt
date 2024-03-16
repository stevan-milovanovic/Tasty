package com.example.tasty.ui.screen.foryou

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.component.recipe.RecipesList
import com.example.tasty.ui.theme.TastyTheme
import com.example.tasty.ui.theme.ThemePreviews
import kotlinx.coroutines.flow.flowOf

@Composable
fun ForYouScreen(
    recipesLazyPagingItems: LazyPagingItems<Recipe>,
    onRecipeClick: (Int) -> Unit
) {
    RecipesList(
        recipesLazyPagingItems = recipesLazyPagingItems,
        onRecipeClick = onRecipeClick
    )
}

@ThemePreviews
@Composable
private fun ForYouScreenPreview() {
    val testPagingData = PagingData.from(
        listOf(
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

    TastyTheme {
        ForYouScreen(
            recipesLazyPagingItems = flowOf(testPagingData).collectAsLazyPagingItems(),
            onRecipeClick = {}
        )
    }
}
