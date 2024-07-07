package com.example.instagram.di

import com.example.instagram.account.data.AccountApiService
import com.example.instagram.account.data.repository.ProfileRepositoryImpl
import com.example.instagram.account.domain.repository.ProfileRepository
import com.example.instagram.account.domain.usecase.GetProfileUseCase
import com.example.instagram.auth.data.AuthRepositoryImpl
import com.example.instagram.auth.data.AuthService
import com.example.instagram.auth.domain.repository.AuthRepository
import com.example.instagram.auth.domain.usecase.SignInUseCase
import com.example.instagram.auth.domain.usecase.SignUpUseCase
import com.example.instagram.common.data.remote.FollowsApiService
import com.example.instagram.common.data.remote.PostApiService
import com.example.instagram.common.util.provideDispatcher
import com.example.instagram.follows.data.FollowsRepositoryImpl
import com.example.instagram.follows.domain.FollowsRepository
import com.example.instagram.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.instagram.follows.domain.usecase.GetFollowableUsersUseCase
import com.example.instagram.post.data.PostRepositoryImpl
import com.example.instagram.post.domain.PostRepository
import com.example.instagram.post.domain.usecase.GetPostsUseCase
import com.example.instagram.post.domain.usecase.GetUserPostsUseCase
import com.example.instagram.post.domain.usecase.LikeOrUnlikePostUseCase
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}
private val postModule = module {
    factory { PostApiService() }
    factory { GetPostsUseCase() }
    factory { LikeOrUnlikePostUseCase() }
    factory { GetUserPostsUseCase() }

    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
}

private val followsModule = module {
    factory { FollowsApiService() }
    factory { GetFollowableUsersUseCase() }
    factory { FollowOrUnfollowUseCase() }

    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
}

private val accountModule = module {
    factory { AccountApiService() }
    factory { GetProfileUseCase() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}

fun getSharedModules() =
    listOf(authModule, utilityModule, platformModule, postModule, followsModule, accountModule)