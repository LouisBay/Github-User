package com.louis.bangkitbfaasubmission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.louis.bangkitbfaasubmission.data.UserRepository

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDarkThemeSettings() = userRepository.SettingRepository().getDarkThemeSettings().asLiveData()
}