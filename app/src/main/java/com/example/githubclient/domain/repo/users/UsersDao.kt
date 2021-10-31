package com.example.githubclient.domain.repo.users

import androidx.room.*
import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getUsersList(): Observable<List<GithubUserEntity>>

    @Insert
    fun put(users: List<GithubUserEntity>) : Completable

    @Query("DELETE FROM users")
    fun clear() : Completable
}