package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.model.RatingEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.rating.RatingRepo
import com.example.githubclient.domain.repo.users.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class UsersListPresenter() : UsersListContract.Presenter() {
    private var compositeDisposable = CompositeDisposable()
    private val usersList: MutableList<UserEntity> = emptyList<UserEntity>().toMutableList()

    private val ratingRepo: RatingRepo by inject(RatingRepo::class.java)
    private val usersRepo: UsersRepo by inject(UsersRepo::class.java)
    private val router: Router by inject(Router::class.java)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
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