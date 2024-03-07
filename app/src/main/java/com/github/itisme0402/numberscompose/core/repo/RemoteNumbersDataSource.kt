package com.github.itisme0402.numberscompose.core.repo

import com.github.itisme0402.numberscompose.core.NumberInfo

interface RemoteNumbersDataSource {
    suspend fun getFact(number: Int): NumberInfo
    suspend fun getRandomNumberFact(): NumberInfo
}
