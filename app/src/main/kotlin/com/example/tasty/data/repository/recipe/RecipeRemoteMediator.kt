/*
 * Copyright © 2014-2024, TWINT AG.
 * All rights reserved.
*/
package com.example.tasty.data.repository.recipe

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tasty.data.local.TastyDatabase
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.network.NetworkDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator @Inject constructor(
    private val tastyDatabase: TastyDatabase,
    private val networkDataSource: NetworkDataSource
) : RemoteMediator<Int, Recipe>() {

    private var remoteRecipesCount = Int.MAX_VALUE
    private var localRecipesCount = Int.MIN_VALUE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Recipe>
    ): MediatorResult {
        return try {
            val from = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    if (localRecipesCount < remoteRecipesCount) {
                        localRecipesCount
                    } else {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
            }

            val (remoteRecipesCount, remoteRecipes) = networkDataSource.getRecipes(from)
            this@RecipeRemoteMediator.remoteRecipesCount = remoteRecipesCount

            tastyDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tastyDatabase.recipeDao().deleteAll()
                }
                val recipes = remoteRecipes.map { it.toLocal() }
                tastyDatabase.recipeDao().upsertAll(recipes)
                localRecipesCount = tastyDatabase.recipeDao().getRecipesCount()
            }

            return MediatorResult.Success(endOfPaginationReached = remoteRecipesCount == localRecipesCount)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}