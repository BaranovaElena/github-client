package com.example.githubclient.ui.userdetail

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

class UserDetailPresenter(
    private val router: Router,
    private val ratingBus: RatingEventBus,
    private val reposRepo: ReposRepo,
    private val ratingRepo: RatingRepo
) :
    UserDetailContract.Presenter() {
    private var likeCounter = 0
    private var dislikeCounter = 0
    private var compositeDisposable = CompositeDisposable()

    private fun loadRepos(url: String) {
        val disposable: Disposable = reposRepo.getRepos(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { repos ->
                for (repo in repos) {
                    repo.createdAt = repo.createdAt.replace('T', ' ')
                    repo.createdAt = repo.createdAt.removeSuffix("Z")
                    repo.updatedAt = repo.updatedAt.replace('T', ' ')
                    repo.updatedAt = repo.updatedAt.removeSuffix("Z")
                }
                viewState.showReposList(repos)
            }
        compositeDisposable.add(disposable)
    }

    override fun onViewCreated(githubUser: GithubUserEntity) {
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