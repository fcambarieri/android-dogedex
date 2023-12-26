package com.hackaprende.dogedex

data class Dog(
    val id: Long, val index: Int, val name: String, val type: String,
    val heigthFemale: Double, val heigthMale: Double, val imageUrl: String,
    val lifeExpectancy: String, val temperament: String, val weighFamele: Double,
    val weighMale: Double
) {
}