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
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import com.example.tasty.ui.theme.TastyTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun TagWithRecipesList(
    tagWithRecipesLazyPagingItems: LazyPagingItems<TagWithRecipes>,
    onRecipeClick: (Int) -> Unit,
) {
    val state = rememberLazyGridState()
    val items = tagWithRecipesLazyPagingItems.itemSnapshotList.items
    if (items.isEmpty()) return
    val recipes = items.flatMap { it.recipes }
    val tag = if (items.size == 1) items.first().tag else null
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(
            count = recipes.size,
            contentType = { "RecipeWithSingleTag" },
        ) { index: Int ->
            val recipe = recipes[index]
            RecipeCard(
                recipe = recipe,
                tag = tag,
                onRecipeClick = onRecipeClick
            )
        }
    }
}

@Preview
@Composable
private fun RecipesWithTagListPreview() {
    val tags = listOf(
        Tag(1, "Serbian", "serbian"),
        Tag(2, "Hungarian", "hungarian"),
    )
    val recipes = listOf(
        Recipe(
            1,
            "Tasty Serbian Recipe",
            "Delicious meal",
            "test",
            "",
            "",
            "Under 30 minutes",
            listOf()
        ),
        Recipe(
            2,
            "Tasty Hungarian Recipe",
            "Delicious meal",
            "test",
            "",
            "",
            "Under 30 minutes",
            listOf()
        )
    )
    val testPagingData = PagingData.from(
        listOf(
            TagWithRecipes(tags.first(), listOf(recipes.first())),
            TagWithRecipes(tags.last(), listOf(recipes.last()))
        )
    )
    TastyTheme {
        TagWithRecipesList(
            tagWithRecipesLazyPagingItems = flowOf(testPagingData).collectAsLazyPagingItems(),
            onRecipeClick = {}
        )
    }
}