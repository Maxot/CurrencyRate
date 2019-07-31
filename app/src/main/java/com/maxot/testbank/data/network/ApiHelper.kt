package com.maxot.testbank.data.network

import com.maxot.testbank.data.model.NBUCurrency
import com.maxot.testbank.data.model.PrivatCurrency
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiHelper {

    @GET("p24api/exchange_rates?json")
    fun getPrivatCurrency(
        @Query("date") date: String
    ): Deferred<Response<PrivatCurrency>>


    @GET("NBUStatService/v1/statdirectory/exchange?json")
    fun getNBUCurrency(
            @Query("date") date: String
    ): Deferred<Response<List<NBUCurrency>>>

}