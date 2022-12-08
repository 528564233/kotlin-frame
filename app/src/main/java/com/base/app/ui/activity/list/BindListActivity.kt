package com.base.app.ui.activity.list


import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.base.app.R
import com.base.app.base.BaseActivity
import com.base.app.databinding.ActivityBindListBindingImpl
import com.base.app.utils.LogUtils
import kotlinx.android.synthetic.main.activity_bind_list.*

class BindListActivity : BaseActivity<BindListPresenter>(), IBindList.IVBindList {
    var bind: ActivityBindListBindingImpl? = null

    var mBindModel: BindListModel? = null
    var mBindAdapter: BindListAdapter? = null
    override fun initView() {
        mPresenter?.bindModel(this)
        bind?.model = mBindModel
        bind?.lifecycleOwner = this
        initList()
    }

    private fun initList() {
        mBindAdapter = BindListAdapter().initView(
            this,
            R.layout.view_bind_list,
            mPresenter?.list,
            0
        ) as BindListAdapter
        lv_list.adapter = mBindAdapter
        lv_list.setOnScrollListener(mPresenter)
    }

    override fun setViewId(): Int {
        bind = DataBindingUtil.setContentView(this, R.layout.activity_bind_list)
        return 0
    }

    override fun createPresenter() {
        val instance = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application);
        mBindModel = ViewModelProvider(this, instance)
            .get(BindListModel::class.java)
        mPresenter = BindListPresenter(this, mBindModel!!)
    }

    override fun showTextToast(hint: String) {
    }

    override fun screenData(list:ArrayList<BindListBean.DataBean>) {
        mBindAdapter?.replaceList(list)
    }

    override fun showLoading(str: String?) {
        mProgressUtils?.showProgressDialog()

    }


    override fun hideLoading() {
        mProgressUtils?.hideProgressDialog()
    }

    override fun loginExpired() {
    }
}