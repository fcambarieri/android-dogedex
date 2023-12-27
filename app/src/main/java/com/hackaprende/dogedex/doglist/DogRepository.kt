package com.hackaprende.dogedex.doglist

import android.util.Log
import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.ApiService
import com.hackaprende.dogedex.api.DogsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    //LA API NO FUNCIONA
    private val retrofitService : ApiService by lazy {  DogsApi.retrofitService }

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            try {
                //val dogListApiResponse = retrofitService.getAllDogs()

                //throw java.lang.RuntimeException("WTF...")
                //getFakeDogs()
                //ApiResponseStatus.Success(dogListApiResponse.data.dogs)
                ApiResponseStatus.Success(getFakeDogs())

            } catch (e: Throwable) {
             //   Log.e("wtf", "Getting Dogs API", e)
             //   getFakeDogs()
                ApiResponseStatus.Error("Error Getting Dogs. Error ${e.message}")
            }

        }
    }

    private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "10.5",
                "12.3"
            )
        )
        return dogList
    }
}