package com.hackaprende.dogedex.auth

import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.ApiService
import com.hackaprende.dogedex.api.dto.LoginDTO
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.model.User
import javax.inject.Inject

interface AuthTasks {
    suspend fun signUp(email: String, password: String, confirmationPassword : String)
            : ApiResponseStatus<User>
    suspend fun signIn(email: String, password: String): ApiResponseStatus<User>
}
class AuthRepository @Inject constructor(private val apiService: ApiService)

    : AuthTasks {

    override suspend fun signUp(email: String, password: String, confirmationPassword : String)
        : ApiResponseStatus<User> = makeNetworkCall{
        val signUpDTO = SignUpDTO(email, password, confirmationPassword)
        val signUpApiResponse = apiService.signUp(signUpDTO)
        if (!signUpApiResponse.isSuccess) {
            throw Exception(signUpApiResponse.message)
        }
        signUpApiResponse.data.user.toUser()
    }

    override suspend fun signIn(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall{
        val loginDTO = LoginDTO(email, password)
        val authApiResponse = apiService.signIn(loginDTO)
        if (!authApiResponse.isSuccess) {
            throw Exception(authApiResponse.message)
        }
        authApiResponse.data.user.toUser()
    }
}