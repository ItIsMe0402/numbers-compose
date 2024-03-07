package com.github.itisme0402.numberscompose.core

import com.github.itisme0402.numberscompose.core.repo.NumbersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    private val numbersRepository: NumbersRepository
) {
    fun observeFacts(): Flow<List<NumberInfo>> {
        return numbersRepository.observeFactsHistory()
    }
}
