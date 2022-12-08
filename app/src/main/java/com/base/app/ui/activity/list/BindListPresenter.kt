package com.base.app.ui.activity.list

import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.base.app.base.BasePresenter
import com.base.app.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BindListPresenter(view:IBindList.IVBindList,model: BindListModel):BasePresenter<IBindList.IVBindList,BindListModel>(view,model),IBindList.IPBindList ,
    AbsListView.OnScrollListener{
    var page=1
    var list = ArrayList<BindListBean.DataBean>()
    fun bindModel(context: BindListActivity) {
        mModel?.listData?.observe(context, Observer {
            if(it is String){
                if(it=="1001"){
                    mView?.showLoading("加载中...")
                    return@Observer
                }
                mView?.hideLoading()
                mView?.showTextToast(it)
            }else if(it is BindListBean){
                mView?.hideLoading()
                handlerBindList(it)
            }
        })
        mModel?.edName?.observe(context, Observer {
            if(it is String){
               screenData(it)
            }
        })

    }

    //处理接口来的list数据--分页all 兼容筛选
    private fun handlerBindList(it: BindListBean) {
        page = it.getPage()
        if (page == 1) {
            list.clear()
        }
        it.getList()?.let { it1 -> list.addAll(it1) }
        //走筛选刷新
        mModel?.edName?.value?.let { it1 -> screenData(it1) }
    }

    //筛选
    private fun screenData(name: String){
        if(name.isEmpty()){
            mView?.screenData(list)
        }
        mModel?.viewModelScope?.launch{
            val screenList = ArrayList<BindListBean.DataBean>()
            withContext(Dispatchers.IO){
                for(index in list.iterator()){
                    if(index.getMoney()?.contains(name) == true){
                        screenList.add(index)
                    }
                }
            }
            mView?.screenData(screenList)
        }

    }


    //刷新
    override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
        when (p1) {
            AbsListView.OnScrollListener.SCROLL_STATE_IDLE -> {
                if(p0==null)return
                if (p0.lastVisiblePosition == (p0.count - 1)) {
                    mModel?.getData(page+1)
                }

            }
        }
    }

    override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {

    }

}