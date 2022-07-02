package com.louis.bangkitbfaasubmission.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: ArrayList<UserItem>
)

data class UserItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String
)

data class UserDetail(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("blog")
	val blog: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String

)
