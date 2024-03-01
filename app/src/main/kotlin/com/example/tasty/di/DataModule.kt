package com.example.tasty.di

import android.content.Context
import androidx.room.Room
import com.example.tasty.data.RecipeRepository
import com.example.tasty.data.RecipeRepositoryImpl
import com.example.tasty.data.UserDataRepository
import com.example.tasty.data.UserDataRepositoryImpl
import com.example.tasty.data.local.TastyDatabase
import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.dao.UserDataDao
import com.example.tasty.data.network.NetworkDataSource
import com.example.tasty.data.network.NetworkMonitor
import com.example.tasty.data.network.TastyRetrofit
import com.example.tasty.util.ConnectivityManagerNetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
	@Binds
	internal abstract fun bindsNetworkMonitor(
		networkMonitor: ConnectivityManagerNetworkMonitor,
	): NetworkMonitor
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
	@Singleton
	@Binds
	abstract fun bindNetworkDataSource(dataSource: TastyRetrofit): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Singleton
	@Provides
	fun provideDatabase(@ApplicationContext context: Context): TastyDatabase = Room.databaseBuilder(
		context.applicationContext,
		TastyDatabase::class.java,
		"Tasty.db"
	).fallbackToDestructiveMigration().build()

	@Provides
	fun provideRecipeDao(database: TastyDatabase): RecipeDao = database.recipeDao()

	@Provides
	fun provideUserDataDao(database: TastyDatabase): UserDataDao = database.userDataDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Singleton
	@Binds
	abstract fun bindRecipesRepository(repository: RecipeRepositoryImpl): RecipeRepository

	@Singleton
	@Binds
	abstract fun bindUserDataRepository(repository: UserDataRepositoryImpl): UserDataRepository
}
