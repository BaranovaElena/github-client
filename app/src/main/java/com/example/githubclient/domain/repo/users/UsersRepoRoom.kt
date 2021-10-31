package com.example.githubclient.domain.repo.users

import android.util.Log
import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UsersRepoRoom(private val usersDao: UsersDao) {
    val users: Observable<List<GithubUserEntity>>
        get() = usersDao.getUsersList()

    fun putNewUsers(users: List<GithubUserEntity>) {
        usersDao.put(users).subscribeOn(Schedulers.io()).subscribe()
    }

    fun clearData() : Completable = usersDao.clear()
}