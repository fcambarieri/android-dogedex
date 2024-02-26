package com.hackaprende.dogedex.api.responses

import com.hackaprende.dogedex.api.dto.DogDTO
import com.hackaprende.dogedex.model.Dog
import com.squareup.moshi.Json

data class DogListResponse(val dogs: List<DogDTO>)

data class DogListApiResponse(
    val message: String,
    @Json(name = "is_success") val isSuccess: Boolean,
    val data: DogListResponse
) {
}

data class DogResponse (val dog : DogDTO)
data class DogApiResponse ( val message: String,
                            @field:Json(name = "is_success") val isSuccess: Boolean,
                            val data: DogResponse)