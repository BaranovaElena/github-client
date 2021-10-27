package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface UsersRetrofitService {
    @GET("/users")
    fun getUsers(): Observable<List<GithubUserEntity>>
}