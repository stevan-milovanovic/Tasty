package com.example.tasty.di

import android.content.Context
import androidx.room.Room
import com.example.tasty.data.local.TastyDatabase
import com.example.tasty.data.local.dao.RecipeDao
import com.example.tasty.data.local.dao.TagDao
import com.example.tasty.data.local.dao.UserDataDao
import com.example.tasty.data.network.NetworkDataSource
import com.example.tasty.data.network.NetworkMonitor
import com.example.tasty.data.network.TastyRetrofit
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.recipe.RecipeRepositoryImpl
import com.example.tasty.data.repository.tag.TagRepository
import com.example.tasty.data.repository.tag.TagRepositoryImpl
import com.example.tasty.data.repository.userData.UserDataRepository
import com.example.tasty.data.repository.userData.UserDataRepositoryImpl
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
    fun provideTagDao(database: TastyDatabase): TagDao = database.tagDao()

    @Provides
    fun provideUserDataDao(database: TastyDatabase): UserDataDao = database.userDataDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRecipeRepository(repository: RecipeRepositoryImpl): RecipeRepository

    @Singleton
    @Binds
    abstract fun bindTagRepository(repository: TagRepositoryImpl): TagRepository

    @Singleton
    @Binds
    abstract fun bindUserDataRepository(repository: UserDataRepositoryImpl): UserDataRepository
}
