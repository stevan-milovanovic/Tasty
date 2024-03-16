package com.example.tasty.ui.screen.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.screen.common.ErrorScreen
import com.example.tasty.ui.screen.common.LoadingScreen
import com.example.tasty.ui.theme.TastyTheme
import de.charlex.compose.material3.HtmlText

@Composable
fun RecipeScreen(
    uiState: RecipeUiState,
    updateBookmarkedList: (Int, Boolean) -> Unit,
    onBackClick: () -> Unit,
) {
    when (uiState) {
        RecipeUiState.Error -> ErrorScreen()
        RecipeUiState.Loading -> LoadingScreen()
        is RecipeUiState.Success -> {
            RecipeScreenContent(
                recipe = uiState.recipe,
                isRecipeBookmarked = uiState.isRecipeBookmarked,
                onBackClick = onBackClick,
                onBookmarkClick = updateBookmarkedList
            )
        }
    }
}

@Composable
private fun RecipeScreenContent(
    recipe: Recipe,
    isRecipeBookmarked: Boolean,
    onBackClick: () -> Unit,
    onBookmarkClick: (Int, Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            RecipeDetailsTopBar(
                isRecipeBookmarked = isRecipeBookmarked,
                onBackClick = onBackClick,
                onBookmarkClick = { onBookmarkClick(recipe.id, it) }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            RecipeTutorial(recipe)
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge
                )
                recipe.description?.let {
                    HtmlText(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                    )
                }
                recipe.keywords?.let {
                    RecipeKeywords(
                        keywords = it,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                recipe.instructions?.let {
                    RecipeInstructions(
                        instructions = it,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecipeScreenContentPreview() {
    TastyTheme {
        RecipeScreenContent(
            recipe = Recipe(
                1,
                "Maple Bacon Marshmallow Fluff Sweet Potato",
                "This One-Pot Cheeseburger Pasta is a delightful twist on a classic favorite. With its savory flavors and easy clean-up, this dish is perfect for busy weeknights and satisfying comfort food cravings.",
                "test",
                "",
                "buzzfeed, chicken, creamy, dinner, lemon, one-pan, quick and easy, tasty",
                "Under 30 minutes",
                listOf(
                    "In a large bowl, add the flour, sugar, salt, baking powder, and baking soda and whisk to combine.",
                    "In a medium bowl or liquid measuring cup, add the buttermilk, melted butter, and egg yolks and whisk to combine.",
                    "Add the buttermilk mixture to the dry ingredients and gently fold with a rubber spatula until just combined.",
                    "Add the egg whites and fold until just combined. Be sure not to overmix. Some lumps are okay.",
                    "Let the batter rest for 15-30 minutes at room temperature.",
                )
            ),
            isRecipeBookmarked = false,
            onBackClick = { },
            onBookmarkClick = { _, _ -> }
        )
    }
}
