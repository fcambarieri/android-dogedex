package com.hackaprende.dogedex.di

import com.hackaprende.dogedex.DOGS_BASE_URL
import com.hackaprende.dogedex.api.ApiService
import com.hackaprende.dogedex.api.ApiServiceInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun createApiService (retrofit: Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun createRetrofilt (okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(DOGS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .callTimeout(5000, TimeUnit.MILLISECONDS)
                .build())
            .build()
    }
    @Provides
    fun provideHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor(ApiServiceInterceptor)
        .build()

}