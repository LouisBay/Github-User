package com.louis.bangkitbfaasubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorite")
data class UserFavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int = 0
)