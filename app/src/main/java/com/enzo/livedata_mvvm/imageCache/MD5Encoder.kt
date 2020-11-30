package com.enzo.livedata_mvvm.imageCache

import java.security.MessageDigest
import kotlin.experimental.and

object MD5Encoder {

    @Throws(Exception::class)
    fun encode(string: String): String {
        val hash = MessageDigest.getInstance("MD5").digest(string.toByteArray(charset("UTF-8")))
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            if (b and 0xFF.toByte() < 0x10) {
                hex.append("0")
            }
            hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
        }
        return hex.toString()
    }
}