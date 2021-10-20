package com.example.githubclient.domain.impl

import android.util.Log
import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.UserEntity
import com.example.githubclient.domain.repo.UsersRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

private const val USERS_OBSERVABLE_DELAY = 2L
private const val BUS_DELAY = 2L

class UsersRepoDummyImpl(ratingBus: RatingEventBus) : UsersRepo {
    private val usersList = listOf(
        UserEntity("login1"),
        UserEntity("login2"),
        UserEntity("login3"),
        UserEntity("login4"),
        UserEntity("login5"),
        UserEntity("login6"),
        UserEntity("login7"),
        UserEntity("login8"),
        UserEntity("login9"),
        UserEntity("login10")
    )
    private val behaviorSubject = BehaviorSubject.createDefault(usersList)

    override val users: Observable<List<UserEntity>>
        get() = behaviorSubject.delay(USERS_OBSERVABLE_DELAY, TimeUnit.SECONDS)

    init {
        ratingBus.get()
            .delay(BUS_DELAY, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("@@@", "ratingBus.get().subscribe " + Thread.currentThread().name)
                when (it) {
                    is LikeEvent -> usersList[usersList.indexOf(it.user)].rating++
                    is DislikeEvent -> usersList[usersList.indexOf(it.user)].rating--
                }
                behaviorSubject.onNext(usersList)
            }
    }
}