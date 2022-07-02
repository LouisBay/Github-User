package com.louis.bangkitbfaasubmission.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.louis.bangkitbfaasubmission.data.UserRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDarkThemeSettings() = userRepository.SettingRepository().getDarkThemeSettings().asLiveData()

    fun saveDarkThemeSettings(isDarkThemeActive: Boolean) {
        viewModelScope.launch {
            userRepository.SettingRepository().saveDarkThemeSettings(isDarkThemeActive)
        }
    }
}