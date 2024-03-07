package com.github.itisme0402.numberscompose.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NumbersDao {
    @Query("SELECT * FROM ${LocalNumberInfo.TABLE_NAME} WHERE ${LocalNumberInfo.COL_ID} = :id")
    suspend fun getFact(id: Long): LocalNumberInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putFact(info: LocalNumberInfo): Long

    @Transaction
    @Query("SELECT * FROM ${LocalNumberInfo.TABLE_NAME} ORDER BY ${LocalNumberInfo.COL_ID} DESC")
    fun observeFactsHistory(): Flow<List<LocalNumberInfo>>
}