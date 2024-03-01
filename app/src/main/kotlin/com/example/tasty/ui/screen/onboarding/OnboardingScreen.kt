package com.example.tasty.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyTheme
import com.example.tasty.ui.theme.ThemePreviews

@Composable
fun OnboardingScreen(
	onContinueClick: () -> Unit
) {
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		Greeting(onContinueClick)
	}
}

@Composable
private fun Greeting(
	onContinueClick: () -> Unit
) {
	Column {
		Image(
			painter = painterResource(id = R.drawable.tasty_onboarding),
			contentDescription = stringResource(id = R.string.app_name),
			modifier = Modifier.fillMaxWidth(),
			contentScale = ContentScale.FillWidth
		)
		Text(
			text = stringResource(R.string.start_cooking),
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 48.dp),
			style = MaterialTheme.typography.titleLarge,
			textAlign = TextAlign.Center,
		)
		Text(
			text = stringResource(R.string.let_s_join_our_community_to_cook_better_food),
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			style = MaterialTheme.typography.bodyLarge,
			textAlign = TextAlign.Center,
			color = MaterialTheme.colorScheme.tertiary
		)
		Button(
			onClick = onContinueClick,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 32.dp, vertical = 19.dp)
		) {
			Text(
				text = stringResource(R.string.get_started),
				modifier = Modifier.padding(8.dp),
				style = MaterialTheme.typography.titleSmall
			)
		}
	}
}

@ThemePreviews
@Composable
private fun GreetingPreview() {
	TastyTheme {
		OnboardingScreen {}
	}
}
