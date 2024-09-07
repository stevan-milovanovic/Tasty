package com.example.tasty.data.repository.recipe

import androidx.paging.PagingData
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.RecipeWithTags
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the recipe data layer.
 */
interface RecipeRepository {

    fun getRecipesPagedFlow(recipeIds: List<Int>): Flow<PagingData<Recipe>>

    fun getRecipesForTagPagedFlow(tag: Tag?): Flow<PagingData<TagWithRecipes>>

    fun getRecipeFlow(recipeId: Int): Flow<RecipeWithTags>
}
