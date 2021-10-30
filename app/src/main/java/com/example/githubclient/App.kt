package com.example.githubclient

import android.app.Application
import androidx.room.Room
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import com.example.githubclient.domain.repo.rating.RatingDb
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.rating.RatingRepoRoomImpl
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.domain.repo.repos.ReposRepoRetrofitImpl
import com.example.githubclient.domain.repo.repos.ReposRetrofitService
import com.example.githubclient.domain.repo.users.*
import com.github.terrakok.cicerone.Cicerone
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    lateinit var ratingRepo: RatingRepo
    lateinit var usersRepo: UsersRepo

    override fun onCreate() {
        super.onCreate()
        val ratingDb = Room.databaseBuilder(this, RatingDb::class.java, "rating.db").build()
        ratingRepo = RatingRepoRoomImpl(ratingBus, ratingDb.ratingDao())

        val usersDb = Room.databaseBuilder(this, UsersDb::class.java, "users.db").build()

        val usersRoomRepo = UsersRepoRoom(usersDb.usersDao())
        val usersWebRepo = UsersRepoRetrofitImpl(usersService)
        val connectionStatus = NetworkConnectionStatus(applicationContext)
        usersRepo = UsersRepoCombinedImpl(connectionStatus, usersWebRepo, usersRoomRepo)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    private val usersService: UsersRetrofitService by lazy {
        retrofit.create(UsersRetrofitService::class.java)
    }
    private val reposService: ReposRetrofitService by lazy {
        retrofit.create(ReposRetrofitService::class.java)
    }
    val ratingBus = RatingEventBus

    val reposRepo: ReposRepo = ReposRepoRetrofitImpl(reposService)
}