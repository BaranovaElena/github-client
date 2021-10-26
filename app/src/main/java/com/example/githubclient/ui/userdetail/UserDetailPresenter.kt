package com.example.githubclient.ui.userdetail

import android.util.Log
import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserDetailPresenter(private val ratingBus: RatingEventBus, private val repo: UsersRepo) :
    UserDetailContract.Presenter() {
    private var likeCounter = 0
    private var dislikeCounter = 0
    private var compositeDisposable = CompositeDisposable()

    override fun onViewCreated(currentUserName: String) {
        compositeDisposable.add(
            repo.users
                .subscribeOn(Schedulers.io())
                .doOnNext { Log.d("@@@", "repo.users.onNext detail " + Thread.currentThread().name) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { users ->
                    kotlin.run {
                        for (item in users) {
                            if (item.name == currentUserName) {
                                viewState.showRating(item.rating)
                            }
                        }
                    }
                }
        )
    }

    override fun onLikeClicked(user: UserEntity) {
        viewState.showLikeCount(++likeCounter)
        ratingBus.post(LikeEvent(user))
        viewState.showRating(user.rating)
    }

    override fun onDislikeClicked(user: UserEntity) {
        viewState.showDislikeCount(++dislikeCounter)
        ratingBus.post(DislikeEvent(user))
        viewState.showRating(user.rating)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}