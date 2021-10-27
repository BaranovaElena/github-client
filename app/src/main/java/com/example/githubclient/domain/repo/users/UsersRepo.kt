package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable

interface UsersRepo {
    val users: Observable<List<GithubUserEntity>>
}