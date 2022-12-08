package com.base.app.ui.activity.list

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.base.app.base.BaseBean

class BindListBean : BaseBean() {
    private var page = 1
    private var list: List<DataBean>? = null

    fun getList(): List<DataBean>? {
        return list
    }

    fun setList(name: List<DataBean>) {
        this.list = name
    }

    fun getPage(): Int {
        return page
    }

    fun setPage(page: Int) {
        this.page = page
    }

    class DataBean : BaseBean() {
        private var money: String? = null

        @Bindable
        fun getMoney(): String? {
            return money
        }

        fun setMoney(name: String?) {
            this.money = name
            notifyPropertyChanged(BR.money)
        }

        fun onClick() {
            setMoney("成功")
        }

    }

}