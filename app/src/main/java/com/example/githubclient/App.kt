package com.example.githubclient

import android.app.Application
import androidx.room.Room
import com.example.githubclient.domain.di.retrofitModule
import com.example.githubclient.domain.di.ratingModule
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import com.example.githubclient.domain.repo.users.*
import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    lateinit var usersRepo: UsersRepo

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(retrofitModule, ratingModule)
        }

        val usersDb = Room.databaseBuilder(this, UsersDb::class.java, "users.db").build()

        val usersRoomRepo = UsersRepoRoom(usersDb.usersDao())
        val usersWebRepo: UsersRepoRetrofit by inject()
        val connectionStatus = NetworkConnectionStatus(applicationContext)
        usersRepo = UsersRepoCombinedImpl(connectionStatus, usersWebRepo, usersRoomRepo)
    }
}