package com.base.app.ui.activity.list

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.base.app.base.BaseAdapter
import com.base.app.databinding.ViewBindListBindingImpl
import kotlinx.android.synthetic.main.view_bind_list.view.*

class BindListAdapter : BaseAdapter<BindListBean.DataBean, ViewBindListBindingImpl>() {
    override fun initData(
        context: Context?,
        bind: ViewBindListBindingImpl,
        viewGroup: ViewGroup?,
        mList: BindListBean.DataBean?,
    ) {
        bind.root.tv_btn2.setOnClickListener {
            Toast.makeText(context,mList?.getMoney(),Toast.LENGTH_SHORT).show()
        }
        bind.bindListBean = mList
    }

}