package com.louis.bangkitbfaasubmission.data.remote.retrofit

import com.louis.bangkitbfaasubmission.BuildConfig
import com.louis.bangkitbfaasubmission.data.remote.response.SearchResponse
import com.louis.bangkitbfaasubmission.data.remote.response.UserDetail
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ) : SearchResponse

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ) : UserDetail

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ) : ArrayList<UserItem>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ) : ArrayList<UserItem>

    @GET("users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getUsers() : ArrayList<UserItem>

}