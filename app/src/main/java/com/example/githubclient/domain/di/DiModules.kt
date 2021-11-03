package com.example.githubclient.domain.di

import com.example.githubclient.BuildConfig
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.domain.repo.repos.ReposRepoRetrofitImpl
import com.example.githubclient.domain.repo.repos.ReposRetrofitService
import com.example.githubclient.domain.repo.users.UsersRepoRetrofit
import com.example.githubclient.domain.repo.users.UsersRetrofitService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    }

    single { get<Retrofit>().create(ReposRetrofitService::class.java) }
    single<ReposRepo> { ReposRepoRetrofitImpl(get()) }

    single { get<Retrofit>().create(UsersRetrofitService::class.java) }
    single { UsersRepoRetrofit(get()) }
}
