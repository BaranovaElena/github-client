package com.example.githubclient.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val name: String = ""
) : Parcelable
