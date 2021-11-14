package com.example.githubclient.domain.repo.rating

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.githubclient.domain.model.RatingEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface RatingDao {
    @Query("SELECT * FROM rating")
    fun getRatingList(): Observable<List<RatingEntity>>

    @Insert
    fun put(ratingEntity: RatingEntity) : Completable

    @Update
    fun update(ratingEntity: RatingEntity) : Completable
}