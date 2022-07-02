package com.louis.bangkitbfaasubmission.api

import com.louis.bangkitbfaasubmission.Api.API_TOKEN
import com.louis.bangkitbfaasubmission.model.SearchResponse
import com.louis.bangkitbfaasubmission.model.UserDetail
import com.louis.bangkitbfaasubmission.model.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserDetail(
        @Path("username") username: String
    ) : Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<UserItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<UserItem>>

    @GET("users")
    @Headers("Authorization: token $API_TOKEN")
    fun getUsers() : Call<ArrayList<UserItem>>

}