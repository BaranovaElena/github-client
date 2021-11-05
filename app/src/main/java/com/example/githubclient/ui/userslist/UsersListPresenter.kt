package com.example.githubclient.ui.userslist

import com.example.githubclient.App
import com.example.githubclient.domain.model.RatingEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.users.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersListPresenter : UsersListContract.Presenter() {
    private var compositeDisposable = CompositeDisposable()
    private val usersList: MutableList<UserEntity> = emptyList<UserEntity>().toMutableList()

    @Inject lateinit var ratingRepo: RatingRepo
    @Inject lateinit var usersRepo: UsersRepo
    @Inject lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.instance.daggerAppComponent.inject(this)
        setUsers()
    }

    private fun setRating() {
        compositeDisposable.add(
            ratingRepo.rating
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ratings ->
                    kotlin.run {
                        for (user in usersList) {
                            var newUser = true
                            for (item in ratings) {
                                if (item.login == user.githubUser.login) {
                                    user.rating = item.rating
                                    newUser = false
                                    break
                                }
                            }
                            if (newUser) {
                                ratingRepo.putNewUser(RatingEntity(user.githubUser.login))
                            }
                        }
                        viewState.showUsersList(usersList)
                    }
                }
        )
    }

    private fun setUsers() {
        compositeDisposable.add(
            usersRepo.users
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users ->
                        for (user in users) {
                            usersList.add(UserEntity(user))
                        }
                        setRating()
                    },
                    { throwable -> viewState.showError(throwable.message.toString()) }
                )
        )
        compositeDisposable.add(
            usersRepo.source
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { source -> viewState.showWarning(source) },
                    { throwable -> viewState.showError(throwable.message.toString()) }
                )
        )
    }

    override fun onUserItemClicked(user: UserEntity) {
        router.navigateTo(Screens.userDetail(user))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}