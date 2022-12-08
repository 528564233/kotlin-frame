package com.base.app.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.base.app.bean.EventBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment<T : BasePresenter<out BaseView, out BaseModel>, A : BaseActivity<out BasePresenter<out BaseView, out BaseModel>>> :

    Fragment() {

    private var mContext: Context? = null
    private var mRootView: View? = null
    var mPresenter: T? = null
    var mActivity: A? = null

    /**
     * 获得全局的，防止使用getActivity()为空
     *
     * @param context
     */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            if (setViewRes() != 0) {
                mRootView = LayoutInflater.from(mContext)
                    .inflate(setViewRes(), container, false)
                mActivity = (activity as A)
                createPresenter()
                EventBus.getDefault().register(this)

            } else {
                throw RuntimeException("layoutResID==-1 have u create your layout?")
            }
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     * 创建presenter实例
     */
    abstract fun createPresenter()


    /**
     * 初始化
     */
    protected abstract fun initView()

    /**
     * 加载数据
     */
    abstract fun initData()

    open fun setData(str: String?) {}

    /**
     * 吐司
     *
     * @param text
     */
    open fun showToast(text: String?) {
        if (isAdded)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * activity跳转（无参数）
     *
     * @param className
     */
    open fun startActivity(className: Class<*>?) {
        val intent = Intent(mContext, className)
        startActivity(intent)
    }

    /*
     *通知
     */
    protected open fun acceptMessage(code: Int, `object`: Any?) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun simpleEventBus(userEvent: EventBean) {
        acceptMessage(userEvent.getCode(), userEvent.getJob())
    }
    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    open fun startActivity(className: Class<*>?, bundle: Bundle?) {
        val intent = Intent(mContext, className)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }
    /**
     *  如果你用了support 23的库，上面的方法会提示过时，有强迫症的小伙伴，可以用下面的方法代替
     */

    /**
     * 如果你用了support 23的库，上面的方法会提示过时，有强迫症的小伙伴，可以用下面的方法代替
     */
    /**
     * 传入布局文件
     *
     * @return
     */
    abstract fun setViewRes(): Int


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.onDestroy() //页面销毁网络请求也取消
        }
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()

    }

    fun startDelayed(time:Long) {
        val handler = Looper.myLooper()?.let { Handler(it) }
        handler?.postDelayed({
            if (isAdded) {
                delayedInit()
            }
        }, time)
    }

    open fun delayedInit() {
    }


}