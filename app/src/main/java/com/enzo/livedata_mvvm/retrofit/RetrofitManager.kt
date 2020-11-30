package com.enzo.livedata_mvvm.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitManager{
    private const val ApiUrl = "https://jsonplaceholder.typicode.com/"
    private const val DEFAULT_CONNECT_TIME = 10L
    private const val DEFAULT_WRITE_TIME = 30L
    private const val DEFAULT_READ_TIME = 30L

    private val okHttpClient: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//連接超時
            .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//寫入操作超時
            .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//讀取操作超時
    }


   private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(ApiUrl)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiService: ApiService by lazy{
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }

    /* lazy 應用於單例模式(if-null-then-init-else-return)，
     僅當變量被第一次調用的時候，委託方法才會執行。 */
}