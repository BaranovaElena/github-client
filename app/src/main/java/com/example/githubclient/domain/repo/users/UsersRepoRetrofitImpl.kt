package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable

class UsersRepoRetrofitImpl(private val service: UsersRetrofitService) : UsersRepo {
    override val users: Observable<List<GithubUserEntity>>
        get() = service.getUsers()
}