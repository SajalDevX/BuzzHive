package com.example.instagram.di

import com.example.instagram.common.data.IOSUserPreferences
import com.example.instagram.common.data.createDatastore
import com.example.instagram.common.data.local.UserPreferences
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { IOSUserPreferences(get()) }

    single {
        createDatastore()
    }
}