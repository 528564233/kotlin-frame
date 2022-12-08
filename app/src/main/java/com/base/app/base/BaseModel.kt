package com.base.app.base

import androidx.lifecycle.ViewModel
import com.base.app.api.ApiRetofit
import com.base.app.api.BaseInaddress

import io.reactivex.disposables.CompositeDisposable

open  class BaseModel : ViewModel(){

   var mDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * 初始化调用网络请求
     * @return
     */
    fun apiService(): BaseInaddress? {
        return ApiRetofit.createApi()?.create(BaseInaddress::class.java)
    }

    /**
     * 取消网络请求
     */
   open fun onDestroy() {
        mDisposable.dispose()
        mDisposable.clear()
    }
}