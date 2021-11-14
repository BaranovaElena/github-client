package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.model.GithubUserEntity
import com.example.githubclient.domain.repo.NetworkConnectionStatus
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class UsersRepoCombinedImpl(
    private val connectionStatus: NetworkConnectionStatus,
    private val webRepo: UsersRepoRetrofit,
    private val roomRepo: UsersRepoRoom
) : UsersRepo {
    private val sourceSubject = BehaviorSubject.create<DataSource>()

    override val users: Observable<List<GithubUserEntity>>
        get() {
            return connectionStatus.isOnline()
                .subscribeOn(Schedulers.io())
                .flatMap { isOnline ->
                    if (isOnline) {
                        webRepo.users.doOnNext {
                            roomRepo.clearData()
                                .subscribeOn(Schedulers.io())
                                .doOnComplete {
                                    roomRepo.putNewUsers(it)
                                    sourceSubject.onNext(DataSource.WEB_SOURCE)
                                }.subscribe()
                        }
                    } else {
                        roomRepo.users.doOnNext {
                            sourceSubject.onNext(DataSource.DATABASE_SOURCE)
                        }
                    }
                }
        }
    override val source: Observable<DataSource>
        get() = sourceSubject
}