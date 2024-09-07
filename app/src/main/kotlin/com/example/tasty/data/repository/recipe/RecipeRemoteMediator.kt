package com.example.tasty.data.repository.recipe

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.tasty.data.local.TastyDatabase
import com.example.tasty.data.local.model.RecipeTagCrossRef
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import com.example.tasty.data.network.NetworkDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator @Inject constructor(
    private val tastyDatabase: TastyDatabase,
    private val networkDataSource: NetworkDataSource
) : RemoteMediator<Int, TagWithRecipes>() {

    private var remoteRecipesCount = Int.MAX_VALUE
    private var localRecipesCount = Int.MIN_VALUE

    private var activeTag: Tag? = null

    fun setActiveTag(tag: Tag) {
        activeTag = tag
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TagWithRecipes>
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

            val (remoteRecipesCount, remoteRecipes) = networkDataSource.getRecipes(
                from, activeTag?.name?.let { listOf(it) } ?: listOf()
            )
            this@RecipeRemoteMediator.remoteRecipesCount = remoteRecipesCount

            tastyDatabase.withTransaction {
                val recipes = remoteRecipes.map { it.toLocal() }
                tastyDatabase.recipeDao().upsertAll(recipes)
                remoteRecipes.forEach { recipe ->
                    val recipeTagsCrossRefs = recipe.tags.map { tag -> RecipeTagCrossRef(recipe.id, tag.id) }
                    tastyDatabase.recipeDao().upsertRecipeTagsCrossRefs(recipeTagsCrossRefs)
                }
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