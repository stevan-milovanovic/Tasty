package com.example.tasty.data.network

import android.util.Log
import com.example.tasty.data.network.model.Recipe
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

const val DEFAULT_PAGE_SIZE = 40

interface TastyNetworkApi {
    @GET(value = "recipes/list")
    suspend fun getRecipes(
        @Query("from") from: Int,
        @Query("size") size: Int = DEFAULT_PAGE_SIZE,
    ): Response<NetworkResponse<Recipe>>
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

    override suspend fun getRecipes(from: Int): Pair<Int, List<Recipe>> {
        return try {
            val response = tastyNetworkApi.getRecipes(from)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                (result.count to result.results)
            } else {
                (0 to listOf())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected exception while trying to fetch recipes", e)
            throw e
        }
    }
}
