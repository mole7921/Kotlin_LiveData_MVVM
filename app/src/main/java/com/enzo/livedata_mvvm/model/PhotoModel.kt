package com.enzo.livedata_mvvm.model

import com.enzo.livedata_mvvm.retrofit.Resource


interface PhotoModel {
    suspend fun getDataList(): Resource<List<Photo>>
}