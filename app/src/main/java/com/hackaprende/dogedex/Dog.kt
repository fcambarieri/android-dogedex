package com.hackaprende.dogedex

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Long, val index: Int, val name: String,
    @Json(name = "dog_type") val type: String,
    @Json(name = "height_female") val heightFemale: String,
    @Json(name = "height_male") val heightMale: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "life_expectancy") val lifeExpectancy: String,
    val temperament: String,
    @Json(name = "weight_female") val weightFemale: String,
    @Json(name = "weigh_male") val weightMale: String
) : Parcelable