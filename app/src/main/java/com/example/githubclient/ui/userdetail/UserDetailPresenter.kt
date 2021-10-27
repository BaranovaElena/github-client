package com.example.githubclient.ui.userdetail

import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.GithubRepoEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserDetailPresenter(
    private val router: Router,
    private val ratingBus: RatingEventBus,
    private val repo: ReposRepo
) :
    UserDetailContract.Presenter() {
    private var likeCounter = 0
    private var dislikeCounter = 0
    private var compositeDisposable = CompositeDisposable()


    private fun loadRepos(url: String) {
        val disposable: Disposable = repo.getRepos(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos ->
                for (repo in repos) {
                    repo.createdAt = repo.createdAt.replace('T', ' ')
                    repo.createdAt = repo.createdAt.removeSuffix("Z")
                    repo.updatedAt = repo.updatedAt.replace('T', ' ')
                    repo.updatedAt = repo.updatedAt.removeSuffix("Z")
                }
                viewState.showReposList(repos) }
        compositeDisposable.add(disposable)
    }

    override fun onViewCreated(reposUrl: String) {
        loadRepos(reposUrl)
        /*compositeDisposable.add(
            repo.users
                .subscribeOn(Schedulers.io())
                .doOnNext { Log.d("@@@", "repo.users.onNext detail " + Thread.currentThread().name) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { users ->
                    kotlin.run {
                        for (item in users) {
                            if (item.login == currentUserName) {
                                viewState.showRating(item.rating)
                            }
                        }
                    }
                }
        )*/
    }

    override fun onRepoItemClicked(repo: GithubRepoEntity) {
        router.navigateTo(Screens.repoDetail(repo))
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