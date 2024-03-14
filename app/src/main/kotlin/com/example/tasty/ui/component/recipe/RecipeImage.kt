package com.example.tasty.ui.component.recipe

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun RecipeImage(
	thumbnailUrl: String,
	modifier: Modifier = Modifier
) {
	AsyncImage(
		model = thumbnailUrl,
		contentDescription = stringResource(R.string.recipe_thumbnail),
		modifier = modifier.fillMaxWidth(),
		contentScale = ContentScale.Crop,
		placeholder = rememberVectorPainter(
			image = ImageVector.vectorResource(R.drawable.ic_cooking_placeholder)
		)
	)
}

@Preview(showSystemUi = true)
@Composable
fun RecipeImagePreview() {
	TastyTheme {
		RecipeImage(thumbnailUrl = "test")
	}
}
