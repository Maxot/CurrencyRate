package com.maxot.testbank.data.repository

import com.maxot.testbank.data.model.NBUCurrency
import com.maxot.testbank.data.model.PrivatCurrency
import com.maxot.testbank.data.network.ApiHelper

class CurrencyRepository(private var api: ApiHelper) : BaseRepository(){

    suspend fun getPrivatCurrency(date: String) : PrivatCurrency?{
        val data = safeApiCall(
            call = {api.getPrivatCurrency(date).await()},
            errorMessage = "Error"
        )
        return data
    }

    suspend fun getNBUCurrency(date: String) : List<NBUCurrency>?{
        val data = safeApiCall(
                call = {api.getNBUCurrency(date).await()},
                errorMessage = "Error"
        )
        return data
    }


}