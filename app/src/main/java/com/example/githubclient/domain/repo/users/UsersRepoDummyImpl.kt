package com.example.githubclient.domain.repo.users

import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.GithubUserEntity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

private const val USERS_OBSERVABLE_DELAY = 2L
private const val BUS_DELAY = 2L

class UsersRepoDummyImpl(ratingBus: RatingEventBus) : UsersRepo {
    private val usersList = listOf(
        GithubUserEntity("login1"),
        GithubUserEntity("login2"),
        GithubUserEntity("login3"),
        GithubUserEntity("login4"),
        GithubUserEntity("login5"),
        GithubUserEntity("login6"),
        GithubUserEntity("login7"),
        GithubUserEntity("login8"),
        GithubUserEntity("login9"),
        GithubUserEntity("login10")
    )
    private val behaviorSubject = BehaviorSubject.createDefault(usersList)

    override val users: Observable<List<GithubUserEntity>>
        get() = behaviorSubject.delay(USERS_OBSERVABLE_DELAY, TimeUnit.SECONDS)

//    init {
//        ratingBus.get()
//            .delay(BUS_DELAY, TimeUnit.SECONDS)
//            .observeOn(Schedulers.io())
//            .subscribe {
//                Log.d("@@@", "ratingBus.get().subscribe " + Thread.currentThread().name)
//                when (it) {
//                    is LikeEvent -> usersList[usersList.indexOf(it.user)].rating++
//                    is DislikeEvent -> usersList[usersList.indexOf(it.user)].rating--
//                }
//                behaviorSubject.onNext(usersList)
//            }
//    }
}