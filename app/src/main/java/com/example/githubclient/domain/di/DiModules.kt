package com.example.githubclient.domain.di

import androidx.room.Room
import com.example.githubclient.BuildConfig
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.repo.rating.RatingDb
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.rating.RatingRepoRoomImpl
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

val ratingModule = module {
    single { RatingEventBus }

    single { Room.databaseBuilder(get(), RatingDb::class.java, "rating.db").build() }
    single { get<RatingDb>().ratingDao() }
    single<RatingRepo> { RatingRepoRoomImpl(get(), get()) }
}
