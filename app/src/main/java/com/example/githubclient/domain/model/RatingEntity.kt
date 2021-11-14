package com.example.githubclient.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "rating")
data class RatingEntity(
    @PrimaryKey @ColumnInfo(name = "login") val login: String = "",
    @ColumnInfo(name = "rating") var rating: Int = 0
) : Parcelable