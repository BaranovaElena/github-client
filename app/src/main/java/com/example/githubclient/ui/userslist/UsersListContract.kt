package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.UserEntity
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
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onViewCreated()
        abstract fun onUserItemClicked(user: UserEntity)
    }
}