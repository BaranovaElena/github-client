package com.example.githubclient.domain.repo

import com.example.githubclient.domain.model.UserEntity

interface UsersRepo {
    fun getUsers() : List<UserEntity>
}