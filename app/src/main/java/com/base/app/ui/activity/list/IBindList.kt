package com.base.app.ui.activity.list

import com.base.app.base.BaseBean
import com.base.app.base.BaseView



public interface IBindList {

    interface IPBindList {
    }

    interface IVBindList : BaseView {
        fun showTextToast(hint: String)
        fun screenData(list: ArrayList<BindListBean.DataBean>)
    }


}