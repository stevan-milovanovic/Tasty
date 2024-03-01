package com.example.tasty.data

import com.example.tasty.data.local.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the user data layer.
 */
interface UserDataRepository {

	suspend fun updateOnboardingShownFlag()

	fun getUserDataFlow(): Flow<UserData?>

	suspend fun getUserData(): UserData?

	suspend fun updateBookmarkedRecipes(userData: UserData)

}
