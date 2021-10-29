package com.example.githubclient.domain.repo.rating

import com.example.githubclient.domain.bus.DislikeEvent
import com.example.githubclient.domain.bus.LikeEvent
import com.example.githubclient.domain.bus.RatingEventBus
import com.example.githubclient.domain.model.RatingEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val BUS_DELAY = 2L

class RatingRepoRoomImpl(ratingBus: RatingEventBus, private val ratingDao: RatingDao) :
    RatingRepo {
    override val rating: Observable<List<RatingEntity>>
        get() = ratingDao.getRatingList()

    override fun putNewUser(rating: RatingEntity) {
        ratingDao.put(rating).subscribeOn(Schedulers.io()).subscribe()
    }

    init {
        ratingBus.get()
            .delay(BUS_DELAY, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                val newRating = RatingEntity(it.user.githubUser.login, it.user.rating)
                when (it) {
                    is LikeEvent -> newRating.rating++
                    is DislikeEvent -> newRating.rating--
                }
                ratingDao.update(newRating).subscribeOn(Schedulers.io()).subscribe()
            }
    }
}