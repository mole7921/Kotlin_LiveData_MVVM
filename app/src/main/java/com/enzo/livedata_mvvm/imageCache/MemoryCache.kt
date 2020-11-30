package com.enzo.livedata_mvvm.imageCache

import android.graphics.Bitmap
import android.util.LruCache


class MemoryCache: ImageCache {


    private var mMemoryCache: LruCache<String, Bitmap>? = null

    constructor() {
        //初始化Lru快取
        initImageCache()
    }

    private fun initImageCache() {
        //計算可使用的最大記憶體
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        //取四分之一可使用的記憶體作為快取
        val cacheSize = maxMemory / 4
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap): Int {
                return bitmap.rowBytes * bitmap.height / 1024
            }
        }
    }


    override fun get(id: String): Bitmap? {
      return mMemoryCache?.get(id)
    }

    override fun put(id: String, bitmap: Bitmap) {
        mMemoryCache?.put(id, bitmap)
    }

}