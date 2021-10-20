package com.example.githubclient.ui.userdetail

import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.UserEntity

class UserDetailPresenter(private val ratingBus: RatingEventBus): UserDetailContract.Presenter() {
    private var likeCounter = 0
    private var dislikeCounter = 0

    override fun onLikeClicked(user: UserEntity) {
        viewState.showLikeCount(++likeCounter)
        ratingBus.post(LikeEvent(user))
    }

    override fun onDislikeClicked(user: UserEntity) {
        viewState.showDislikeCount(++dislikeCounter)
        ratingBus.post(DislikeEvent(user))
    }
}