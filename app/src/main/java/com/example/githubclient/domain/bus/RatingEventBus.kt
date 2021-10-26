package com.example.githubclient.domain.bus

import com.example.githubclient.domain.model.UserEntity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RatingEventBus {
    open class Event(val user: UserEntity)

    private val bus = PublishSubject.create<Event>()

    fun post(event: Event) {
        bus.onNext(event)
    }

    fun get(): Observable<Event> = bus
}

class LikeEvent(user: UserEntity): RatingEventBus.Event(user)
class DislikeEvent(user: UserEntity): RatingEventBus.Event(user)