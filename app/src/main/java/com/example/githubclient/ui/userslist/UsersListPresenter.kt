package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.impl.UsersRepoDummyImpl
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class UsersListPresenter(private val router: Router) : UsersListContract.Presenter() {
    private val repo: UsersRepo = UsersRepoDummyImpl()
    private var compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUsers()
    }

    override fun onViewCreated() {
        setUsers()
    }

    private fun setUsers() {
        compositeDisposable.add(
            repo.users
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState.showUsersList(it) }
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