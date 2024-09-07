package com.example.tasty.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Internal model used to represent a recipe tag stored locally in a Room database.
 * This is used inside the data layer only.
 */
@Entity(tableName = "recipeTag")
data class Tag(
    @PrimaryKey
    val tagId: Int,
    val displayName: String,
    val name: String?,
)