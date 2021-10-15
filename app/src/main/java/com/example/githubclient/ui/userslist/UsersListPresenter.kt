package com.example.githubclient.ui.userslist

import com.example.githubclient.domain.impl.UsersRepoDummyImpl
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import com.example.githubclient.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.Disposable

class UsersListPresenter(private val router: Router) : UsersListContract.Presenter() {
    private val repo: UsersRepo = UsersRepoDummyImpl()
    private var disposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setUsers()
    }

    override fun onViewCreated() {
        setUsers()
    }

    private fun setUsers() {
        disposable = repo.users.subscribe {
            viewState.showUsersList(it)
        }
    }

    override fun onUserItemClicked(user: UserEntity) {
        router.navigateTo(Screens.userDetail(user))
    }

    override fun onDestroy() {
        disposable?.takeIf { !it.isDisposed }?.dispose()
        disposable = null
        super.onDestroy()
    }
}