package com.example.githubclient.domain.repo.rating

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubclient.domain.model.RatingEntity

@Database(entities = [RatingEntity::class], version = 1)
abstract class RatingDb : RoomDatabase() {
    abstract fun ratingDao(): RatingDao
}