package com.hackaprende.dogedex.api.dto

import com.squareup.moshi.Json

data class SignUpDTO (val email :String, val password :String,
@Json(name = "password_confirmation") val password_confirmation : String){

}