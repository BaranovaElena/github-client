package com.example.githubclient.domain.repo.rating

import com.example.githubclient.domain.model.RatingEntity
import io.reactivex.Observable

interface RatingRepo {
    val rating: Observable<List<RatingEntity>>
}