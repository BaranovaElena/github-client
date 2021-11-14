package com.example.githubclient

import android.app.Application
import com.example.githubclient.domain.di.*

class App : Application() {

    companion object {
        lateinit var instance: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val daggerAppComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .ratingModule(RatingModule(this))
            .usersModule(UsersModule(this))
            .build()
    }
}