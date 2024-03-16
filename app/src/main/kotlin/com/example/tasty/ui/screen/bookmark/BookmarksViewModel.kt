package com.example.tasty.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.UserData
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    recipesRepository: RecipeRepository,
    userDataRepository: UserDataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            recipesRepository.fetchRecipesIfNeeded()
        }
    }

    private val userDataFlow: Flow<UserData?> = userDataRepository.getUserDataFlow()
    private val pagingDataFlow: Flow<PagingData<Recipe>> = recipesRepository
        .getRecipesPagedFlow()
        .cachedIn(viewModelScope)

    val recipesPagingDataFlow = pagingDataFlow.combine(userDataFlow) { pagingData, userData ->
        if (userData != null) {
            pagingData.filter { recipe -> userData.bookmarkedRecipes.contains(recipe.id) }
        } else {
            pagingData
        }
    }
}
