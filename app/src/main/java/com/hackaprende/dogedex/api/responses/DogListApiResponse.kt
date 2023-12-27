package com.hackaprende.dogedex.api.responses

import com.hackaprende.dogedex.Dog
import com.squareup.moshi.Json

data class DogListResponse (val dogs : List<Dog>)

data class DogListApiResponse (val message : String,
                               @Json(name ="is_success") val isSuccess : Boolean ,
                               val data : DogListResponse) {
}