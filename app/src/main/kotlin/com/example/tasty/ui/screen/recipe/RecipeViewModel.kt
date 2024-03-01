package com.example.tasty.ui.screen.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasty.data.RecipeRepository
import com.example.tasty.data.UserDataRepository
import com.example.tasty.data.local.model.Recipe
import com.example.tasty.data.local.model.UserData
import com.example.tasty.navigation.RecipeArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	recipesRepository: RecipeRepository,
	private val userDataRepository: UserDataRepository
) : ViewModel() {

	private val recipeArgs: RecipeArgs = RecipeArgs(savedStateHandle)

	private var recipeId: Int = recipeArgs.recipeId

	private val recipeFlow: Flow<Recipe> = recipesRepository.getRecipeFlow(recipeId)
	private val userDataFlow: Flow<UserData?> = userDataRepository.getUserDataFlow()

	val uiState = recipeFlow.combine(userDataFlow) { recipe, userData ->
		if (userData != null) {
			RecipeUiState.Success(
				recipe = recipe,
				isRecipeBookmarked = userData.bookmarkedRecipes.contains(recipeId)
			)
		} else {
			RecipeUiState.Error
		}
	}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(),
			initialValue = RecipeUiState.Loading,
		)

	fun updateBookmarkedList(recipeId: Int, isBookmarked: Boolean) {
		viewModelScope.launch {
			val userData = userDataRepository.getUserData() ?: return@launch
			val mutableBookmarkedRecipesSet = userData.bookmarkedRecipes.toMutableSet()
			if (isBookmarked) {
				mutableBookmarkedRecipesSet.add(recipeId)
			} else {
				mutableBookmarkedRecipesSet.remove(recipeId)
			}
			userDataRepository.updateBookmarkedRecipes(
				userData.copy(
					bookmarkedRecipes = mutableBookmarkedRecipesSet
				)
			)
		}
	}
}

sealed interface RecipeUiState {
	data class Success(
		val recipe: Recipe,
		val isRecipeBookmarked: Boolean
	) : RecipeUiState

	data object Error : RecipeUiState
	data object Loading : RecipeUiState
}
