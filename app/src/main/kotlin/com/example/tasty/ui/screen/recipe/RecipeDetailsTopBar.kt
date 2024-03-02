package com.example.tasty.ui.screen.recipe

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyIcons
import com.example.tasty.ui.theme.TastyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsTopBar(
	isRecipeBookmarked: Boolean,
	onBackClick: () -> Unit,
	onBookmarkClick: (Boolean) -> Unit
) {
	TopAppBar(
		title = {
			Text(
				text = stringResource(id = R.string.recipe_details),
				maxLines = 1
			)
		},
		navigationIcon = {
			IconButton(onClick = onBackClick) {
				Icon(
					TastyIcons.ArrowBack,
					stringResource(id = R.string.back),
				)
			}
		},
		actions = {
			IconButton(onClick = { onBookmarkClick(!isRecipeBookmarked) }) {
				Icon(
					imageVector = if (isRecipeBookmarked) TastyIcons.Bookmark else TastyIcons.BookmarkBorder,
					contentDescription = stringResource(R.string.bookmark_recipe)
				)
			}
		},
	)
}

@Preview
@Composable
private fun RecipeDetailsTopBarPreview() {
	TastyTheme {
		RecipeDetailsTopBar(false, {}) {}
	}
}
