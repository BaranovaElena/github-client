package com.example.githubclient.domain.repo.users

import android.util.Log
import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UsersRepoCombinedImpl(
    private val connectionStatus: NetworkConnectionStatus,
    private val webRepo: UsersRepoRetrofitImpl,
    private val roomRepo: UsersRepoRoom
) : UsersRepo {
    override val users: Observable<List<GithubUserEntity>>
        get() {
            return connectionStatus.isOnline()
                .subscribeOn(Schedulers.io())
                .flatMap { isOnline ->
                    if (isOnline) {
                        webRepo.users.doOnNext {
                            Log.d("@@@", "webRepo.users.doOnNext")
                            roomRepo.clearData()
                                .subscribeOn(Schedulers.io())
                                .doOnComplete {
                                    roomRepo.putNewUsers(it)
                                }.subscribe()
                        }
                    } else {
                        roomRepo.users
                    }
                }
        }
}