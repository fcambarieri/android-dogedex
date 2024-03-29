package com.hackaprende.dogedex.api.dto

import android.os.Parcelable
import com.hackaprende.dogedex.model.Dog
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class DogDTO(
    val id: Long, val index: Int, val name_es: String,
    val dog_type: String,
    val height_female: String,
    val height_male: String,
    val image_url: String,
    val life_expectancy: String,
    val temperament: String,
    val weight_female: String,
    val weight_male: String,
    val ml_id: String
) : Parcelable {
    fun toDog(): Dog {
        return Dog(
            id,
            index,
            name_es,
            dog_type,
            height_female,
            height_male,
            image_url,
            life_expectancy,
            temperament,
            weight_female,
            weight_male
        )
    }
}