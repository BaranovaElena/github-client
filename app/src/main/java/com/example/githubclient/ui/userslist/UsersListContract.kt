package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.users.DataSource
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

class UsersListContract {
    interface View : MvpView {
        @AddToEndSingle
        fun showUsersList(list: List<UserEntity>)
        @Skip
        fun showError(msg: String)
        @Skip
        fun showWarning(source: DataSource)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onUserItemClicked(user: UserEntity)
    }
}