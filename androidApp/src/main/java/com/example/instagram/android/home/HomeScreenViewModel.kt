package com.example.instagram.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram.android.common.util.Constants
import com.example.instagram.android.common.util.DefaultPagingManager
import com.example.instagram.android.common.util.PagingManager
import com.example.instagram.common.domain.model.FollowsUser
import com.example.instagram.common.domain.model.Post
import com.example.instagram.follows.domain.usecase.GetFollowableUsersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.instagram.common.util.Result
import com.example.instagram.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.instagram.post.domain.usecase.GetPostsUseCase
import com.example.instagram.post.domain.usecase.LikeOrUnlikePostUseCase
import kotlinx.coroutines.async

class HomeScreenViewModel(
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getFollowableUsersUseCase: GetFollowableUsersUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val likePostUseCase:LikeOrUnlikePostUseCase
) : ViewModel() {

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set
    var postFeedUiState by mutableStateOf(PostFeedUiState())
        private set
    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set
    private val pagingManager by lazy { createPagingManager() }

    init {
        fetchData()
    }


    private fun fetchData(){
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)

        viewModelScope.launch {
            delay(1000)

            val onboardingDeferred = async {
                getFollowableUsersUseCase()
            }

            pagingManager.apply {
                reset()
                loadItems()
            }
            handleOnBoardingResult(onboardingDeferred.await())
            homeRefreshState = homeRefreshState.copy(isRefreshing = false)
        }
    }
    private fun likeOrDislikePost(post:Post){
        viewModelScope.launch {
            val count = if(post.isLiked) -1 else +1
            postFeedUiState = postFeedUiState.copy(
                posts = postFeedUiState.posts.map {
                    if(it.postId==post.postId){
                        it.copy(
                            isLiked = !post.isLiked,
                            likesCount = post.likesCount.plus(count)
                        )
                    }else{
                        it
                    }
                }
            )
            val result = likePostUseCase(
                post = post,
            )
            when(result){
                is Result.Error -> {
                    postFeedUiState= postFeedUiState.copy(
                        posts = postFeedUiState.posts.map {
                            if(it.postId == post.postId) post else it
                        }
                    )
                }
                is Result.Success -> Unit
            }
        }
    }

    private fun loadMorePosts() {
        if (postFeedUiState.endReached) return
        viewModelScope.launch {
            pagingManager.loadItems()
        }
    }

    private fun createPagingManager(): PagingManager<Post>{
        return DefaultPagingManager(
            onRequest = {page ->
                getPostsUseCase(page, Constants.DEFAULT_REQUEST_PAGE_SIZE)
            },
            onSuccess = {posts, page ->
                postFeedUiState = if (posts.isEmpty()){
                    postFeedUiState.copy(endReached = true)
                }else{
                    if (page == Constants.INITIAL_PAGE_NUMBER){
                        postFeedUiState = postFeedUiState.copy(posts = emptyList())
                    }
                    postFeedUiState.copy(
                        posts = postFeedUiState.posts + posts,
                        endReached = posts.size < Constants.DEFAULT_REQUEST_PAGE_SIZE
                    )
                }
            },
            onError = {cause, page ->
                if (page == Constants.INITIAL_PAGE_NUMBER){
                    homeRefreshState = homeRefreshState.copy(
                        refreshErrorMessage = cause
                    )
                }else{
                    postFeedUiState = postFeedUiState.copy(
                        loadingErrorMessage = cause
                    )
                }
            },
            onLoadStateChange = {isLoading ->
                postFeedUiState = postFeedUiState.copy(
                    isLoading = isLoading
                )
            }
        )
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
            HomeUiAction.LoadMorePostsAction -> loadMorePosts()
            is HomeUiAction.PostLikeAction -> likeOrDislikePost(uiAction.post)
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
    val posts: List<Post> = listOf(),
    val loadingErrorMessage: String? = null,
    val endReached: Boolean = false
)

sealed interface HomeUiAction {
    data class FollowUserAction(val user: FollowsUser) : HomeUiAction
    data class PostLikeAction(val post: Post) : HomeUiAction
    data object RemoveOnBoardingAction : HomeUiAction
    data object RefreshAction : HomeUiAction
    data object LoadMorePostsAction : HomeUiAction
}