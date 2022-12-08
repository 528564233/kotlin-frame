package com.base.app.ui.activity.home


import androidx.lifecycle.Observer
import com.base.app.base.BaseActivity
import com.base.app.base.BaseBean
import com.base.app.base.BasePresenter
import com.base.app.rehelper.RxObservable



class HomePresenter(model: HomeModel, view: IHome.IVHome) :
    BasePresenter<IHome.IVHome, HomeModel>(view, model), IHome.IPHome {
    fun bindModel(context: BaseActivity<Nothing>) {
        mModel?.user?.observe(context, Observer {
           if(it is String){
               mView?.showTextToast(it)
           }else if(it is BaseBean){
               mView?.loginSuccess(it);
           }
        })
    }

    override fun getLogin() {
         mView?.showLoading("")
        mModel?.getLogin(
            object : RxObservable<BaseBean>() {
                override fun onSuccess(t: BaseBean?) {
                    mModel?.user?.value = t
                }

                override fun onFail(reason: String?) {
                    mModel?.user?.value = reason
                }

                override fun onExpired() {
                    mModel?.user?.value = ""
                }
            })
    }
}