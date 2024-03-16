package com.example.tasty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasty.data.repository.userData.UserDataRepository
import com.example.tasty.navigation.FOR_YOU_ROUTE
import com.example.tasty.navigation.ONBOARDING_ROUTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var _initialDestination: MutableStateFlow<String?> = MutableStateFlow(null)
    val initialDestination: StateFlow<String?> = _initialDestination

    init {
        getInitialDestination()
    }

    private fun getInitialDestination() {
        viewModelScope.launch {
            _initialDestination.value =
                if (userDataRepository.getUserData()?.shouldHideOnboarding == true)
                    FOR_YOU_ROUTE else ONBOARDING_ROUTE
        }
    }
}
