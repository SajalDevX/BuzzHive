package com.example.instagram.android.di

import com.dipumba.ytsocialapp.android.post.PostDetailViewModel
import com.example.instagram.android.MainActivityViewModel
import com.example.instagram.android.account.edit.EditProfileViewModel
import com.example.instagram.android.account.follows.FollowsViewModel
import com.example.instagram.android.account.profile.ProfileViewModel
import com.example.instagram.android.auth.login.LoginViewModel
import com.example.instagram.android.auth.signup.SignUpViewModel
import com.example.instagram.android.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { HomeScreenViewModel(get(),get()) }
    viewModel { PostDetailViewModel() }
    viewModel { ProfileViewModel() }
    viewModel { EditProfileViewModel() }
    viewModel { FollowsViewModel() }

}