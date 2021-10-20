package com.example.githubclient.ui.userdetail

import com.example.githubclient.domain.model.UserEntity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

class UserDetailContract {
    interface View: MvpView {
        @AddToEndSingle
        fun showLikeCount(count: Int)
        @AddToEndSingle
        fun showDislikeCount(count: Int)
    }

    abstract class Presenter: MvpPresenter<View>() {
        abstract fun onLikeClicked(user: UserEntity)
        abstract fun onDislikeClicked(user: UserEntity)
    }
}