package com.example.tasty.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.tasty.R
import com.example.tasty.data.network.NetworkMonitor
import com.example.tasty.navigation.ONBOARDING_ROUTE
import com.example.tasty.navigation.RECIPE_ROUTE
import com.example.tasty.navigation.TastyNavHost
import com.example.tasty.navigation.TopLevelDestination
import com.example.tasty.ui.component.TastyNavigationBar
import com.example.tasty.ui.component.TastyNavigationBarItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TastyApp(
	networkMonitor: NetworkMonitor,
	appState: TastyAppState = rememberTastyAppState(networkMonitor = networkMonitor),
	startDestination: String,
) {
	val snackbarHostState = remember { SnackbarHostState() }

	val isOffline by appState.isOffline.collectAsStateWithLifecycle()

	// If user is not connected to the internet show a snack bar to inform them.
	val notConnectedMessage = stringResource(R.string.not_connected)
	LaunchedEffect(isOffline) {
		if (isOffline) {
			snackbarHostState.showSnackbar(
				message = notConnectedMessage,
				duration = SnackbarDuration.Indefinite,
			)
		}
	}

	Scaffold(
		containerColor = Color.Transparent,
		contentColor = MaterialTheme.colorScheme.onBackground,
		contentWindowInsets = WindowInsets(0, 0, 0, 0),
		snackbarHost = { SnackbarHost(snackbarHostState) },
		bottomBar = {
			val currentRoute = appState.currentDestination?.route
			if (currentRoute == null ||
				currentRoute == ONBOARDING_ROUTE ||
				currentRoute.startsWith(RECIPE_ROUTE)
			) {
				return@Scaffold
			} else {
				TastyBottomBar(
					destinations = appState.topLevelDestinations,
					onNavigateToDestination = appState::navigateToTopLevelDestination,
					currentDestination = appState.currentDestination
				)
			}
		},
	) { padding ->
		Row(
			Modifier
				.fillMaxSize()
				.padding(padding)
				.consumeWindowInsets(padding)
				.windowInsetsPadding(
					WindowInsets.safeDrawing.only(
						WindowInsetsSides.Horizontal,
					),
				),
		) {
			Column(Modifier.fillMaxSize()) {
				TastyNavHost(
					appState = appState,
					startDestination = startDestination
				)
			}
		}
	}
}

@Composable
private fun TastyBottomBar(
	destinations: List<TopLevelDestination>,
	onNavigateToDestination: (TopLevelDestination) -> Unit,
	currentDestination: NavDestination?,
	modifier: Modifier = Modifier,
) {
	TastyNavigationBar(
		modifier = modifier,
	) {
		destinations.forEach { destination ->
			val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
			TastyNavigationBarItem(
				selected = selected,
				onClick = { onNavigateToDestination(destination) },
				icon = {
					Icon(
						imageVector = destination.unselectedIcon,
						contentDescription = null,
					)
				},
				selectedIcon = {
					Icon(
						imageVector = destination.selectedIcon,
						contentDescription = null,
					)
				},
				label = { Text(stringResource(destination.iconTextId)) },
			)
		}
	}
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
	this?.hierarchy?.any {
		it.route?.contains(destination.name, true) ?: false
	} ?: false
