package com.base.app.ui.activity.home

import android.os.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.base.app.R
import com.base.app.base.BaseActivity
import com.base.app.base.BaseBean
import com.base.app.ui.fragment.HomeFragment
import com.base.app.utils.LogUtils
import com.base.app.utils.appBar.StatusBarUtil
import kotlin.collections.ArrayList

class BitHomeActivity : BaseActivity<HomePresenter>(), IHome.IVHome {
    var mHomeModel: HomeModel? = null

    private var fragmentManager: FragmentManager? = null
    private var currentFragment = Fragment()

    //当前显示的fragment
    private val CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW"
    var currentIndex = 0
    private val fragments: MutableList<Fragment> by lazy {
        ArrayList<Fragment>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            fragments.clear()
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT)
        }
        addFragment()
        showFragment(false)
    }

    override fun initView() {
        mPresenter?.bindModel(this as BaseActivity<Nothing>)
        //打开沉浸式状态栏
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)

    }


    override fun setViewId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter() {
        val instance = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application);
        mHomeModel = ViewModelProvider(this, instance)
            .get(HomeModel::class.java)
        mPresenter = HomePresenter(mHomeModel!!, this)
    }

    override fun showTextToast(hint: String) {
    }

    override fun loginSuccess(BasBean: BaseBean) {

    }


    override fun showLoading(str: String?) {
        mProgressUtils?.showProgressDialog()
    }

    override fun hideLoading() {
        mProgressUtils?.hideProgressDialog()
    }

    override fun loginExpired() {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex)
        super.onSaveInstanceState(outState, outPersistentState)
    }


    private fun addFragment() {
        fragments.add(HomeFragment())
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private fun showFragment(b: Boolean) {
        // vp_view.currentItem = currentIndex
        //showTab()
        //如果之前没有添加过
        try {
            hideFragment(b)
        } catch (e: java.lang.Exception) {
            LogUtils.e("崩溃了" + e.message)
            fragmentManager = supportFragmentManager
            addFragment()
            showFragment(true)
        }
    }

    private fun hideFragment(b: Boolean) {
        if (fragmentManager == null) fragmentManager = supportFragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        if (fragments.size > 4) {
            if (!fragments[currentIndex].isAdded) {
                transaction
                    .hide(currentFragment)
                    .add(R.id.fl, fragments[currentIndex], "" + currentIndex)
                //第三个参数为添加当前的fragment时绑定一个tag
            } else {
                transaction
                    .hide(currentFragment)
                    .show(fragments[currentIndex])
            }
        } else {
            addFragment()
            if (!fragments[currentIndex].isAdded) {
                transaction
                    .hide(currentFragment)
                    .add(R.id.fl, fragments[currentIndex], "" + currentIndex)
                //第三个参数为添加当前的fragment时绑定一个tag
            } else {
                transaction
                    .hide(currentFragment)
                    .show(fragments[currentIndex])
            }
        }
        currentFragment = fragments[currentIndex]
        if (b) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}