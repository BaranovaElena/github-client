package com.example.githubclient.domain.repo.repos

import com.example.githubclient.domain.model.GithubRepoEntity
import io.reactivex.Observable

class ReposRepoRetrofitImpl(private val service: ReposRetrofitService): ReposRepo {
    override fun getRepos(url: String): Observable<List<GithubRepoEntity>> = service.getRepos(url)
}