package com.example.githubclient.domain.impl

import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface UsersRetrofitService {
    @GET("/users")
    fun getUsers(): Observable<List<GithubUserEntity>>
}