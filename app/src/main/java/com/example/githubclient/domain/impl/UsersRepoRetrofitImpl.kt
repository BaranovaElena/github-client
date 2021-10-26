package com.example.githubclient.domain.impl

import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import io.reactivex.Observable
import retrofit2.Callback

class UsersRepoRetrofitImpl(private val service: UsersRetrofitService) : UsersRepo {
    override val users: Observable<List<GithubUserEntity>>
        get() = service.getUsers()
}