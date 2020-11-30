package com.enzo.livedata_mvvm.viewmodel




import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enzo.livedata_mvvm.model.Photo
import com.enzo.livedata_mvvm.model.PhotoModel
import com.enzo.livedata_mvvm.model.PhotoRepository
import com.enzo.livedata_mvvm.retrofit.Resource
import com.enzo.livedata_mvvm.retrofit.ResponseHandler
import com.enzo.livedata_mvvm.retrofit.RetrofitManager.apiService
import kotlinx.coroutines.*



class PhotoViewModel:ViewModel() {

    private val model: PhotoModel = PhotoRepository(apiService, ResponseHandler())
    private val _dataList = MutableLiveData<Resource<List<Photo>>>()
    val dataList: LiveData<Resource<List<Photo>>>
        get() = _dataList

    //一次性的工作例如提示訊息和畫面跳轉
    private val _clickItem = SingleLiveEvent<Photo>()
    val clickItem: LiveData<Photo>
        get() = _clickItem




    init {
        //只在初始化取得資料
        fetchData()
    }


    private fun fetchData(){
        viewModelScope.launch {
            _dataList.value = model.getDataList()
        }
    }


     fun isClick(photo:Photo){
         _clickItem.value = photo
    }


}