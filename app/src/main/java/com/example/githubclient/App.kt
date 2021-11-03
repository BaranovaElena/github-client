package com.example.githubclient

import android.app.Application
import com.example.githubclient.domain.di.navModule
import com.example.githubclient.domain.di.retrofitModule
import com.example.githubclient.domain.di.ratingModule
import com.example.githubclient.domain.di.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(retrofitModule, ratingModule, usersModule, navModule)
        }
    }
}