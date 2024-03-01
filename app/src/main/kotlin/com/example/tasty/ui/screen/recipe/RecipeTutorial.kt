package com.example.tasty.ui.screen.recipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasty.R
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.ui.recipe.RecipeImage

private const val PLAY_ANIMATION_DELAY_MILLIS = 1750

@Composable
fun RecipeTutorial(
	recipe: Recipe
) {
	var shouldPlayVideo by remember { mutableStateOf(false) }

	Box(
		modifier = Modifier
			.fillMaxHeight(0.4f)
			.wrapContentHeight(Alignment.Top, unbounded = false),
		contentAlignment = Alignment.Center
	) {
		RecipeImage(
			thumbnailUrl = recipe.thumbnailUrl,
			modifier = Modifier.fillMaxSize()
		)
		if (recipe.videoUrl == null) {
			return
		}
		val animationSpec: FiniteAnimationSpec<Float> =
			tween(delayMillis = PLAY_ANIMATION_DELAY_MILLIS)
		AnimatedVisibility(
			visible = shouldPlayVideo,
			enter = fadeIn(animationSpec = animationSpec)
		) {
			VideoPlayer(videoUrl = recipe.videoUrl)
		}
		AnimatedVisibility(
			visible = !shouldPlayVideo,
			exit = fadeOut(animationSpec = animationSpec)
		) {
			var iconClicked by remember { mutableStateOf(false) }
			if (iconClicked) {
				CircularProgressIndicator(
					modifier = Modifier.size(64.dp),
					color = MaterialTheme.colorScheme.onPrimary,
					trackColor = MaterialTheme.colorScheme.surfaceVariant,
				)
			} else {
				Icon(
					imageVector = Icons.Rounded.PlayCircle,
					contentDescription = stringResource(id = R.string.play_video),
					modifier = Modifier
						.size(64.dp)
						.clickable {
							iconClicked = true
							shouldPlayVideo = true
						},
					tint = MaterialTheme.colorScheme.onPrimary
				)
			}
		}
	}
}
