package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.UserEntity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

class UsersListContract {
    interface View : MvpView {
        @AddToEndSingle
        fun showUsersList(list: List<UserEntity>)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onViewCreated()
    }
}