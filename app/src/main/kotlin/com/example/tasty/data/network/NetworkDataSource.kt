package com.example.tasty.data.network

import com.example.tasty.data.network.model.Recipe
import com.example.tasty.data.network.model.Tag

/**
 * Interface representing network calls to the Tasty backend
 */
interface NetworkDataSource {
    suspend fun getRecipes(from: Int, tags: List<String>): Pair<Int, List<Recipe>>

    suspend fun getTags(): Pair<Int, List<Tag>>
}
