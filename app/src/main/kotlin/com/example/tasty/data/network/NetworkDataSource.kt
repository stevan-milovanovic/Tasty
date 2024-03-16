package com.example.tasty.data.network

import com.example.tasty.data.network.model.Recipe

/**
 * Interface representing network calls to the Tasty backend
 */
interface NetworkDataSource {
    suspend fun getRecipes(): List<Recipe>
}
