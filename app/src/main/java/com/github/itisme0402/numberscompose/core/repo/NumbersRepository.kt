package com.github.itisme0402.numberscompose.core.repo

import com.github.itisme0402.numberscompose.core.NumberInfo
import kotlinx.coroutines.flow.Flow

interface NumbersRepository {
    suspend fun getFact(number: Int): NumberInfo
    suspend fun getRandomNumberFact(): NumberInfo
    fun observeFactsHistory(): Flow<List<NumberInfo>>
}