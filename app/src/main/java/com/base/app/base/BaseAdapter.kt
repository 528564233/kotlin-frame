package com.base.app.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseAdapter<T, M : ViewDataBinding> : BaseAdapter() {
    private var mContext: Context? = null
    private var mList: List<T>? = null
    private var mIndexPositive = 0
    private val viewHolder: ViewHolder? = null

    @LayoutRes
    private var mLayout = 0

    override fun getCount(): Int {
        return if (mList == null) {
            0
        } else {
            mList!!.size + mIndexPositive
        }
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    fun replaceList(List: List<T>?) {
        mList = List
        notifyDataSetChanged()
    }

    open fun initView(
        context: Context?,
        @LayoutRes layout: Int,
        List: List<T>?,
        indexPositive: Int
    ): com.base.app.base.BaseAdapter<*, *>? {
        mContext = context
        mList = List
        mIndexPositive = indexPositive
        mLayout = layout
        notifyDataSetChanged()
        return this
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var binding: M? = null
        var fView = view
        if (mContext == null) {
            return fView
        }
        if (fView == null) {
            binding = initDataView(mContext, mLayout, viewGroup.parent as ViewGroup)
        } else {
            binding = setDataView(fView)
        }
        if (binding != null) {
            initData(mContext, binding, viewGroup, mList?.get(i))
        }
        return binding?.root
    }

    abstract fun initData(
        context: Context?,
        bind: M,
        viewGroup: ViewGroup?,
        mList: T?,
    )

    private fun initDataView(
        context: Context?,
        i: Int,
        viewGroup: ViewGroup?,
    ): M {
        return DataBindingUtil.inflate(LayoutInflater.from(context), i, viewGroup, false)
    }

    private fun setDataView(view: View?): M? {
        return view?.let {
            DataBindingUtil.getBinding(it)
        }
    }

}