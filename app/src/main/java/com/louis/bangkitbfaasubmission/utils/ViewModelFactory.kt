package com.louis.bangkitbfaasubmission.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.louis.bangkitbfaasubmission.data.UserRepository
import com.louis.bangkitbfaasubmission.di.Injection
import com.louis.bangkitbfaasubmission.viewmodel.*
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> { MainViewModel(userRepository) as T }

            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> { UserDetailViewModel(userRepository) as T }

            modelClass.isAssignableFrom(FollowViewModel::class.java) -> { FollowViewModel(userRepository) as T }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> { FavoriteViewModel(userRepository) as T }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> { SettingsViewModel(userRepository) as T }

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> { SplashViewModel(userRepository) as T }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}