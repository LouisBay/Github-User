package com.louis.bangkitbfaasubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.louis.bangkitbfaasubmission.api.ApiConfig
import com.louis.bangkitbfaasubmission.model.SearchResponse
import com.louis.bangkitbfaasubmission.model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUserSearch = MutableLiveData<ArrayList<UserItem>>()
    val listUserSearch: LiveData<ArrayList<UserItem>> = _listUserSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _isNotFound = MutableLiveData(false)
    val isNotFound: LiveData<Boolean> = _isNotFound

    private val _querySearch = MutableLiveData<String>()
    val querySearch: LiveData<String> = _querySearch

    init {
        getUsersFirst()
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        _isSearching.value = true
        _querySearch.value = query
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) _listUserSearch.value = response.body()?.items as ArrayList<UserItem>
                else Log.e(TAG, "onFailure: ${response.message()}")

                _isLoading.value = false
                _isNotFound.value = response.body()?.items?.size == 0
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")

                _isLoading.value = false
            }

        })
    }

    private fun getUsersFirst() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<ArrayList<UserItem>> {
            override fun onResponse(call: Call<ArrayList<UserItem>>, response: Response<ArrayList<UserItem>>) {
                if (response.isSuccessful) _listUserSearch.value = response.body()
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
        private const val TAG = "MainViewModel"
    }
}