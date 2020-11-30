package com.enzo.livedata_mvvm.model

import com.enzo.livedata_mvvm.retrofit.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PhotoRepository:PhotoModel {

    override suspend fun getDataList(): Result<Response<List<Photo>>>
            = withContext(Dispatchers.IO) {
        //抓取例外，如果成功回傳Success，不成功回傳例外
        kotlin.runCatching {
            //使用Retrofit進行網路連線＆資料處理
            val response =
                    RetrofitManager.apiService.getDatas()

            response
        }
    }
}