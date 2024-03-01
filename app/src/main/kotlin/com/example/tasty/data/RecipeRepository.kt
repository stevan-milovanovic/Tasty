package com.example.tasty.data

import com.example.tasty.data.local.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the recipe data layer.
 */
interface RecipeRepository {

	fun getRecipesFlow(): Flow<List<Recipe>>

	suspend fun fetchRecipesIfNeeded()

	fun getRecipeFlow(recipeId: Int): Flow<Recipe>

	suspend fun getRecipe(recipeId: Int, forceUpdate: Boolean = false): Recipe

	suspend fun refresh()

}
