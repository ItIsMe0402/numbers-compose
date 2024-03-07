package com.github.itisme0402.numberscompose.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalNumberInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun numbersDao(): NumbersDao

    companion object {
        fun create(appContext: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, "numbers").build()
        }
    }
}