package com.base.app.ui.activity.home

import com.base.app.base.BaseBean
import com.base.app.base.BaseView



public interface IHome {

    interface IPHome {
        fun getLogin()
    }

    interface IVHome : BaseView {
        fun showTextToast(hint: String)
        fun loginSuccess(BasBean: BaseBean)
    }

    interface IPHomeFragment {
        fun getBanner()
    }

    interface IVHomeFragment : BaseView {
        fun showTextToast(hint: String)
        fun bannerSuccess(bannerEntity: BaseBean)
        fun bannerError(error: String)
    }

}