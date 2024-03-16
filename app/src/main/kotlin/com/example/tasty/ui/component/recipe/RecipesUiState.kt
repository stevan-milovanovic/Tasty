package com.example.tasty.ui.component.recipe

import androidx.paging.PagingData
import com.example.tasty.data.local.model.Recipe

sealed interface RecipesUiState {
    data class Success(
        val recipes: PagingData<Recipe>
    ) : RecipesUiState

    data object Error : RecipesUiState
    data object Loading : RecipesUiState
}
