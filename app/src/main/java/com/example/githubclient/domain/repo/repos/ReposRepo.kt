package com.example.githubclient.domain.repo.repos

import com.example.githubclient.domain.model.GithubRepoEntity
import io.reactivex.Observable

interface ReposRepo {
    fun getRepos(url: String) : Observable<List<GithubRepoEntity>>
}