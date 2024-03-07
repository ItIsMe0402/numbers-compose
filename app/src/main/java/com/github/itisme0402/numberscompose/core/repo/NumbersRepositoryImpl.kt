package com.github.itisme0402.numberscompose.core.repo

import com.github.itisme0402.numberscompose.core.NumberInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NumbersRepositoryImpl @Inject constructor(
    private val localNumbersDataSource: LocalNumbersDataSource,
    private val remoteNumbersDataSource: RemoteNumbersDataSource,
) : NumbersRepository {
    override suspend fun getFact(number: Int): NumberInfo {
        val fact = remoteNumbersDataSource.getFact(number)
        val factId = localNumbersDataSource.putFact(fact)
        return localNumbersDataSource.getFactById(factId)
            ?: throw NullPointerException("Unable to find the fact for number $number in the local DB.")
    }

    override suspend fun getRandomNumberFact(): NumberInfo {
        val remoteData = remoteNumbersDataSource.getRandomNumberFact()
        val factId = localNumbersDataSource.putFact(remoteData)
        val number = remoteData.number
        return localNumbersDataSource.getFactById(factId)
            ?: throw NullPointerException("Unable to find the fact for previously-stored random number $number.")
    }

    override fun observeFactsHistory(): Flow<List<NumberInfo>> {
        return localNumbersDataSource.observeFactsHistory()
    }
}