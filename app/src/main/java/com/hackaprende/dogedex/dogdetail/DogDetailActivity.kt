package com.hackaprende.dogedex.dogdetail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable(DOG_KEY, Dog::class.java)

        if (dog == null) {
            Toast.makeText(this, "Dog not found", Toast.LENGTH_LONG)
            finish()
            return
        }

        binding.dog = dog
    }
}