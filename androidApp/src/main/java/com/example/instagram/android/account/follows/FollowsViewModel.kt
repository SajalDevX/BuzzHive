package com.example.instagram.android.account.follows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.android.common.dummy_data.FollowsUser
import com.example.instagram.android.common.dummy_data.sampleUsers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FollowsViewModel : ViewModel(){

    var uiState by mutableStateOf(FollowsUiState())
        private set

    fun fetchFollows(userId: Int, followsType: Int){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            delay(1000)

            uiState = uiState.copy(isLoading = false, followsUsers = sampleUsers)
        }
    }

}

data class FollowsUiState(
    val isLoading: Boolean = false,
    val followsUsers: List<FollowsUser> = listOf(),
    val errorMessage: String? = null
)