package com.example.githubclient.ui

import com.example.githubclient.domain.model.GithubRepoEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.ui.repodetail.RepoDetailFragment
import com.example.githubclient.ui.userdetail.UserDetailFragment
import com.example.githubclient.ui.userslist.UsersListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun usersList() = FragmentScreen { UsersListFragment.newInstance() }
    fun userDetail(user: UserEntity) = FragmentScreen { UserDetailFragment.newInstance(user) }
    fun repoDetail(repo: GithubRepoEntity) = FragmentScreen { RepoDetailFragment.newInstance(repo) }
}