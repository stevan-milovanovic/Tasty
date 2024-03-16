package com.example.tasty.data.repository.recipe

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.network.NetworkDataSource
import com.example.tasty.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: RecipeDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RecipeRepository {
    override fun getRecipesPagedFlow(): Flow<PagingData<Recipe>> = Pager(PagingConfig(pageSize = 20)) {
        localDataSource.loadAllRecipesPaged()
    }.flow

    override suspend fun fetchRecipesIfNeeded() {
        val localRecipesIsEmpty = withContext(dispatcher) { localDataSource.isEmpty() }
        if (localRecipesIsEmpty) {
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
