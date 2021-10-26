package com.example.githubclient.domain.repo

import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.model.UserEntity
import io.reactivex.Observable

interface UsersRepo {
    val users: Observable<List<GithubUserEntity>>
}