package com.example.githubclient.domain.impl

import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class UsersRepoDummyImpl : UsersRepo {
    private val usersList = listOf(
        UserEntity("login1"),
        UserEntity("login2"),
        UserEntity("login3"),
        UserEntity("login4"),
        UserEntity("login5"),
        UserEntity("login6"),
        UserEntity("login7"),
        UserEntity("login8"),
        UserEntity("login9"),
        UserEntity("login10")
    )

    override val users: Observable<List<UserEntity>>
        get() = Observable
            .interval(1000, TimeUnit.MILLISECONDS)
            .map { usersList }
}