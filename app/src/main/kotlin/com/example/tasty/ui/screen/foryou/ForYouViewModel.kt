package com.example.tasty.ui.screen.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    recipesRepository: RecipeRepository,
    userDataRepository: UserDataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            recipesRepository.fetchRecipesIfNeeded()
            userDataRepository.updateOnboardingShownFlag()
        }
    }

    val pagingDataFlow: Flow<PagingData<Recipe>> = recipesRepository
        .getRecipesPagedFlow()
        .cachedIn(viewModelScope)
}
