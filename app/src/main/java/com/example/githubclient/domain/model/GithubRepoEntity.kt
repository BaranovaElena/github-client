package com.example.githubclient.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepoEntity(
    val name: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
    val description: String = "",
    @SerializedName("created_at") var createdAt: String = "",
    @SerializedName("updated_at") var updatedAt: String = "",
    val language: String = "",
    @SerializedName("forks_count") val forksCount: Int = 0,
    @SerializedName("stargazers_count") val stargazersCount: Int = 0
) : Parcelable
