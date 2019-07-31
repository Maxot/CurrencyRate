package com.maxot.testbank.ui.window

import androidx.lifecycle.MutableLiveData
import com.maxot.testbank.data.model.NBUCurrency
import com.maxot.testbank.data.model.PrivatCurrency
import com.maxot.testbank.data.network.ApiFactoryPrivat
import com.maxot.testbank.data.network.ApiFactoryNBU
import com.maxot.testbank.data.repository.CurrencyRepository
import com.maxot.testbank.ui.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CurrencyViewModel : BaseViewModel(){
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repositoryPrivat: CurrencyRepository =
            CurrencyRepository(ApiFactoryPrivat.apiHelper)

    private val repositoryNBU: CurrencyRepository =
            CurrencyRepository(ApiFactoryNBU.apiHelper)

    val currencyPrivatData = MutableLiveData<PrivatCurrency>()
    val currencyNBUData = MutableLiveData<List<NBUCurrency>>()

    fun getPrivatCurrency(date: String) {
        scope.launch {
            val response = repositoryPrivat.getPrivatCurrency(date)
            currencyPrivatData.postValue(response)
        }
    }

    fun getNBUCurrency(date: String) {
        scope.launch {
            val response = repositoryNBU.getNBUCurrency(date)
            currencyNBUData.postValue(response)
        }
    }

}