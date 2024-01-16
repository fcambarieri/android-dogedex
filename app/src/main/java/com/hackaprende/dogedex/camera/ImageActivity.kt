package com.hackaprende.dogedex.camera

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toFile
import coil.load
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.databinding.ActivityImageBinding
import java.io.File

class ImageActivity : AppCompatActivity() {
    companion object {
        const val PHOTO_URI_KEY = "photo_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY) ?: ""
        if (photoUri.isNullOrEmpty()) {
            Toast.makeText(this, "Error showing image", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val uri = Uri.parse(photoUri)
        binding.wholeImage.load(uri.toFile())
    }


}