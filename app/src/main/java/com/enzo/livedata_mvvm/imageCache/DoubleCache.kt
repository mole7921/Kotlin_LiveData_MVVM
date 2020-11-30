package com.enzo.livedata_mvvm.imageCache

import android.graphics.Bitmap

class DoubleCache: ImageCache {
    var mMemoryCache: ImageCache = MemoryCache()
    var mDiskCache: ImageCache = DiskCache()



    override fun get(id: String): Bitmap? {
        var bitmap = mMemoryCache.get(id)
        if (bitmap == null) {
            bitmap = mDiskCache.get(id)
        }
        return bitmap
    }

    override fun put(id: String, bitmap: Bitmap) {
        mMemoryCache.put(id, bitmap)
        mDiskCache.put(id, bitmap)
    }
}