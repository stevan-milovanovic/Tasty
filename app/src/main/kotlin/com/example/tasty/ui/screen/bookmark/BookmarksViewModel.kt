package com.example.tasty.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasty.data.RecipeRepository
import com.example.tasty.data.UserDataRepository
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.UserData
import com.example.tasty.ui.recipe.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

	private val recipesFlow: Flow<List<Recipe>> = recipesRepository.getRecipesFlow()
	private val userDataFlow: Flow<UserData?> = userDataRepository.getUserDataFlow()

	val uiState = recipesFlow.combine(userDataFlow) { recipes, userData ->
		if (userData != null) {
			RecipesUiState.Success(
				recipes = recipes.filter { userData.bookmarkedRecipes.contains(it.id) },
			)
		} else {
			RecipesUiState.Error
		}
	}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = RecipesUiState.Loading,
		)
}
