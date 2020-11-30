package com.enzo.livedata_mvvm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Photo(
    @Expose
    @SerializedName("albumId")
    var albumId: String? = null,
    @Expose
    @SerializedName("id")
    var id: String? = null,
    @Expose
    @SerializedName("title")
    var title: String? = null,
    @Expose
    @SerializedName("url")
    var url: String? = null,
    @Expose
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
):Serializable





