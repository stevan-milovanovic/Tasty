package com.example.tasty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.RecipeTagCrossRef
import com.example.tasty.data.local.model.RecipeWithTags
import com.example.tasty.data.local.model.TagWithRecipes
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Recipe table.
 */
@Dao
interface RecipeDao {

    /**
     * Observes a single recipe.
     *
     * @param recipeId the recipe id.
     * @return the recipe with recipeId.
     */
    @Transaction
    @Query("SELECT * FROM recipe WHERE recipeId = :recipeId")
    fun observeById(recipeId: Int): Flow<RecipeWithTags>

    /**
     * Load all recipes paged
     */
    @Query("SELECT * FROM recipe WHERE recipeId IN (:recipeIds)")
    fun loadRecipesPaged(recipeIds: List<Int>): PagingSource<Int, Recipe>

    /**
     * Load all recipes for tag paged
     */
    @Transaction
    @Query("SELECT * FROM recipeTag WHERE (:tagId IS NULL OR tagId = :tagId)")
    fun loadAllRecipesForTagPaged(tagId: Int?): PagingSource<Int, TagWithRecipes>

    /**
     * Select a recipe by id.
     *
     * @param recipeId the recipe id.
     * @return the recipe with recipeId.
     */
    @Query("SELECT * FROM recipe WHERE recipeId = :recipeId")
    suspend fun getById(recipeId: Int): Recipe

    /**
     * Insert or update recipes in the database. If a recipe already exists, replace it.
     *
     * @param recipes the recipes to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(recipes: List<Recipe>)

    /**
     * Insert or update recipes tags cross references in the database.
     *
     * @param recipesTags the recipes tags cross references to be inserted or updated.
     */
    @Upsert
    suspend fun upsertRecipeTagsCrossRefs(recipesTags: List<RecipeTagCrossRef>)

    /**
     * Check if [Recipe] table is empty
     */
    @Query("SELECT (SELECT COUNT(*) FROM recipe) == 0")
    fun isEmpty(): Boolean

    @Query("SELECT COUNT(recipeId) FROM recipe")
    fun getRecipesCount(): Int
}
