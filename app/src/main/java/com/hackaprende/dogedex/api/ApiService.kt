package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.DOGS_BASE_URL
import com.hackaprende.dogedex.GET_ALL_DOGS
import com.hackaprende.dogedex.api.responses.DogListApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.time.Duration
import java.util.concurrent.TimeUnit


private val retrofit = Retrofit.Builder()
    .baseUrl(DOGS_BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .client(OkHttpClient.Builder()
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .callTimeout(5000, TimeUnit.MILLISECONDS)
        .build())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS)
    suspend fun getAllDogs() : DogListApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}