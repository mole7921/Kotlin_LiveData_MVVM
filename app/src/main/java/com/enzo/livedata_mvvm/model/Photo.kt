package com.enzo.livedata_mvvm.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Photo(
    @SerializedName("albumId")
    var albumId: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
):Serializable{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass){
            return false
        }

        other as Photo
        if(albumId != other.albumId){
            return false
        }
        if(id != other.id){
            return false
        }
        if(title != other.title){
            return false
        }
        if(url != other.url){
            return false
        }
        if(thumbnailUrl != other.thumbnailUrl){
            return false
        }
        return true
    }
}





