package com.example.githubclient.ui.userslist

import android.util.Log
import com.example.githubclient.domain.impl.UsersRepoRetrofitImpl
import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { users ->
                        val usersList: MutableList<UserEntity> = emptyList<UserEntity>().toMutableList()
                        for (user in users) {
                            usersList.add(UserEntity(user))
                        }
                        viewState.showUsersList(usersList)
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