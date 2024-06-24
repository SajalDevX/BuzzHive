package com.example.instagram.android.home.onboarding

import com.example.instagram.android.common.dummy_data.SampleFollowsUser


data class OnBoardingUiState(
    val isLoading: Boolean = false,
    val users: List<SampleFollowsUser> = listOf(),
    val errorMessage: String? = null,
    val shouldShowOnBoarding: Boolean = false
)
