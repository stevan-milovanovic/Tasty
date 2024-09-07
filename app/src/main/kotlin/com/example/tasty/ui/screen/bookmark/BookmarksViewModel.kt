package com.example.tasty.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    recipesRepository: RecipeRepository,
    userDataRepository: UserDataRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<Recipe>> = userDataRepository.getUserDataFlow()
        .flatMapLatest { userData ->
            val bookmarkedRecipeIds = userData?.bookmarkedRecipes?.toList() ?: emptyList()
            recipesRepository.getRecipesPagedFlow(bookmarkedRecipeIds)
        }
        .cachedIn(viewModelScope)
}