package com.base.app.rehelper

import com.base.app.api.ApiRetofit.Companion.clearOkHttpClient
import com.base.app.api.MessageCode
import com.base.app.base.BaseBean
import com.base.app.utils.AppUtils.Companion.sendMessage
import com.base.app.utils.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException

abstract class RxObservable<T> : Observer<T>, CallBack<T> {

    override fun onSubscribe(s: Disposable) {
        run { LogUtils.e(s.toString() + "") }
    }

    override fun onError(t1: Throwable) {
        var t = t1
        if (t is CompositeException) {
            t = t.exceptions[0]
        }
        var error = RxException.handleException(t).message
        if ("Program error, please restart" == error||"ArrayIndexOutOfBoundsException"==error) {
            clearOkHttpClient()
            onFail("")
            return
        }
        onFail(error)
    }

    override fun onComplete() {}
    override fun onNext(t: T) {
        //可以根据需求对code统一处理
        if (t != null) {
            val responseCode = t as BaseBean
            if (responseCode.code == 200) {
                onSuccess(t)
            } else {
                if (responseCode.code == 401) {
                    sendMessage(MessageCode.LOGIN_EXPIRED, "")
                    return
                }
                onFail(responseCode.message)
            }
        }
    }

}