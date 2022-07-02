package com.louis.bangkitbfaasubmission.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import com.louis.bangkitbfaasubmission.data.local.room.UserFavoriteDao
import com.louis.bangkitbfaasubmission.data.remote.retrofit.ApiService
import com.louis.bangkitbfaasubmission.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class UserRepository private constructor(
    private val apiService: ApiService,
    private val userFavoriteDao: UserFavoriteDao,
    private val dataStore: DataStore<Preferences>
){

    inner class SearchRepository {
        fun getSearchUser(query: String) = flow {
            emit(Result.Loading)
            apiService.getSearchUsers(query).items.let { emit(Result.Success(it)) }
        }.catch { emit(Result.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

        fun getFirstUsers() = flow {
            emit(Result.Loading)
            emit(Result.Success(apiService.getUsers()))
        }.catch { emit(Result.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    inner class DetailUserRepository {
        fun getUserData(username: String) = flow {
            emit(Result.Loading)
            emit(Result.Success(apiService.getUserDetail(username)))
        }.catch { emit(Result.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

        fun getUserFollowers(username: String) = flow {
            emit(Result.Loading)
            emit(Result.Success(apiService.getUserFollowers(username)))
        }.catch { emit(Result.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)

        fun getUserFollowing(username: String) = flow {
            emit(Result.Loading)
            emit(Result.Success(apiService.getUserFollowing(username)))
        }.catch { emit(Result.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    inner class FavoriteUserRepository {
        fun getUserFavorite() = userFavoriteDao.getAllFavoriteUsers()

        fun checkFavorite(username: String) = userFavoriteDao.isUserFavorite(username)

        suspend fun deleteAllUserFavorite() { userFavoriteDao.deleteAllFavorite() }

        suspend fun deleteUserFromFavorite(user: UserFavoriteEntity) { userFavoriteDao.deleteFromFavorite(user) }

        suspend fun insertUserToFavorite(user: UserFavoriteEntity) { userFavoriteDao.insertToFavorite(user) }
    }

    inner class SettingRepository {
        private val KEY_THEME = booleanPreferencesKey("theme_settings")

        fun getDarkThemeSettings() = dataStore.data.map { pref ->
            pref[KEY_THEME] ?: false
        }

        suspend fun saveDarkThemeSettings(isDarkThemeActive: Boolean) {
            dataStore.edit { pref ->
                pref[KEY_THEME] = isDarkThemeActive
            }
        }
    }


    companion object {

        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userFavoriteDao: UserFavoriteDao,
            dataStore: DataStore<Preferences>
        ) : UserRepository {
            return instance ?: synchronized(this) {
                UserRepository(apiService, userFavoriteDao, dataStore).also {
                    instance = it
                }
            }
        }
    }
}