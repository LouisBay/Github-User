package com.louis.bangkitbfaasubmission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louis.bangkitbfaasubmission.data.UserRepository
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import com.louis.bangkitbfaasubmission.data.remote.response.UserDetail
import com.louis.bangkitbfaasubmission.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _result = MutableLiveData<Result<UserDetail>>()
    val result: LiveData<Result<UserDetail>> = _result

    private val _isCalled = MutableLiveData(false)
    val isCalled: LiveData<Boolean> = _isCalled

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getUserData(username: String) {
        _isCalled.value = true
        viewModelScope.launch {
            _result.value = Result.Loading
            userRepository.DetailUserRepository().getUserData(username).collect {
                _result.value = it
            }
        }
    }

    fun insertUserToFavorite(user: UserFavoriteEntity) {
        viewModelScope.launch {
            _isFavorite.value = true
            userRepository.FavoriteUserRepository().insertUserToFavorite(user)
        }
    }

    fun deleteUserFromFavorite(user: UserFavoriteEntity) {
        viewModelScope.launch {
            _isFavorite.value = false
            userRepository.FavoriteUserRepository().deleteUserFromFavorite(user)
        }
    }

    fun checkFavorite(username: String) {
        viewModelScope.launch {
            userRepository.FavoriteUserRepository().checkFavorite(username).collect {
                _isFavorite.value = it
            }
        }
    }
}