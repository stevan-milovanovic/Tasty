package com.example.tasty.data.repository.recipe

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.network.DEFAULT_PAGE_SIZE
import com.example.tasty.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val localDataSource: RecipeDao,
    private val remoteMediator: RecipeRemoteMediator,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RecipeRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipesPagedFlow(): Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
        remoteMediator = remoteMediator
    ) {
        localDataSource.loadAllRecipesPaged()
    }.flow

    override fun getRecipeFlow(recipeId: Int): Flow<Recipe> =
        localDataSource.observeById(recipeId).map { withContext(dispatcher) { it } }
}