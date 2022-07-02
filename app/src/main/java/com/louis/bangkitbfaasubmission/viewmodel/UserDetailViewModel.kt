package com.louis.bangkitbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.louis.bangkitbfaasubmission.api.ApiConfig
import com.louis.bangkitbfaasubmission.model.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    private val _userData = MutableLiveData<UserDetail>()
    val userData: LiveData<UserDetail> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isCalled = MutableLiveData(false)
    val isCalled: LiveData<Boolean> = _isCalled

    fun getUserData(username: String) {
        _isLoading.value = true
        _isCalled.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) _userData.value = response.body()
                else Log.e(TAG, "onFailure: ${response.message()}")

                _isLoading.value = false
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")

                _isLoading.value = false
            }

        })
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}