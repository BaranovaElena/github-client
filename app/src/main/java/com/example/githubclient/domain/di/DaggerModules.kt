package com.example.githubclient.domain.di

import android.content.Context
import androidx.room.Room
import com.example.githubclient.BuildConfig
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import com.example.githubclient.domain.repo.rating.*
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.domain.repo.repos.ReposRepoRetrofitImpl
import com.example.githubclient.domain.repo.repos.ReposRetrofitService
import com.example.githubclient.domain.repo.users.*
import com.example.githubclient.ui.MainActivity
import com.example.githubclient.ui.userdetail.UserDetailPresenter
import com.example.githubclient.ui.userslist.UsersListPresenter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides @Singleton
    fun provideReposRetrofitService(retrofit: Retrofit): ReposRetrofitService =
        retrofit.create(ReposRetrofitService::class.java)

    @Provides @Singleton
    fun provideReposRepo(retrofitService: ReposRetrofitService): ReposRepo =
        ReposRepoRetrofitImpl(retrofitService)

    @Provides @Singleton
    fun provideUsersRetrofitService(retrofit: Retrofit): UsersRetrofitService =
        retrofit.create(UsersRetrofitService::class.java)

    @Provides @Singleton
    fun provideUsersRepo(retrofitService: UsersRetrofitService): UsersRepoRetrofit =
        UsersRepoRetrofit(retrofitService)
}

@Module
class RatingModule(val context: Context) {
    @Provides
    fun providesContext(): Context = context

    @Provides @Singleton
    fun provideRatingBus(): RatingEventBus = RatingEventBus

    @Provides @Singleton
    @Named("rating")
    fun provideRatingDbName(): String = "rating.db"

    @Provides @Singleton
    fun provideRatingDao(context: Context, @Named("rating") ratingDbName: String): RatingDao =
        Room.databaseBuilder(context, RatingDb::class.java, ratingDbName).build().ratingDao()

    @Provides
    fun provideRatingRepo(bus: RatingEventBus, dao: RatingDao): RatingRepo =
        RatingRepoRoomImpl(bus, dao)

    @Provides
    @Named("dummy")
    fun provideDummyRatingRepo(bus: RatingEventBus): RatingRepo =
        RatingRepoDummyImpl(bus)
}

@Module
class UsersModule(val context: Context) {
    @Provides @Singleton
    @Named("users")
    fun provideUsersDbName(): String = "users.db"

    @Provides @Singleton
    fun provideUsersDao(context: Context, @Named("users") usersDbName: String): UsersDao =
        Room.databaseBuilder(context, UsersDb::class.java, usersDbName).build().usersDao()

    @Provides @Singleton
    fun provideRoomUsersRepo(dao: UsersDao): UsersRepoRoom = UsersRepoRoom(dao)

    @Provides @Singleton
    fun provideConnectionStatus(context: Context): NetworkConnectionStatus =
        NetworkConnectionStatus(context)

    @Provides @Singleton
    fun provideUsersRepo(
        status: NetworkConnectionStatus,
        webRepo: UsersRepoRetrofit,
        dbRepo: UsersRepoRoom
    ): UsersRepo =
        UsersRepoCombinedImpl(status, webRepo, dbRepo)

    @Provides @Singleton
    @Named("dummy")
    fun provideDummyUsersRepo(): UsersRepo = UsersRepoDummyImpl()
}

@Module
class NavModule {
    @Provides @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides @Singleton
    fun provideNavHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()
}

@Singleton @Component(modules = [
    RetrofitModule::class,
    RatingModule::class,
    UsersModule::class,
    NavModule::class]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(presenter: UsersListPresenter)
    fun inject(presenter: UserDetailPresenter)
}