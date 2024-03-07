package com.github.itisme0402.numberscompose.db

import com.github.itisme0402.numberscompose.core.repo.LocalNumbersDataSource
import com.github.itisme0402.numberscompose.core.NumberInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomNumbersDataSource @Inject constructor(
    private val numbersDao: NumbersDao,
) : LocalNumbersDataSource {
    override suspend fun getFactById(id: Long): NumberInfo? {
        return numbersDao.getFact(id)?.let { (number, fact) -> NumberInfo(number, fact) }
    }

    override suspend fun putFact(numberInfo: NumberInfo): Long {
        return numbersDao.putFact(LocalNumberInfo(numberInfo.number, numberInfo.fact))
    }

    override fun observeFactsHistory(): Flow<List<NumberInfo>> {
        return numbersDao.observeFactsHistory().map {
            it.map { (number, fact) ->
                NumberInfo(number, fact)
            }
        }
    }
}