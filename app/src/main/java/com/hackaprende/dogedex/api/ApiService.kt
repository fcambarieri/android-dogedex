package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.GET_ALL_DOGS
import com.hackaprende.dogedex.GET_DOG_BY_ML_ID
import com.hackaprende.dogedex.SIGN_IN_URL
import com.hackaprende.dogedex.SIGN_UP_URL
import com.hackaprende.dogedex.api.dto.LoginDTO
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.responses.AuthApiResponse
import com.hackaprende.dogedex.api.responses.DogApiResponse
import com.hackaprende.dogedex.api.responses.DogListApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs() : DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO) : AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun signIn(@Body loginDTO: LoginDTO) : AuthApiResponse
    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMLId(@Query("ml_id") mlDogId: String): DogApiResponse
}
