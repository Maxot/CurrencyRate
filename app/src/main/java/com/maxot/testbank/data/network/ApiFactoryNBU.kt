package com.maxot.testbank.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object  ApiFactoryNBU {
    private const val BASE_URL = "https://bank.gov.ua/"

        //gson
        val gson = GsonBuilder()
                .disableHtmlEscaping()
                .setLenient()
                .create()

        //OkhttpClient for building http request url
        val webClient = OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .build()


        fun retrofit(): Retrofit = Retrofit.Builder()
                .client(webClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()



    val apiHelper: ApiHelper = retrofit()
            .create(ApiHelper::class.java)
}