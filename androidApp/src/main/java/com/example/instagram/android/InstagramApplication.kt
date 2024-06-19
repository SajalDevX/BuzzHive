package com.example.instagram.android

import android.app.Application
import com.example.instagram.android.di.appModule
import com.example.instagram.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SocialApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SocialApplication)
            modules(appModule + getSharedModules() )
        }
    }
}