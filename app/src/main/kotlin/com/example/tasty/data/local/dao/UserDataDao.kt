package com.example.tasty.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasty.data.local.model.UserData
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the User Data table.
 */
@Dao
interface UserDataDao {

	/**
	 * Observes user data.
	 *
	 * @return user data.
	 */
	@Query("SELECT * FROM userData")
	fun observeUserData(): Flow<UserData?>

	/**
	 * Select user data from the user data table.
	 *
	 * @return user data or null if it doesn't exist
	 */
	@Query("SELECT * FROM userData")
	suspend fun getUserData(): UserData?

	/**
	 * Insert or update user data in the database. If the user data already exists, replace it.
	 *
	 * @param userData data to be inserted or updated.
	 */
	@Upsert
	suspend fun upsertUserData(userData: UserData)

	/**
	 * Insert or update bookmarked recipe ids in the database.
	 *
	 * @param bookmarkedRecipes the bookmarked recipes to be inserted or updated.
	 */
	@Query("UPDATE userData SET bookmarkedRecipes = :bookmarkedRecipes")
	suspend fun upsertBookmarkedRecipes(bookmarkedRecipes: Set<Int>)

}
