package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.impl.UsersRepoDummyImpl
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo

class UsersListPresenter : UsersListContract.Presenter {
    private var view: UsersListContract.View? = null
    private val repo: UsersRepo = UsersRepoDummyImpl()

    override fun onAttach(view: UsersListContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

    override fun onViewCreated() {
        val list: List<UserEntity> = repo.getUsers()
        var logins = ""
        for (user in list) {
            logins = logins + user.name + "\n"
        }
        view?.showLoginsAsString(logins)
    }
}