package com.hackaprende.dogedex.api.dto

import com.squareup.moshi.Json

class SignUpDTO (email :String, password :String,
@Json(name = "password_confirmation") val passwordConfigrmation : String){

}