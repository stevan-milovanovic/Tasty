package com.example.tasty.ui.screen.foryou

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import com.example.tasty.ui.component.recipe.TagWithRecipesList
import com.example.tasty.ui.theme.TastyTheme
import com.example.tasty.ui.theme.ThemePreviews
import kotlinx.coroutines.flow.flowOf

@Composable
fun ForYouScreen(
    tags: List<Tag>,
    activeTag: Tag?,
    tagWithRecipesLazyPagingItems: LazyPagingItems<TagWithRecipes>,
    onRecipeClick: (Int) -> Unit,
    onTagSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TagsFlowRow(
            tags = tags,
            activeTag = activeTag,
            onTagSelected = onTagSelected
        )
        TagWithRecipesList(
            tagWithRecipesLazyPagingItems = tagWithRecipesLazyPagingItems,
            onRecipeClick = onRecipeClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagsFlowRow(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    activeTag: Tag?,
    onTagSelected: (Int) -> Unit
) {
    FlowRow(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tags.forEach { tag ->
            Text(
                text = tag.displayName,
                modifier = Modifier
                    .background(
                        color = if (activeTag == tag) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme
                            .secondaryContainer,
                        shape = CircleShape
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onTagSelected(tag.tagId)
                    },
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ForYouScreenPreview() {
    val tags = listOf(
        Tag(1, "Serbian", "serbian"),
        Tag(2, "Fast", "fast"),
        Tag(3, "Trendy", "trendy"),
    )
    val testPagingData = PagingData.from(
        listOf(
            TagWithRecipes(
                tag = tags.first(),
                recipes = listOf(
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
        )
    )

    TastyTheme {
        ForYouScreen(
            tags = tags,
            activeTag = tags.first(),
            tagWithRecipesLazyPagingItems = flowOf(testPagingData).collectAsLazyPagingItems(),
            onRecipeClick = {},
            onTagSelected = {}
        )
    }
}
