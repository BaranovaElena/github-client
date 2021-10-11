package com.example.githubclient.domain.impl

import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo

class UsersRepoDummyImpl : UsersRepo {
    private val usersList = listOf(
        UserEntity("login1"),
        UserEntity("login2"),
        UserEntity("login3"),
        UserEntity("login4"),
        UserEntity("login5")
    )

    override fun getUsers() : List<UserEntity> {
        return usersList
    }
}