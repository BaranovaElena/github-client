package com.example.githubclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingEntity(
    val login: String = "",
    var rating: Int = 0
) : Parcelable