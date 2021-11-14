package com.example.githubclient.domain.repo.users

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubclient.domain.model.GithubUserEntity

@Database(entities = [GithubUserEntity::class], version = 1)
abstract class UsersDb : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}