package com.example.githubclient

import android.app.Application
import androidx.room.Room
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.di.retrofitModule
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import com.example.githubclient.domain.repo.rating.RatingDb
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.rating.RatingRepoRoomImpl
import com.example.githubclient.domain.repo.users.*
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    lateinit var ratingRepo: RatingRepo
    lateinit var usersRepo: UsersRepo

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(retrofitModule)
        }

        val ratingDb = Room.databaseBuilder(this, RatingDb::class.java, "rating.db").build()
        ratingRepo = RatingRepoRoomImpl(ratingBus, ratingDb.ratingDao())

        val usersDb = Room.databaseBuilder(this, UsersDb::class.java, "users.db").build()

        val usersRoomRepo = UsersRepoRoom(usersDb.usersDao())
        val usersWebRepo: UsersRepoRetrofit by inject()
        val connectionStatus = NetworkConnectionStatus(applicationContext)
        usersRepo = UsersRepoCombinedImpl(connectionStatus, usersWebRepo, usersRoomRepo)
    }

    val ratingBus = RatingEventBus
}