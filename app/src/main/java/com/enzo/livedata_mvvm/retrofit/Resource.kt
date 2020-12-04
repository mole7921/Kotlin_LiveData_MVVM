package com.enzo.livedata_mvvm.retrofit

//<out T>: 將泛型作为内部方法的返回
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.Success, data,null)
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.Error,null, msg)
        }
    }
}
