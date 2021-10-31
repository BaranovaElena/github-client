package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable

class UsersRepoRetrofit(private val service: UsersRetrofitService) {
    val users: Observable<List<GithubUserEntity>>
        get() = service.getUsers()
}