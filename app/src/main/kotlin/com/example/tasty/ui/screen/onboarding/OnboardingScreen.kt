package com.example.tasty.ui.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
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
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
        ExpandedWidthGreeting(
            onContinueClick = onContinueClick
        )
    } else {
        Column {
            BackgroundImage(id = R.drawable.tasty_onboarding)
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
            GetStartedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 19.dp),
                textStyle = MaterialTheme.typography.titleSmall,
                onContinueClick = onContinueClick
            )
        }
    }
}

@Composable
private fun GetStartedButton(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    onContinueClick: () -> Unit
) {
    Button(
        onClick = onContinueClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.get_started),
            modifier = Modifier.padding(8.dp),
            style = textStyle
        )
    }
}

@Composable
private fun ExpandedWidthGreeting(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit
) {
    Box(modifier = modifier) {
        BackgroundImage(id = R.drawable.tasty_expanded_width_background)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 80.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.start_cooking),
                modifier = Modifier
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.let_s_join_our_community_to_cook_better_food),
                modifier = Modifier
                    .padding(bottom = 48.dp),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary
            )
            GetStartedButton(
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp),
                textStyle = MaterialTheme.typography.titleLarge,
                onContinueClick = onContinueClick
            )
        }
    }
}

@Composable
private fun BackgroundImage(
    @DrawableRes id: Int
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = stringResource(id = R.string.app_name),
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@ThemePreviews
@Composable
private fun GreetingPreview() {
    TastyTheme {
        OnboardingScreen {}
    }
}

@Composable
@Preview(device = Devices.PIXEL_TABLET)
private fun ExpandedWidthGreetingPreview() {
    TastyTheme {
        ExpandedWidthGreeting {}
    }
}
