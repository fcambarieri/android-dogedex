package com.hackaprende.dogedex.dogdetail

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.model.Dog
import dagger.hilt.android.AndroidEntryPoint

class DogDetailComposeActivity : ComponentActivity() {
    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent?.extras?.getParcelable(DOG_KEY, Dog::class.java)

        if (dog == null) {
            Toast.makeText(this, "Dog not found", Toast.LENGTH_LONG)
            finish()
            return
        }

        setContent {
            DogedexTheme {
              DogDetailScreen(dog = dog, onClick = { finish()})
            }
        }
    }
}
