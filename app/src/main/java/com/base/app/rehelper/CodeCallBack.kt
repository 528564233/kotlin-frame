package com.base.app.rehelper

interface CodeCallBack<T> {
    fun onSuccess(t: T?)

    fun onFail(reason: String?)

    fun onCode(code: Int?)

    fun onExpired()
}