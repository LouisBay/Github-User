package com.louis.bangkitbfaasubmission.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.louis.bangkitbfaasubmission.data.UserRepository
import com.louis.bangkitbfaasubmission.data.local.room.UserFavoriteDatabase
import com.louis.bangkitbfaasubmission.data.remote.retrofit.ApiConfig

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context) : UserRepository {
        val apiService = ApiConfig.getApiService()
        val dao = UserFavoriteDatabase.getInstance(context).userFavoriteDao()
        return UserRepository.getInstance(apiService, dao, context.dataStore)
    }
}