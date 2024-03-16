package com.example.tasty.data.repository.recipe

import androidx.paging.PagingData
import com.example.tasty.data.local.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the recipe data layer.
 */
interface RecipeRepository {

    fun getRecipesPagedFlow(): Flow<PagingData<Recipe>>

    suspend fun fetchRecipesIfNeeded()

    fun getRecipeFlow(recipeId: Int): Flow<Recipe>

    suspend fun getRecipe(recipeId: Int, forceUpdate: Boolean = false): Recipe

    suspend fun refresh()
}
