package com.louis.bangkitbfaasubmission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louis.bangkitbfaasubmission.data.UserRepository
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import com.louis.bangkitbfaasubmission.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _result = MutableLiveData<Result<ArrayList<UserItem>>>()
    val result: LiveData<Result<ArrayList<UserItem>>> = _result

    private val _isCalled = MutableLiveData(false)
    val isCalled: LiveData<Boolean> = _isCalled

    fun getUserFollowers(username: String) {
        _isCalled.value = true
        viewModelScope.launch {
            _result.value = Result.Loading
            userRepository.DetailUserRepository().getUserFollowers(username).collect {
                _result.value = it
            }
        }
    }

    fun getUserFollowing(username: String) {
        _isCalled.value = true
        viewModelScope.launch {
            _result.value = Result.Loading
            userRepository.DetailUserRepository().getUserFollowing(username).collect {
                _result.value = it
            }
        }
    }

}