package com.github.itisme0402.numberscompose.core

import com.github.itisme0402.numberscompose.core.repo.NumbersRepository
import javax.inject.Inject

class LoadFactUseCase @Inject constructor(private val numbersRepository: NumbersRepository) {
    suspend fun getFact(number: Int): NumberInfo {
        return numbersRepository.getFact(number)
    }

    suspend fun getFactForRandomNumber(): NumberInfo {
        return numbersRepository.getRandomNumberFact()
    }
}
