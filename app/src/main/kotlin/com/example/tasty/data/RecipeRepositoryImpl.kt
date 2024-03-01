package com.example.tasty.data

import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.network.NetworkDataSource
import com.example.tasty.di.DefaultDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl @Inject constructor(
	private val networkDataSource: NetworkDataSource,
	private val localDataSource: RecipeDao,
	@DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RecipeRepository {
	override fun getRecipesFlow(): Flow<List<Recipe>> =
		localDataSource.observeAll().map { recipes ->
			withContext(dispatcher) { recipes }
		}

	override suspend fun fetchRecipesIfNeeded() {
		val localRecipes = withContext(dispatcher) { localDataSource.getAll() }
		if (localRecipes.isEmpty()) {
			refresh()
		}
	}

	override fun getRecipeFlow(recipeId: Int): Flow<Recipe> =
		localDataSource.observeById(recipeId).map { withContext(dispatcher) { it } }

	override suspend fun getRecipe(recipeId: Int, forceUpdate: Boolean): Recipe {
		val localRecipe = withContext(dispatcher) { localDataSource.getById(recipeId) }
		var isRefreshed = false
		if (forceUpdate) {
			refresh()
			isRefreshed = true
		}
		return if (isRefreshed) withContext(dispatcher) { localDataSource.getById(recipeId) } else localRecipe
	}

	override suspend fun refresh() {
		withContext(dispatcher) {
			val remoteRecipes = networkDataSource.getRecipes()
			localDataSource.deleteAll()
			val filteredRecipes = remoteRecipes.filterNot { it.thumbnailUrl.endsWith("jpeg") }
			localDataSource.upsertAll(filteredRecipes.toLocal())
		}
	}
}

@JvmName("networkToLocal")
fun List<com.example.tasty.data.network.model.Recipe>.toLocal() =
	map(com.example.tasty.data.network.model.Recipe::toLocal)
