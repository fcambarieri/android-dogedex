package com.hackaprende.dogedex.doglist

import android.util.Log
import com.hackaprende.dogedex.model.Dog
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.ApiService
import com.hackaprende.dogedex.api.DogsApi
import com.hackaprende.dogedex.api.makeNetworkCall
import java.lang.Exception

class DogRepository {

    //LA API NO FUNCIONA
    private val retrofitService: ApiService by lazy { DogsApi.retrofitService }

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            //getFakeDogs()
            val response = retrofitService.getAllDogs()
            return@makeNetworkCall response.data.dogs.map {
                Dog(
                    it.id,
                    it.index,
                    it.name_es,
                    it.dog_type,
                    it.height_female,
                    it.height_male,
                    it.image_url,
                    it.life_expectancy,
                    it.temperament,
                    it.weight_female,
                    it.weight_male
                )
            }
        }
    }

    suspend fun getDogByMLId(mlDogId : String) : ApiResponseStatus<Dog> = makeNetworkCall {
        val response = retrofitService.getDogByMLId(mlDogId)
        Log.d("GetDog", response.toString())
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
        return@makeNetworkCall response.data.dog.toDog()
    }

    private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Toy", "5.4",
                "6.7", "https://cdn.pixabay.com/photo/2014/09/19/21/47/chihuahua-453063_1280.jpg",
                "12 - 15", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                2,
                2,
                "Labrador",
                "Toy",
                "5.4",
                "6.7",
                "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
                "12 - 15",
                "",
                "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                3,
                3,
                "Retriever",
                "Toy",
                "5.4",
                "6.7",
                "https://cdn.pixabay.com/photo/2016/07/29/09/30/dog-1551698_640.jpg",
                "12 - 15",
                "",
                "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                4,
                4,
                "San Bernardo",
                "Toy",
                "5.4",
                "6.7",
                "https://www.istockphoto.com/photo/saint-bernard-rescue-dog-gm1263320809-369760658?utm_source=pixabay&utm_medium=affiliate&utm_campaign=SRP_photo_sponsored&utm_content=https%3A%2F%2Fpixabay.com%2Fes%2Fphotos%2Fsearch%2Fsan%2520bernardo%2F&utm_term=san+bernardo",
                "12 - 15",
                "",
                "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                5,
                5,
                "Husky",
                "Toy",
                "5.4",
                "6.7",
                "https://cdn.pixabay.com/photo/2017/08/22/23/45/husky-2671006_640.jpg",
                "12 - 15",
                "",
                "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                6,
                6,
                "Xoloscuincle",
                "Toy",
                "5.4",
                "6.7",
                "https://media.admagazine.com/photos/64f94c88cfbb183dcb271dac/16:9/w_1600,c_limit/xoloitzcuintle.jpg",
                "12 - 15",
                "",
                "10.5",
                "12.3"
            )
        )
        return dogList
    }
}