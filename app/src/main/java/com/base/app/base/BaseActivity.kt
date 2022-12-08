package com.base.app.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.base.app.bean.EventBean
import com.base.app.utils.LanguageUtils
import com.base.app.utils.appBar.StatusBarUtil
import com.canbot.aileyou.utils.ProgressUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

public abstract class BaseActivity<T : BasePresenter<out BaseView, out BaseModel>> :
    AppCompatActivity() {
    var mContext: Context? = null
    var mPresenter: T? = null

    // private Unbinder mBind;
    var mSavedInstanceState: Bundle? = null
    var mProgressUtils: ProgressUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSavedInstanceState = savedInstanceState
        mContext = this
        if (setViewId() != 0) {
            setContentView(setViewId())
        } else {
            // throw RuntimeException("layoutRes==-1")
        }
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        //设置状态栏透明
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this)
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }

        createPresenter()
        mProgressUtils = ProgressUtils(this as BaseActivity<*>)
        EventBus.getDefault().register(this)
        initView()
    }

    /**
     * 初始化方法
     */
    abstract fun initView()

    /**
     * 获取contentView 资源id
     */
    abstract fun setViewId(): Int

    /**
     * 创建presenter实例
     */
    abstract fun createPresenter()

    override fun onResume() {
        super.onResume()
    }

    fun startDelayed() {
        val handler = Looper.myLooper()?.let { Handler(it) }
        handler?.postDelayed({
            delayedInit()
        }, 50)
    }

    open fun delayedInit() {

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

    /**
     *
     *
     * @param text
     */
    open fun showToast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    /**
     *
     *
     * @param className
     */
    open fun startActivity(className: Class<*>?, bundle: Bundle?) {
        val intent = Intent(mContext, className)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }


    /**
     *
     *
     * @param className
     */
    open fun startActivity(className: Class<*>?, type: String, bundle: Int?) {
        val intent = Intent(mContext, className)
        intent.putExtra(type, bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    /*
     *通知
     */
    protected open fun acceptMessage(code: Int, job: Any?) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun simpleEventBus(userEvent: EventBean) {
        acceptMessage(userEvent.code, userEvent.job)
    }

    var eventBean: EventBean? = null

    //发送消息
    open fun sendMessage(code: Int, o: Any?) {
        if (eventBean == null) {
            eventBean = EventBean()
        }
        eventBean!!.code = code
        eventBean!!.job = o
        EventBus.getDefault().postSticky(eventBean)
    }


    override fun onDestroy() {
        super.onDestroy()
        /*if (mBind != null) {
            mBind.unbind();
        }*/
        mPresenter?.onDestroy() //页面销毁 网络请求同销毁
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)

    }


    /*多语言的设置*/
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtils.updateLanguage(newBase))
    }

    /* *//*皮肤初始化*//*
    override fun getDelegate(): AppCompatDelegate {
        return SkinAppCompatDelegateImpl.get(this, this)
    }*/

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private fun isLightColor(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) >= 0.5
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    @ColorInt
    protected open fun getStatusBarColor(): Int {
        return Color.WHITE
    }


}