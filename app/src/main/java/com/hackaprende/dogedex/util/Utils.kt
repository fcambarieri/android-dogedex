package com.hackaprende.dogedex.util

import android.util.Patterns


fun isValidEmail(email: String): Boolean {
    return !email.isEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.isEmpty(): Boolean {
    return this.length == 0
}