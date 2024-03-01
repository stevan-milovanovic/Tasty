package com.example.tasty.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Internal model used to represent a recipe stored locally in a Room database.
 * This is used inside the data layer only.
 *
 * See ModelMappingExt.kt for mapping functions used to convert this model to other
 * models.
 */
@Entity(tableName = "recipe")
data class Recipe(
	@PrimaryKey
	val id: Int,
	val name: String,
	val description: String,
	val thumbnailUrl: String,
	val videoUrl: String?,
	val keywords: String,
	val totalTimeNeeded: String?,
	val instructions: List<String>
)
