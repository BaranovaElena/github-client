package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.users.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UsersListPresenter(
    private val router: Router,
    private val usersRepo: UsersRepo,
    private val ratingRepo: RatingRepo
) : UsersListContract.Presenter() {
    private var compositeDisposable = CompositeDisposable()
    private val usersList: MutableList<UserEntity> =
        emptyList<UserEntity>().toMutableList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUsers()
        setRating()
    }

    private fun setRating() {
        compositeDisposable.add(
            ratingRepo.rating
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { ratings ->
                    kotlin.run {
                        for (user in usersList) {
                            for (item in ratings) {
                                if (item.login == user.githubUser.login) {
                                    user.rating = item.rating
                                    break
                                }
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
    }

    override fun onUserItemClicked(user: UserEntity) {
        router.navigateTo(Screens.userDetail(user))
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}