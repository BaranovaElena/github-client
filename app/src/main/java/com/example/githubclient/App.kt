package com.example.githubclient

import android.app.Application
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.domain.repo.repos.ReposRepoRetrofitImpl
import com.example.githubclient.domain.repo.repos.ReposRetrofitService
import com.example.githubclient.domain.repo.users.UsersRepoRetrofitImpl
import com.example.githubclient.domain.repo.users.UsersRetrofitService
import com.example.githubclient.domain.repo.users.UsersRepo
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

    val usersRepo: UsersRepo = UsersRepoRetrofitImpl(usersService)
    val reposRepo: ReposRepo = ReposRepoRetrofitImpl(reposService)
}