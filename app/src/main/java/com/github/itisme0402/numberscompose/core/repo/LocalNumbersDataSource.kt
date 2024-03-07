package com.github.itisme0402.numberscompose.core.repo

import com.github.itisme0402.numberscompose.core.NumberInfo
import kotlinx.coroutines.flow.Flow

interface LocalNumbersDataSource {
    suspend fun getFactById(id: Long): NumberInfo?
    suspend fun putFact(numberInfo: NumberInfo): Long
    fun observeFactsHistory(): Flow<List<NumberInfo>>
}
