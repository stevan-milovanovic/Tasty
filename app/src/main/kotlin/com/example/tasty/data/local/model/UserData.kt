package com.example.tasty.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class summarizing user interest data
 */
@Entity(tableName = "userData")
data class UserData(
    @PrimaryKey
    val bookmarkedRecipes: Set<Int>,
    val shouldHideOnboarding: Boolean,
)
