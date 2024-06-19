package com.example.instagram.android.home.onboarding

import com.example.instagram.android.common.dummy_data.FollowsUser


data class OnBoardingUiState(
    val isLoading: Boolean = false,
    val users: List<FollowsUser> = listOf(),
    val errorMessage: String? = null,
    val shouldShowOnBoarding: Boolean = false
)
