package com.hackaprende.dogedex.main

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hackaprende.dogedex.LABELS_PATH
import com.hackaprende.dogedex.MODEL_PATH
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.auth.LoginActivity
import com.hackaprende.dogedex.camera.ImageActivity
import com.hackaprende.dogedex.databinding.ActivityMainBinding
import com.hackaprende.dogedex.dogdetail.DogDetailActivity
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity
import com.hackaprende.dogedex.doglist.DogListActivity
import com.hackaprende.dogedex.machinelearning.Classifier
import com.hackaprende.dogedex.model.Dog
import com.hackaprende.dogedex.model.User
import com.hackaprende.dogedex.setttings.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.common.FileUtil
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor : ExecutorService
    private var isCameraReady : Boolean = false

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                setupCamera()
            } else {
                Toast.makeText(this, "You need to accept camara permission", Toast.LENGTH_LONG).show()
            }
        }
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.docListFab.setOnClickListener {
            openDogListActivity()
        }

        binding.takePhotoFab.setOnClickListener {
            if (isCameraReady) {
                takePhoto()
            }
        }

        val loadingWeel = binding.loadingWheel

        viewModel.status.observe(this) { status ->
            when (status) {

                is ApiResponseStatus.Loading -> {
                    loadingWeel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Error -> {
                    loadingWeel.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error al descargar datos: " + status.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ApiResponseStatus.Success -> {
                    loadingWeel.visibility = View.GONE
                }
            }
        }

        viewModel.dog.observe(this) { dog ->
            openDetailDogActivity(dog)
        }

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        }
        requestCamaraPermission()
    }

    private fun openDetailDogActivity(dog: Dog) {
        if (dog != null) {
            val intent = Intent(this, DogDetailComposeActivity::class.java)
            intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
    }

    override fun onStart() {
        super.onStart()
         classifier = Classifier(
            FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(this@MainActivity, LABELS_PATH)
        )
    }


    private fun requestCamaraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    setupCamera()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.CAMERA) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected, and what
                    // features are disabled if it's declined. In this UI, include a
                    // "cancel" or "no thanks" button that lets the user continue
                    // using your app without granting the permission.
                    AlertDialog.Builder(this)
                        .setTitle("Accept me, please")
                        .setMessage("Aceptar")
                        .setPositiveButton(android.R.string.ok) { _ , _ ->
                            launchRequest()
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->

                        }.show()

                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    launchRequest()
                }
            }
        } else {
            //open camera
            setupCamera()

        }
    }

    private fun launchRequest() {
        requestPermissionLauncher.launch(
            android.Manifest.permission.CAMERA)
    }


    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun startCamera() {
        val camaraProviderFuture = ProcessCameraProvider.getInstance(this)
        camaraProviderFuture.addListener({
            val camaraProvider = camaraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            val camaraSelector = CameraSelector.DEFAULT_BACK_CAMERA


            val imageAnalysis = ImageAnalysis.Builder()
                // enable the following line if RGBA output is needed.
                // .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                //.setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor, ImageAnalysis.Analyzer { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                imageProxy.close()
            })

            camaraProvider.bindToLifecycle(this, camaraSelector, preview, imageCapture, imageAnalysis)


        }, ContextCompat.getMainExecutor(this))
    }

    private fun setupCamera() {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }

    }

    private fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException)
                {
                    Toast.makeText(this@MainActivity, "Fucking Error ${error.message}",Toast.LENGTH_SHORT).show()
                }
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    //Toast.makeText(this@MainActivity, "Save it!", Toast.LENGTH_SHORT).show()
                    val photoUri = outputFileResults.savedUri

                    val bitmap = BitmapFactory.decodeFile(photoUri?.path)
                    val dogRecognition = classifier.recognizeImage(bitmap).first()

                    viewModel.getDogByMLId(dogRecognition.id)

                    //openImageActivity(photoUri.toString())
                }
            })
    }

    private fun openImageActivity(photo: String) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra(ImageActivity.PHOTO_URI_KEY, photo)
        startActivity(intent)
    }

    private fun getOutputPhotoFile() : File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs()}
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
}