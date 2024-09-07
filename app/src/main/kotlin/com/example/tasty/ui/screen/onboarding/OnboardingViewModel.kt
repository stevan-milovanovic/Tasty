package com.example.tasty.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasty.data.repository.tag.TagRepository
import com.example.tasty.data.repository.userData.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    private var _shouldProceedToHome: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shouldProceedToHome: StateFlow<Boolean> = _shouldProceedToHome

    init {
        updateShouldProceedToHomeBasedOnUserData()

        viewModelScope.launch {
            tagRepository.fetchTags()
        }
    }

    private fun updateShouldProceedToHomeBasedOnUserData() {
        viewModelScope.launch {
            _shouldProceedToHome.update {
                userDataRepository.getUserData()?.shouldHideOnboarding == true
            }
        }
    }
}
