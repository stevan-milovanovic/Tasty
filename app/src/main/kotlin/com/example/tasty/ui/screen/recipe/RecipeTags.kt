package com.example.tasty.ui.screen.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ContextualFlowRowOverflow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasty.R
import com.example.tasty.data.local.model.Tag
import com.example.tasty.ui.theme.TastyTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeTags(
    tags: List<Tag>,
    modifier: Modifier = Modifier,
    onTagSelected: (Int) -> Unit
) {
    var overflowSelected by remember { mutableStateOf(false) }
    val expandIndicator = remember {
        ContextualFlowRowOverflow.expandIndicator {
            val remainingItems = tags.size - shownItemCount
            OverflowItem(
                remainingItemsCount = remainingItems,
                onOverflowItemSelected = { overflowSelected = true }
            )
        }
    }

    ContextualFlowRow(
        itemCount = tags.size,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxLines = 3,
        overflow = if (overflowSelected) ContextualFlowRowOverflow.Visible else expandIndicator
    ) { index ->
        TagItem(
            tag = tags[index],
            onTagSelected = onTagSelected
        )
    }
}

@Composable
private fun TagItem(
    tag: Tag,
    onTagSelected: (Int) -> Unit
) {
    Text(
        text = "#" + tag.displayName,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
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
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun OverflowItem(
    remainingItemsCount: Int,
    onOverflowItemSelected: () -> Unit
) {
    Text(
        text = stringResource(R.string.show_n_more_items, remainingItemsCount),
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = CircleShape
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onOverflowItemSelected
            ),
        color = MaterialTheme.colorScheme.tertiary,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Preview
@Composable
private fun RecipeTagsPreview() {
    TastyTheme {
        RecipeTags(
            tags = listOf(
                Tag(1, "Serbian", "serbian"),
                Tag(2, "Hungarian", "hungarian"),
                Tag(3, "Greek", "greek"),
                Tag(4, "Spanish", "spanish"),
                Tag(5, "Decorative Cookies", "decorative_cookies"),
                Tag(6, "Holiday Desserts", "holiday_desserts"),
                Tag(7, "Chicken", "chicken"),
                Tag(8, "Tree nuts", "tree_nuts"),
                Tag(9, "Alcohol-Free", "alcohol_free"),
                Tag(10, "Tillamook Bag", "tillamook_bag"),
                Tag(11, "Mccormick Sunday", "mccormick_sunday"),
            ),
            onTagSelected = {}
        )
    }
}