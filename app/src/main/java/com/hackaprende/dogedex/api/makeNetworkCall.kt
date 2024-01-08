package com.hackaprende.dogedex.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.hackaprende.dogedex.R

suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (e: HttpException) {
            val errorMessage = if (e.code() == 401) {
                R.string.wrong_user_or_password
            } else {
                R.string.unknow_error
            }
            ApiResponseStatus.Error(errorMessage.toString())
        } catch (e: Throwable) {
            ApiResponseStatus.Error("Message error: ${e.message}")
        }

    }
}