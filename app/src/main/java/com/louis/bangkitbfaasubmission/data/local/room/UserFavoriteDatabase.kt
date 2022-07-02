package com.louis.bangkitbfaasubmission.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity

@Database(entities = [UserFavoriteEntity::class], version = 1, exportSchema = false)
abstract class UserFavoriteDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        @Volatile
        private var instance: UserFavoriteDatabase? = null
        fun getInstance(context: Context) : UserFavoriteDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserFavoriteDatabase::class.java, "UserFavorite.db"
                ).build()
            }
    }
}