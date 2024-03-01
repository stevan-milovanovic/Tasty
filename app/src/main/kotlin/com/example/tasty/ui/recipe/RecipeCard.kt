package com.example.tasty.ui.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasty.R
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun RecipeCard(
	recipe: Recipe,
	onRecipeClick: (Int) -> Unit,
) {
	val width = 180.dp
	Column(
		modifier = Modifier
			.width(width)
			.padding(top = 8.dp, bottom = 8.dp)
			.clickable(
				interactionSource = remember { MutableInteractionSource() },
				indication = null,
				onClick = { onRecipeClick(recipe.id) }
			),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Card {
			RecipeImage(
				thumbnailUrl = recipe.thumbnailUrl,
				modifier = Modifier.size(width)
			)
		}
		Text(
			text = recipe.name,
			modifier = Modifier
				.width(width)
				.padding(top = 8.dp),
			color = MaterialTheme.colorScheme.onTertiary,
			lineHeight = 18.sp,
			textAlign = TextAlign.Start,
			maxLines = 3,
			style = MaterialTheme.typography.titleSmall
		)
		val timeText = recipe.totalTimeNeeded ?: stringResource(R.string.under_60_minutes)
		Row(
			modifier = Modifier
				.width(width)
				.padding(top = 2.dp, bottom = 12.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				imageVector = Icons.Filled.AccessTime,
				contentDescription = stringResource(R.string.cooking_time),
				modifier = Modifier.size(16.dp),
				tint = MaterialTheme.colorScheme.onTertiary
			)
			Text(
				text = timeText,
				modifier = Modifier.padding(start = 4.dp),
				color = MaterialTheme.colorScheme.tertiary,
				textAlign = TextAlign.Start,
				maxLines = 1,
				style = MaterialTheme.typography.bodySmall
			)
		}
	}
}

@Preview
@Composable
private fun RecipeCardPreview() {
	TastyTheme {
		RecipeCard(
			recipe = Recipe(
				1,
				"Low Carb Meals For A Healthy You",
				"Delicious meal",
				"test",
				"",
				"",
				"Under 30 minutes",
				listOf()
			),
			onRecipeClick = {}
		)
	}

}
