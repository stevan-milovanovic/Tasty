package com.example.tasty.data.repository.recipe

import androidx.paging.PagingData
import com.example.tasty.data.local.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the recipe data layer.
 */
interface RecipeRepository {

    fun getRecipesPagedFlow(): Flow<PagingData<Recipe>>

    fun getRecipeFlow(recipeId: Int): Flow<Recipe>
}
