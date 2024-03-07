package com.github.itisme0402.numberscompose.network

import com.github.itisme0402.numberscompose.core.NumberInfo
import com.github.itisme0402.numberscompose.core.repo.RemoteNumbersDataSource
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class NumbersApiNumbersDataSource @Inject constructor() : RemoteNumbersDataSource {
    private val api: NumbersApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(NumbersApi::class.java)

    override suspend fun getFact(number: Int): NumberInfo {
        val fact = api.getFact(number)
        return NumberInfo(number, fact)
    }

    override suspend fun getRandomNumberFact(): NumberInfo {
        val response = api.getRandomNumberFact()
        val number = response.headers()[HEADER_NUMBER]!!.toInt()
        val fact = response.body()!!
        return NumberInfo(number, fact)
    }

    private companion object {
        const val BASE_URL = "http://numbersapi.com"
        const val HEADER_NUMBER = "X-Numbers-Api-Number"
    }
}