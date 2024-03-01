package com.example.tasty.ui.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyTheme

@Composable
fun ErrorScreen() {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Card(
			modifier = Modifier
				.padding(20.dp),
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.primary
			)
		) {
			Text(
				text = stringResource(id = R.string.unexpected_error),
				modifier = Modifier
					.padding(20.dp),
				color = MaterialTheme.colorScheme.onPrimary
			)
		}
	}
}

@Preview(showSystemUi = true)
@Composable
private fun ProgressBarBoxPreview() {
	TastyTheme {
		ErrorScreen()
	}
}
