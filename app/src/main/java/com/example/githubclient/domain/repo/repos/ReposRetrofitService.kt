package com.example.githubclient.domain.repo.repos

import com.example.githubclient.domain.model.GithubRepoEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface ReposRetrofitService {
    @GET
    fun getRepos(@Url url: String): Observable<List<GithubRepoEntity>>
}