package com.enzo.livedata_mvvm.model


import com.enzo.livedata_mvvm.retrofit.ApiService
import com.enzo.livedata_mvvm.retrofit.Resource
import com.enzo.livedata_mvvm.retrofit.ResponseHandler


class PhotoRepository( private val apiService: ApiService,
                       private val responseHandler: ResponseHandler):PhotoModel {


    override suspend fun getDataList(): Resource<List<Photo>> {
        return try {
            val response = apiService.getDatas()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}