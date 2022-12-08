package com.base.app.ui.fragment

import androidx.lifecycle.Observer
import com.base.app.base.BaseBean
import com.base.app.base.BaseFragment
import com.base.app.base.BasePresenter
import com.base.app.ui.activity.home.HomeModel
import com.base.app.ui.activity.home.IHome


class HomeFragmentPresenter(model: HomeModel, view: IHome.IVHomeFragment) :
    BasePresenter<IHome.IVHomeFragment, HomeModel>(view, model), IHome.IPHomeFragment {
    fun bindModel(context: BaseFragment<Nothing, Nothing>) {
        mModel?.banner?.observe(context, Observer {
            //mView?.hideLoading()
            if (it is BaseBean) {
                mView?.bannerSuccess(it)
            } else if (it is String) {
                mView?.showTextToast(it)
            }
        })
    }


    override fun getBanner() {
        //网络请求前加载loading
        // mView?.showLoading("")
        mModel?.banner?.value="哈哈";
     /*   mModel?.getBanner(
            object : RxObservable<BannerBean>() {
                override fun onSuccess(t: BannerBean?) {
                    mModel?.banner?.value = t
                }

                override fun onFail(reason: String?) {
                    // mModel?.banner?.value = reason
                }

                override fun onExpired() {
                    mModel?.banner?.value = ""
                }
            })*/
    }


    override fun onDestroy() {
        super.onDestroy()

    }
}