package com.enzo.livedata_mvvm.retrofit

import com.enzo.livedata_mvvm.model.Photo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // 測試網站      https://jsonplaceholder.typicode.com/
    // GET網址      https://jsonplaceholder.typicode.com/photos
    // POST網址     https://jsonplaceholder.typicode.com/photos
    // ...typicode.com/[這裡就是API的路徑]

    // 設置一個GET連線，路徑為photos
    @GET("photos")
   suspend fun getDatas(): List<Photo>


    // 用{}表示路徑參數，@Path會將參數帶入至該位置
    @GET("photos/{id}")
    fun getAlbumsById(@Path("id") id: Int): Call<Photo>

    // 用@Body表示要傳送Body資料
    @POST("photos")
    fun postAlbums(@Body xxxxx: Photo?): Call<Photo>

//    測試網址的POST是用Body方式收參數所以我們用@Body，若是要用問號帶在網址後的參數如?id=1則應改用@Query
}