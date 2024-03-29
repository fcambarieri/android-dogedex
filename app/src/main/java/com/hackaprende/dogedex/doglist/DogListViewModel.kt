package com.hackaprende.dogedex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.model.Dog
import com.hackaprende.dogedex.api.ApiResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogListViewModel @Inject constructor(private val dogRepository: DogTasks) : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList : LiveData<List<Dog>> get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status : LiveData<ApiResponseStatus<List<Dog>>> get() = _status


    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.downloadDogs())
        }
    }

    private fun handleResponseStatus(downloadDogs: ApiResponseStatus<List<Dog>>) {
        if (downloadDogs is ApiResponseStatus.Success) {
            _dogList.value = downloadDogs.data
        }
        _status.value = downloadDogs
    }
}