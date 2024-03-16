package com.example.tasty.ui.component.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.theme.TastyTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun RecipesList(
    recipesLazyPagingItems: LazyPagingItems<Recipe>,
    onRecipeClick: (Int) -> Unit,
) {
    val state = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(
            count = recipesLazyPagingItems.itemCount,
            key = recipesLazyPagingItems.itemKey { it.id },
            contentType = recipesLazyPagingItems.itemContentType { "Recipes" }
        ) { index: Int ->
            val recipe = recipesLazyPagingItems[index]

            if (recipe != null) {
                RecipeCard(
                    recipe = recipe,
                    onRecipeClick = onRecipeClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipesListPreview() {
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
        RecipesList(
            recipesLazyPagingItems = flowOf(testPagingData).collectAsLazyPagingItems(),
            onRecipeClick = {}
        )
    }
}
