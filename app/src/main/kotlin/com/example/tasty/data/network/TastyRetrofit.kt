package com.example.tasty.data.network

import com.example.tasty.data.network.model.Recipe
import com.squareup.moshi.JsonClass
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

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
			throw e
			listOf()
		}
	}
}
