package com.example.tasty.data.repository.userData

import com.example.tasty.data.local.dao.UserDataDao
import com.example.tasty.data.local.model.UserData
import com.example.tasty.di.DefaultDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserDataRepositoryImpl @Inject constructor(
	private val localDataSource: UserDataDao,
	@DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : UserDataRepository {

	override suspend fun updateOnboardingShownFlag() {
		withContext(dispatcher) {
			var userData = localDataSource.getUserData()
			userData = userData?.copy(shouldHideOnboarding = true) ?: UserData(
				bookmarkedRecipes = setOf(),
				shouldHideOnboarding = true
			)
			localDataSource.upsertUserData(userData)
		}
	}

	override fun getUserDataFlow(): Flow<UserData?> =
		localDataSource.observeUserData().map { userData ->
			withContext(dispatcher) { userData }
		}

	override suspend fun getUserData(): UserData? =
		withContext(dispatcher) { localDataSource.getUserData() }


	override suspend fun updateBookmarkedRecipes(userData: UserData) {
		withContext(dispatcher) {
			localDataSource.upsertBookmarkedRecipes(userData.bookmarkedRecipes)
		}
	}

}
