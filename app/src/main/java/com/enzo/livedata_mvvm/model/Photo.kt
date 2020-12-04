package com.enzo.livedata_mvvm.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Photo(
    @SerializedName("albumId")
    var albumId: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
):Serializable





