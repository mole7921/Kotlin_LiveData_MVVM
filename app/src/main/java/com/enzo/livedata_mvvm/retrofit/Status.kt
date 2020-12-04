package com.enzo.livedata_mvvm.retrofit


sealed class Status {
    object Error : Status()
    object Success : Status()
}