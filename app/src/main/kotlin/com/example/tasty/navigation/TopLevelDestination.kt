package com.example.tasty.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tasty.R
import com.example.tasty.ui.theme.TastyIcons

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
) {
    FOR_YOU(
        selectedIcon = TastyIcons.Upcoming,
        unselectedIcon = TastyIcons.UpcomingBorder,
        iconTextId = R.string.feature_for_you_title
    ),
    BOOKMARKS(
        selectedIcon = TastyIcons.Bookmarks,
        unselectedIcon = TastyIcons.BookmarksBorder,
        iconTextId = R.string.feature_bookmarks_title
    )
}
