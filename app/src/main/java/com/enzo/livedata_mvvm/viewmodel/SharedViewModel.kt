package com.enzo.livedata_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enzo.livedata_mvvm.model.Photo

class SharedViewModel:ViewModel() {

    private val _data = MutableLiveData<Photo>()
    val data: LiveData<Photo>
        get() = _data

    fun select(photo: Photo) {
        _data.value = photo
    }

}