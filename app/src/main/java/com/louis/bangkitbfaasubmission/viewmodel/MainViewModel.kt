package com.louis.bangkitbfaasubmission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louis.bangkitbfaasubmission.utils.Result
import com.louis.bangkitbfaasubmission.data.UserRepository
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<ArrayList<UserItem>>>()
    val result: LiveData<Result<ArrayList<UserItem>>> = _result

    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _querySearch = MutableLiveData<String>()
    val querySearch: LiveData<String> = _querySearch

    init {
        firstUsers()
    }

    fun searchUser(query: String) {
        _isSearching.value = true
        _querySearch.value = query
        viewModelScope.launch {
            _result.value = Result.Loading
            userRepository.SearchRepository().getSearchUser(query).collect {
                _result.value = it
            }
        }
    }

    private fun firstUsers() {
        viewModelScope.launch {
            _result.value = Result.Loading
            userRepository.SearchRepository().getFirstUsers().collect {
                _result.value = it
            }
        }
    }
}