package com.example.tasty.ui.screen.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun RecipeInstructions(
	instructions: List<String>,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
	) {
		Text(
			text = stringResource(R.string.how_to_make_it),
			modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
			style = MaterialTheme.typography.titleSmall,
		)
		repeat(instructions.size) { instructionIndex ->
			val instruction = instructions[instructionIndex]
			Row(
				modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
				verticalAlignment = Alignment.Top
			) {
				Text(
					text = (instructionIndex + 1).toString() + ".",
					style = MaterialTheme.typography.bodySmall,
					lineHeight = 18.sp
				)
				Text(
					text = instruction,
					modifier = Modifier.padding(horizontal = 12.dp),
					style = MaterialTheme.typography.bodySmall,
					lineHeight = 18.sp
				)
			}
		}
	}
}


@Preview
@Composable
private fun RecipeInstructionsPreview() {
	TastyTheme {
		RecipeInstructions(
			instructions = listOf(
				"In a blender or food processor, combine the yogurt, lime juice, pepper, and chili powder and pulse to combine. Add ½ of the avocado and blend until creamy.",
				"In a medium bowl, combine the chicken, yogurt sauce, celery, the remaining ½ avocado, onion, and salt. Mix until well combined.",
				"Serve on low-carb bread and garnish with cilantro, or as desired.",
				"Enjoy!"
			)
		)
	}
}
