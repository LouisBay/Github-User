package com.louis.bangkitbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.louis.bangkitbfaasubmission.api.ApiConfig
import com.louis.bangkitbfaasubmission.model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragmentViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<ArrayList<UserItem>>()
    val listUsers: LiveData<ArrayList<UserItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCalled = MutableLiveData(false)
    val isCalled: LiveData<Boolean> = _isCalled


    fun getUserFollowers(username: String) {
        _isLoading.value = true
        _isCalled.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<UserItem>> {
            override fun onResponse(call: Call<ArrayList<UserItem>>, response: Response<ArrayList<UserItem>>) {
                if (response.isSuccessful) _listUsers.value = response.body()
                else Log.e(TAG, response.message())
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.value = false
            }


        })
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        _isCalled.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<ArrayList<UserItem>> {
            override fun onResponse(call: Call<ArrayList<UserItem>>, response: Response<ArrayList<UserItem>>) {
                if (response.isSuccessful) _listUsers.value = response.body()
                else Log.e(TAG, response.message())
                _isLoading.value = false
            }

            override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                Log.e(TAG, t.message.toString())

                _isLoading.value = false
            }


        })
    }

    companion object {
        private const val TAG = "FollowFragmentViewModel"
    }

}