package com.example.tasty.ui.screen.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasty.ui.theme.TastyTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeKeywords(
    keywords: String,
    modifier: Modifier = Modifier
) {
    if (keywords.isBlank()) return
    val keywordsArray = keywords.split(", ")
    if (keywordsArray.isEmpty()) return

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        keywordsArray.forEach { keyword ->
            Text(
                text = keyword,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RecipeKeywordsPreview() {
    val keywords =
        "bacon, best, best ever, bread crumbs, butter, buzzfeed, cheddar, cheese, cheesy, chives, classic, colby jack, cook, easy, elbow, flour, food, garlic, gouda, gruyere, how to, mac and cheese, mac n cheese, macaroni, milk, mozzarella, noodles, panko, parsley, pasta, pepper, perfect, provolone, recipe, salt, tasty, ultimate, yum, yummy"
    TastyTheme {
        RecipeKeywords(keywords = keywords)
    }
}
