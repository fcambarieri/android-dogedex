package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.Dog

sealed class ApiResponseStatus<T>() {
    class Success<T>(val data : T) : ApiResponseStatus<T>()
    class Error<T>(val message : String) : ApiResponseStatus<T>()
    class Loading<T>() : ApiResponseStatus<T>()

}
