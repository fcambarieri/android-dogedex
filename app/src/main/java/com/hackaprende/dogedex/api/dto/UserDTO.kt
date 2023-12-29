package com.hackaprende.dogedex.api.dto

import com.hackaprende.dogedex.model.User
import com.squareup.moshi.Json

class UserDTO(
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token") val authenticationToken: String
) {

    fun toUser() : User {
      return User(id, email, authenticationToken)
    }
}