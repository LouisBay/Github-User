package com.louis.bangkitbfaasubmission.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louis.bangkitbfaasubmission.data.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getAllFavoriteUser() = userRepository.FavoriteUserRepository().getUserFavorite()

    fun deleteAllFavoriteUser() {
        viewModelScope.launch {
            userRepository.FavoriteUserRepository().deleteAllUserFavorite()
        }
    }
}