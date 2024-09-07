package com.example.tasty.ui.screen.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tasty.data.local.model.Tag
import com.example.tasty.data.local.model.TagWithRecipes
import com.example.tasty.data.repository.recipe.RecipeRepository
import com.example.tasty.data.repository.tag.TagRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    recipesRepository: RecipeRepository,
    userDataRepository: UserDataRepository,
    tagRepository: TagRepository
) : ViewModel() {

    private val _tagIdState: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tagStateFlow: StateFlow<Tag?> = _tagIdState
        .flatMapLatest { tagId ->
            tagRepository.getTagForId(tagId)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    init {
        viewModelScope.launch {
            userDataRepository.updateOnboardingShownFlag()
        }
    }

    fun setInitialActiveTag(tagId: Int) {
        if (_tagIdState.value == null) {
            _tagIdState.update {
                tagId
            }
        }
    }

    fun updateActiveTag(tagId: Int) {
        _tagIdState.update {
            if (tagId == tagStateFlow.value?.tagId) null else tagId
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<TagWithRecipes>> = tagStateFlow
        .flatMapLatest { tag -> recipesRepository.getRecipesForTagPagedFlow(tag) }
        .cachedIn(viewModelScope)

    val tagsFlow = tagRepository.getTagsFlow()
}
