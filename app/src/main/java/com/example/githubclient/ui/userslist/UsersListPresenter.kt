package com.example.githubclient.ui.userslist

import android.util.Log
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UsersListPresenter(private val router: Router, private val repo: UsersRepo) :
    UsersListContract.Presenter() {
    private var compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUsers()
    }

    private fun setUsers() {
        compositeDisposable.add(
            repo.users
                .subscribeOn(Schedulers.io())
                .doOnNext { Log.d("@@@", "repo.users.onNext list " + Thread.currentThread().name) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users -> viewState.showUsersList(users) },
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