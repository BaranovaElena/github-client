package com.example.githubclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val name: String = "",
    val rating: Int = 0
) : Parcelable
