package com.enzo.livedata_mvvm.imageCache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class DiskCache: ImageCache {


    private val CACHE_PATH =
        Environment.getExternalStorageDirectory().absolutePath + "/PhotoTempCache"

    /**
     * 從本地讀取圖片
     * @param id
     */
    fun getBitmapFromLocal(id: String?): Bitmap? {
        try {
            val file = File(CACHE_PATH, id)
            return BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }




    override fun get(id: String): Bitmap? {
        return getBitmapFromLocal(id)
    }

    override fun put(id: String, bitmap: Bitmap) {
        val md5: String? = MD5Encoder.encode(id)

        val baseFile: File = File(CACHE_PATH)

        if (!baseFile.exists() || !baseFile.isDirectory) {
            baseFile.mkdirs()
        }

        val bitemapFile = File(baseFile, md5)

        try {
            val fos = FileOutputStream(bitemapFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}