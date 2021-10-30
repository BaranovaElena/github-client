package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UsersRepoRoom(private val usersDao: UsersDao) {
    val users: Observable<List<GithubUserEntity>>
        get() = usersDao.getUsersList()

    fun putNewUsers(users: List<GithubUserEntity>) {
        for (user in users) {
            usersDao.put(user).subscribeOn(Schedulers.io()).subscribe()
        }
    }

    fun clearData() {
        usersDao.clear().subscribeOn(Schedulers.io()).subscribe()
    }
}