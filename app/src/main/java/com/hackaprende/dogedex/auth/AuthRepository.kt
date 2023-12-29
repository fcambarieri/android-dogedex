package com.hackaprende.dogedex.auth

import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.DogsApi
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.api.responses.SignUpApiResponse
import com.hackaprende.dogedex.model.User

class AuthRepository {

    suspend fun signUp(email: String, password: String, confirmationPassword : String)
        : ApiResponseStatus<User> = makeNetworkCall{
        val signUpDTO = SignUpDTO(email, password, confirmationPassword)
        val signUpApiResponse = DogsApi.retrofitService.signUp(signUpDTO)
        signUpApiResponse.data.user.toUser()
    }
}