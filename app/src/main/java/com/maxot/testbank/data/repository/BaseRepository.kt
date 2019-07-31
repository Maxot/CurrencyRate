package com.maxot.testbank.data.repository

import android.util.Log
import com.maxot.testbank.data.network.Result
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        var data: T? = null

        try {
            val result: Result<T> = safeApiResult(call, errorMessage)
            data = null

            when (result) {
                is Result.Success -> {
                    data = result.data
                    Log.d("Registration", "Success")
                }
                is Result.Error -> {
                    Log.d("Repository", "$errorMessage & Exception - ${result.exception}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>, errorMessage: String): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}