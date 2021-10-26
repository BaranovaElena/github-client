package com.example.githubclient.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val githubEntity: GithubUserEntity = GithubUserEntity(),
    var rating: Int = 0
) : Parcelable

@Parcelize
data class GithubUserEntity(
    @SerializedName("id") val id: String = "",
    @SerializedName("login") val login: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = ""
) : Parcelable
