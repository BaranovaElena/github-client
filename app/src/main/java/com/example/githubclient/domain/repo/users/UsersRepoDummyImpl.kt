package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

private const val USERS_OBSERVABLE_DELAY = 2L

class UsersRepoDummyImpl : UsersRepo {
    private val usersList = listOf(
        GithubUserEntity("login1"),
        GithubUserEntity("login2"),
        GithubUserEntity("login3"),
        GithubUserEntity("login4"),
        GithubUserEntity("login5"),
        GithubUserEntity("login6"),
        GithubUserEntity("login7"),
        GithubUserEntity("login8"),
        GithubUserEntity("login9"),
        GithubUserEntity("login10")
    )
    private val behaviorSubject = BehaviorSubject.createDefault(usersList)

    override val users: Observable<List<GithubUserEntity>>
        get() = behaviorSubject.delay(USERS_OBSERVABLE_DELAY, TimeUnit.SECONDS)
}