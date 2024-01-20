package com.dinesh.android.kotlin.database.room.v0

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java, "user_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }

    }
}