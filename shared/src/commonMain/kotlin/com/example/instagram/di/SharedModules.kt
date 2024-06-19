package com.example.instagram.di

import com.example.instagram.auth.data.AuthRepositoryImpl
import com.example.instagram.auth.data.AuthService
import com.example.instagram.auth.domain.repository.AuthRepository
import com.example.instagram.auth.domain.usecase.SignInUseCase
import com.example.instagram.auth.domain.usecase.SignUpUseCase
import com.example.instagram.common.util.provideDispatcher
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(),get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(authModule, utilityModule,platformModule)