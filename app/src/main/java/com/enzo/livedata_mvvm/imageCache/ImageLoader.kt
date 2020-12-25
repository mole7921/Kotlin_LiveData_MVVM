package com.enzo.livedata_mvvm.imageCache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference


object ImageLoader {
     private var imageViewWeakReference: WeakReference<ImageView>? = null
     private var mImageCache: ImageCache? = null
     private val okHttpClient = OkHttpClient()




     suspend fun displayImage(url: String, imageView: ImageView, imageCache: ImageCache?) {
         imageViewWeakReference = WeakReference(imageView)

         mImageCache = imageCache
        var md5 = ""
        try {
            md5 = MD5Encoder.encode(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val bitmap = mImageCache?.get(md5)
        if (bitmap != null) {
            if (md5 == imageView.tag) {
                imageViewWeakReference!!.get()?.setImageBitmap(bitmap)
            }
            return
        }
         imageViewWeakReference!!.get()?.let { submitLoadRequest(url, it) }
    }

    private suspend fun submitLoadRequest(url: String, imageView: ImageView) {
        withContext(Dispatchers.IO) {
            var bitmap: Bitmap? = null
            try {
                bitmap = downloadImage(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (bitmap == null) {
                return@withContext
            }
            try {
                val md5 = MD5Encoder.encode(url)
                if (md5 == imageView.tag) {
                    withContext(Dispatchers.Main){
                        imageView.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                mImageCache?.put(url, bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
   private fun  downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap?
        val inStream: InputStream?
        val bis: BufferedInputStream?
        val req = Request.Builder().url(url).header("user-agent", "Chrome 74 on Windows 10").build()
        val response = okHttpClient.newCall(req).execute()
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        bitmap = if (response.body!!.contentType().toString().toLowerCase()
                .contains("application/json") || response.body!!
                .contentType().toString().toLowerCase().contains("text/plain")
        ) {
            throw IOException("下載資源失敗,下載地址為=$url")
            null
        } else {
            inStream = response.body!!.byteStream()
            bis = BufferedInputStream(inStream)
            BitmapFactory.decodeStream(bis)
        }

        bis?.close()
        inStream?.close()
        response.body!!.close()

        return bitmap
    }


}