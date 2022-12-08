package com.base.app.rehelper

interface CallBack<T> {
    fun onSuccess(t: T?)

    fun onFail(reason: String?)

    fun onExpired()
}