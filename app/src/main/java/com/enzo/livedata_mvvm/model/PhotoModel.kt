package com.enzo.livedata_mvvm.model

import retrofit2.Response

interface PhotoModel {
    suspend fun getDataList(): Result<Response<List<Photo>>>
}