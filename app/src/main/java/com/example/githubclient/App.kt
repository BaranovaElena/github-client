package com.example.githubclient

import android.app.Application
import com.example.githubclient.domain.bus.RatingEventBus
import com.github.terrakok.cicerone.Cicerone

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val ratingBus = RatingEventBus
}