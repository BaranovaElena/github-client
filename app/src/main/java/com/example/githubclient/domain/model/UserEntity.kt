package com.example.githubclient.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val githubEntity: GithubUserEntity = GithubUserEntity(),
    var rating: Int = 0
) : Parcelable
