package com.example.tasty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tasty.data.local.converter.BookmarkedRecipesConverter
import com.example.tasty.data.local.converter.InstructionsConverter
import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.dao.UserDataDao
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.UserData

/**
 * The Room Database that contains the Recipe table.
 *
 * Note that exportSchema should be true in production databases.
 */
@TypeConverters(InstructionsConverter::class, BookmarkedRecipesConverter::class)
@Database(entities = [Recipe::class, UserData::class], version = 2, exportSchema = false)
abstract class TastyDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    abstract fun userDataDao(): UserDataDao
}
