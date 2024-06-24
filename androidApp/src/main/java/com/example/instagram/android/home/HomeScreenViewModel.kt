package com.example.instagram.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.android.common.dummy_data.SamplePost
import com.example.instagram.android.common.dummy_data.sampleSamplePosts
import com.example.instagram.common.domain.model.FollowsUser
import com.example.instagram.follows.domain.usecase.GetFollowableUsersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.instagram.common.util.Result
import com.example.instagram.follows.domain.usecase.FollowOrUnfollowUseCase

class HomeScreenViewModel(
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getFollowableUsersUseCase: GetFollowableUsersUseCase
) : ViewModel() {

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set
    var postFeedUiState by mutableStateOf(PostFeedUiState())
        private set
    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    init {
        fetchData()
    }


    private fun fetchData() {
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)

        viewModelScope.launch {

            delay(1000)

            val users = getFollowableUsersUseCase()
            handleOnBoardingResult(users)
            postFeedUiState = postFeedUiState.copy(
                isLoading = false,
                samplePosts = sampleSamplePosts
            )
            homeRefreshState = homeRefreshState.copy(isRefreshing = false)
        }
    }

    private fun handleOnBoardingResult(result: Result<List<FollowsUser>>) {
        when (result) {
            is Result.Error -> Unit
            is Result.Success -> (
                    result.data?.let { followsUsers ->
                        onBoardingUiState = onBoardingUiState.copy(
                            shouldShowOnBoarding = followsUsers.isNotEmpty(),
                            followableUsers = followsUsers
                        )
                    }
                    )
        }
    }

    private fun followUser(followsUser: FollowsUser) {
        viewModelScope.launch {
            val result = followOrUnfollowUseCase(
                followedUserId = followsUser.id,
                shouldFollow = !followsUser.isFollowing
            )
            onBoardingUiState = onBoardingUiState.copy(
                followableUsers = onBoardingUiState.followableUsers.map {
                    if (it.id == followsUser.id) {
                        it.copy(isFollowing = !followsUser.isFollowing)
                    } else it
                }
            )
            when (result) {
                is Result.Error -> {
                    onBoardingUiState = onBoardingUiState.copy(
                        followableUsers = onBoardingUiState.followableUsers.map {
                            if (it.id == followsUser.id) {
                                it.copy(isFollowing = followsUser.isFollowing)
                            } else it
                        }
                    )
                }

                is Result.Success -> Unit
            }
        }
    }

    private fun dismissOnBoarding() {
        val hasFollowing = onBoardingUiState.followableUsers.any { it.isFollowing }
        if (!hasFollowing) {
            //TODO()
        } else {
            onBoardingUiState =
                onBoardingUiState.copy(shouldShowOnBoarding = false, followableUsers = emptyList())
        }
    }

    fun onUiAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.FollowUserAction -> followUser(uiAction.user)
            HomeUiAction.LoadMorePostsAction -> Unit
            is HomeUiAction.PostLikeAction -> Unit
            HomeUiAction.RefreshAction -> fetchData()
            HomeUiAction.RemoveOnBoardingAction -> dismissOnBoarding()
        }
    }

}

data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null
)

data class OnBoardingUiState(
    val shouldShowOnBoarding: Boolean = false,
    val followableUsers: List<FollowsUser> = listOf(),
)

data class PostFeedUiState(
    val isLoading: Boolean = false,
    val samplePosts: List<SamplePost> = listOf(),
    val loadingErrorMessage: String? = null
)

sealed interface HomeUiAction {
    data class FollowUserAction(val user: FollowsUser) : HomeUiAction
    data class PostLikeAction(val post: com.example.instagram.common.domain.model.Post) :
        HomeUiAction

    data object RemoveOnBoardingAction : HomeUiAction
    data object RefreshAction : HomeUiAction
    data object LoadMorePostsAction : HomeUiAction
}