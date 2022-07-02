package com.louis.bangkitbfaasubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteDao {

    @Query("SELECT * FROM user_favorite ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<UserFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorite(user: UserFavoriteEntity)

    @Delete
    suspend fun deleteFromFavorite(user: UserFavoriteEntity)

    @Query("DELETE FROM user_favorite")
    suspend fun deleteAllFavorite()

    @Query("SELECT EXISTS(SELECT * FROM user_favorite WHERE username = :username)")
    fun isUserFavorite(username: String) : Flow<Boolean>
}