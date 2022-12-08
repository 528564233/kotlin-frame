package com.base.app.base

import com.base.app.ui.fragment.HomeFragment

open class BasePresenter<V : BaseView, M : BaseModel>(mView: V, mModel: M) {
    var mView: V? = null
    var mModel: M? = null

    init {
        this.mView = mView
        this.mModel = mModel
    }

    open fun onDestroy() {
        if (mModel != null) {
            mModel?.onDestroy()
        }
    }
}