package com.hackaprende.dogedex.auth

import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.DogsApi
import com.hackaprende.dogedex.api.dto.LoginDTO
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.model.User

class AuthRepository {

    suspend fun signUp(email: String, password: String, confirmationPassword : String)
        : ApiResponseStatus<User> = makeNetworkCall{
        val signUpDTO = SignUpDTO(email, password, confirmationPassword)
        val signUpApiResponse = DogsApi.retrofitService.signUp(signUpDTO)
        if (!signUpApiResponse.isSuccess) {
            throw Exception(signUpApiResponse.message)
        }
        signUpApiResponse.data.user.toUser()
    }

    suspend fun signIn(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall{
        val loginDTO = LoginDTO(email, password)
        val authApiResponse = DogsApi.retrofitService.signIn(loginDTO)
        if (!authApiResponse.isSuccess) {
            throw Exception(authApiResponse.message)
        }
        authApiResponse.data.user.toUser()
    }
}