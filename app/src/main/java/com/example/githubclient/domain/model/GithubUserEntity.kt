package com.example.githubclient.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class GithubUserEntity(
    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String = "",

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String = "",

    @SerializedName("html_url")
    @ColumnInfo(name = "htmlUrl")
    val htmlUrl: String = "",

    @SerializedName("repos_url")
    @ColumnInfo(name = "reposUrl")
    val reposUrl: String = ""
) : Parcelable
