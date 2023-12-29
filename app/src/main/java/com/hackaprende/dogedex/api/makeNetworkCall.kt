package com.hackaprende.dogedex.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeNetworkCall(call: suspend () -> T): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {

            ApiResponseStatus.Success(call())

        } catch (e: Throwable) {

            ApiResponseStatus.Error("Error Getting Dogs. Error ${e.message}")
        }

    }
}