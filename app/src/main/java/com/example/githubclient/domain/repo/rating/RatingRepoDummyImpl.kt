package com.example.githubclient.domain.repo.rating

import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.RatingEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

private const val BUS_DELAY = 2L

class RatingRepoDummyImpl(ratingBus: RatingEventBus) : RatingRepo {
    private var ratingList = mutableListOf(
        RatingEntity("mojombo", 5),
        RatingEntity("defunkt", 5),
        RatingEntity("pjhyett", 5),
        RatingEntity("wycats", 5),
        RatingEntity("ezmobius", 5),
        RatingEntity("ivey", 5),
        RatingEntity("evanphx", 5),
        RatingEntity("vanpelt", 5),
        RatingEntity("wayneeseguin", 5),
        RatingEntity("brynary", 5)
    )

    private val behaviorSubject = BehaviorSubject.createDefault<List<RatingEntity>>(ratingList)

    override val rating: Observable<List<RatingEntity>>
        get() = behaviorSubject

    override fun putNewUser(rating: RatingEntity) {
        ratingList.add(rating)
    }

    init {
        ratingBus.get()
            .delay(BUS_DELAY, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                when (it) {
                    is LikeEvent ->
                        changeRating(it.user.githubUser.login, true)
                    is DislikeEvent ->
                        changeRating(it.user.githubUser.login, false)
                }
                behaviorSubject.onNext(ratingList)
            }
    }

    private fun changeRating(login: String, inc: Boolean) {
        for (elem in ratingList) {
            if (login == elem.login) {
                when (inc) {
                    true -> elem.rating++
                    false -> elem.rating--
                }
                break
            }
        }
    }
}