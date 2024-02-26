package com.hackaprende.dogedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.responses.DogApiResponse
import com.hackaprende.dogedex.doglist.DogRepository
import com.hackaprende.dogedex.model.Dog
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val dogRepository = DogRepository()

    private val _dog = MutableLiveData<Dog>()
    val dog : LiveData<Dog> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status : LiveData<ApiResponseStatus<Dog>> get() = _status

    fun getDogByMLId(mlDogID : String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogByMLId(mlDogID))
        }
    }
    private fun handleResponseStatus(dogApiResponse : ApiResponseStatus<Dog>) {
        if (dogApiResponse is ApiResponseStatus.Success) {
            _dog.value = dogApiResponse.data
        }
        _status.value = dogApiResponse
    }
}