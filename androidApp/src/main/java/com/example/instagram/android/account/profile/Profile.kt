package com.example.instagram.android.account.profile

import androidx.compose.runtime.Composable
import com.example.instagram.android.destinations.EditProfileDestination
import com.example.instagram.android.destinations.FollowersDestination
import com.example.instagram.android.destinations.FollowingDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun Profile(
    userId: Int,
    navigator: DestinationsNavigator
) {

    val viewModel: ProfileViewModel = koinViewModel()

    ProfileScreen(
        userInfoUiState = viewModel.userInfoUiState,
        profilePostsUiState = viewModel.profilePostsUiState,
        onButtonClick = { navigator.navigate(EditProfileDestination(userId)) },
        onFollowersClick = { navigator.navigate(FollowersDestination(userId))},
        onFollowingClick = { navigator.navigate(FollowingDestination(userId))},
        onPostClick = {},
        onLikeClick = {},
        onCommentClick = {},
        fetchData = { viewModel.fetchProfile(userId)}
    )
}