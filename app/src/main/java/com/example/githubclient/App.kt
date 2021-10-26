package com.example.githubclient

import android.app.Application
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.impl.UsersRepoRetrofitImpl
import com.example.githubclient.domain.impl.UsersRetrofitService
import com.example.githubclient.domain.repo.UsersRepo
import com.github.terrakok.cicerone.Cicerone
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    val ratingBus = RatingEventBus

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    private val usersService: UsersRetrofitService by lazy {
        retrofit.create(UsersRetrofitService::class.java)
    }

    val repo: UsersRepo = UsersRepoRetrofitImpl(usersService)
}