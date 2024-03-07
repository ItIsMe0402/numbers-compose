package com.github.itisme0402.numberscompose.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LocalNumberInfo.TABLE_NAME)
data class LocalNumberInfo(
    @ColumnInfo(name = COL_NUMBER)
    val number: Int,
    @ColumnInfo(name = COL_FACT)
    val fact: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID)
    val id: Long = 0,
) {
    companion object {
        const val TABLE_NAME = "number_info"
        const val COL_NUMBER = "number"
        const val COL_FACT = "fact"
        const val COL_ID = "id"
    }
}