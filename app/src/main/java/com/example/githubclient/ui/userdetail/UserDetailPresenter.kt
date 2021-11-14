package com.example.githubclient.ui.userdetail

import com.example.githubclient.App
import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.GithubRepoEntity
import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.repos.ReposRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailPresenter : UserDetailContract.Presenter() {
    private var likeCounter = 0
    private var dislikeCounter = 0
    private var compositeDisposable = CompositeDisposable()

    @Inject lateinit var reposRepo: ReposRepo
    @Inject lateinit var ratingBus: RatingEventBus
    @Inject lateinit var ratingRepo: RatingRepo
    @Inject lateinit var router: Router

    private fun loadRepos(url: String) {
        val disposable: Disposable = reposRepo.getRepos(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { repos ->
                    for (repo in repos) {
                        repo.createdAt = repo.createdAt.replace('T', ' ')
                        repo.createdAt = repo.createdAt.removeSuffix("Z")
                        repo.updatedAt = repo.updatedAt.replace('T', ' ')
                        repo.updatedAt = repo.updatedAt.removeSuffix("Z")
                    }
                    viewState.showReposList(repos)
                },
                {
                    viewState.showReposList(emptyList())
                    viewState.showLoadRepoError(it.message)
                })
        compositeDisposable.add(disposable)
    }

    override fun onViewCreated(githubUser: GithubUserEntity) {
        App.instance.daggerAppComponent.inject(this)

        loadRepos(githubUser.reposUrl)
        loadRating(githubUser.login)
    }

    private fun loadRating(login: String) {
        compositeDisposable.add(
            ratingRepo.rating
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ratings ->
                    kotlin.run {
                        for (item in ratings) {
                            if (item.login == login) {
                                viewState.showRating(item.rating)
                            }
                        }
                    }
                }
        )
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