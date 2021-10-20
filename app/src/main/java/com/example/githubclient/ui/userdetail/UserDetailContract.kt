package com.example.githubclient.ui.userdetail

import com.example.githubclient.domain.model.UserEntity
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

class UserDetailContract {
    interface View: MvpView {
        @AddToEndSingle
        fun showLikeCount(count: Int)
        @AddToEndSingle
        fun showDislikeCount(count: Int)
        @Skip
        fun showRating(rating: Int)
    }

    abstract class Presenter: MvpPresenter<View>() {
        abstract fun onLikeClicked(user: UserEntity)
        abstract fun onDislikeClicked(user: UserEntity)
    }
}