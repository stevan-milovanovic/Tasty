package com.example.tasty.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Internal model used to represent a recipe stored locally in a Room database.
 * This is used inside the data layer only.
 */
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey
    val recipeId: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String,
    val videoUrl: String?,
    val keywords: String?,
    val totalTimeNeeded: String?,
    val instructions: List<String>?
)

@Entity(primaryKeys = ["recipeId", "tagId"])
data class RecipeTagCrossRef(
    val recipeId: Int,
    val tagId: Int
)

data class RecipeWithTags(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "tagId",
        associateBy = Junction(RecipeTagCrossRef::class)
    )
    val tags: List<Tag>
)

data class TagWithRecipes(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "recipeId",
        associateBy = Junction(RecipeTagCrossRef::class)
    )
    val recipes: List<Recipe>
)