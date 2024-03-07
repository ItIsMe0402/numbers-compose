package com.github.itisme0402.numberscompose.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {
    @GET("{number}")
    suspend fun getFact(@Path("number") number: Int): String

    @GET("random/math")
    suspend fun getRandomNumberFact(): Response<String>
}
