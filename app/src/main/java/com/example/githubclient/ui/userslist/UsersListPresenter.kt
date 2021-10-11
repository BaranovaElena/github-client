package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.impl.UsersRepoDummyImpl
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router

class UsersListPresenter(private val router: Router) : UsersListContract.Presenter() {
    private val repo: UsersRepo = UsersRepoDummyImpl()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUsers()
    }

    override fun onViewCreated() {
        setUsers()
    }

    private fun setUsers() {
        val list: List<UserEntity> = repo.getUsers()
        viewState.showUsersList(list)
    }

    override fun onUserItemClicked() {
        router.navigateTo(Screens.userDetail())
    }

    override fun onBackPressed() {
        router.exit()
    }
}