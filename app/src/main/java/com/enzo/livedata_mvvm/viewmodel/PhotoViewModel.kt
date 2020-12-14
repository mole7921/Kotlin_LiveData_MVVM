package com.enzo.livedata_mvvm.viewmodel




import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.model.PhotoRepository
import com.enzo.livedata_mvvm.retrofit.Resource
import kotlinx.coroutines.*



class PhotoViewModel @ViewModelInject constructor(private val model: PhotoRepository):ViewModel() {

    private val _dataList = MutableLiveData<Resource<List<Photo>>>()
    val dataList: LiveData<Resource<List<Photo>>>
        get() = _dataList


    init {
        //只在初始化取得資料
        fetchData()
    }


    private fun fetchData(){
        viewModelScope.launch {
            _dataList.value = model.getDataList()
        }
    }



}