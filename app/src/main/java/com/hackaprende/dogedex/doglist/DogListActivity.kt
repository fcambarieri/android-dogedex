package com.hackaprende.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.databinding.ActivityDogListBinding
import com.hackaprende.dogedex.dogdetail.DogDetailActivity
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity
import dagger.hilt.android.AndroidEntryPoint

private val GRID_SPAN_COUNT = 3

@AndroidEntryPoint
class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWeel = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        val adapter = DogAdapter()
        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailComposeActivity::class.java)
            intent.putExtra(DogDetailComposeActivity.DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) {
            adapter.submitList(it)
        }

        dogListViewModel.status.observe(this) { status ->
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
    }
}