package com.example.tasty.data.network

import android.util.Log
import com.example.tasty.data.network.model.Recipe
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

interface TastyNetworkApi {
    @GET(value = "recipes/list")
    suspend fun getRecipes(): Response<NetworkResponse<Recipe>>
}

@JsonClass(generateAdapter = true)
data class NetworkResponse<T>(
    val count: Int,
    val results: List<T>
)

/**
 * [Retrofit] backed [NetworkDataSource]
 */
@Singleton
class TastyRetrofit @Inject constructor(
    private val tastyNetworkApi: TastyNetworkApi
) : NetworkDataSource {

    companion object {
        private const val TAG = "Tasty Network Layer"
    }

    override suspend fun getRecipes(): List<Recipe> {
        return try {
            val response = tastyNetworkApi.getRecipes()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                result.results
            } else {
                listOf()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected network error", e)
            throw e
        }
    }
}
