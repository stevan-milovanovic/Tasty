package com.example.tasty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasty.data.local.model.Recipe
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Recipe table.
 */
@Dao
interface RecipeDao {
	/**
	 * Observes list of recipes.
	 *
	 * @return all recipes.
	 */
	@Query("SELECT * FROM recipe")
	fun observeAll(): Flow<List<Recipe>>

	/**
	 * Observes a single recipe.
	 *
	 * @param recipeId the recipe id.
	 * @return the recipe with recipeId.
	 */
	@Query("SELECT * FROM recipe WHERE id = :recipeId")
	fun observeById(recipeId: Int): Flow<Recipe>

	/**
	 * Select all recipes from the recipes table.
	 *
	 * @return all recipes.
	 */
	@Query("SELECT * FROM recipe")
	suspend fun getAll(): List<Recipe>

	/**
	 * Select a recipe by id.
	 *
	 * @param recipeId the recipe id.
	 * @return the recipe with recipeId.
	 */
	@Query("SELECT * FROM recipe WHERE id = :recipeId")
	suspend fun getById(recipeId: Int): Recipe

	/**
	 * Delete all recipes.
	 */
	@Query("DELETE FROM recipe")
	suspend fun deleteAll()

	/**
	 * Insert or update recipes in the database. If a recipe already exists, replace it.
	 *
	 * @param recipes the recipes to be inserted or updated.
	 */
	@Upsert
	suspend fun upsertAll(recipes: List<Recipe>)

}
