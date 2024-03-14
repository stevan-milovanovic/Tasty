package com.example.tasty.ui.screen.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import com.example.tasty.ui.Result
import com.example.tasty.ui.asResult
import com.example.tasty.ui.component.recipe.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

	val uiState: StateFlow<RecipesUiState> = recipesRepository
		.getRecipesFlow()
		.asResult()
		.map { result ->
			when (result) {
				is Result.Success -> {
					RecipesUiState.Success(recipes = result.data)
				}

				is Result.Loading -> RecipesUiState.Loading
				is Result.Error -> RecipesUiState.Error
			}
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = RecipesUiState.Loading,
		)
}
