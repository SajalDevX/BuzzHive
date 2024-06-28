package com.example.instagram.android.account.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.android.common.dummy_data.SamplePost
import com.example.instagram.android.common.dummy_data.Profile
import com.example.instagram.android.common.dummy_data.samplePosts
import com.example.instagram.android.common.dummy_data.sampleProfiles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel(){

    var userInfoUiState by mutableStateOf(UserInfoUiState())
        private set

    var profilePostsUiState by mutableStateOf(ProfilePostsUiState())
        private set


    fun fetchProfile(userId: Int){
        viewModelScope.launch {
            delay(1000)

            userInfoUiState = userInfoUiState.copy(
                isLoading = false,
                profile = sampleProfiles.find { it.id == userId }
            )

            profilePostsUiState = profilePostsUiState.copy(
                isLoading = false,
                samplePosts = samplePosts
            )
        }
    }
}

data class UserInfoUiState(
    val isLoading: Boolean = true,
    val profile: Profile? = null,
    var errorMessage: String? = null
)

data class ProfilePostsUiState(
    val isLoading: Boolean = true,
    val samplePosts: List<SamplePost> = listOf(),
    var errorMessage: String? = null
)