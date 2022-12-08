package com.base.app.ui.fragment

import android.os.Message
import androidx.lifecycle.viewModelScope
import com.base.app.R
import com.base.app.base.BaseBean
import com.base.app.base.BaseFragment
import com.base.app.ui.activity.camera.CameraActivity
import com.base.app.ui.activity.home.BitHomeActivity
import com.base.app.ui.activity.home.IHome
import com.base.app.ui.activity.list.BindListActivity
import com.base.app.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep
import java.util.concurrent.Future

class HomeFragment : BaseFragment<HomeFragmentPresenter, BitHomeActivity>(), IHome.IVHomeFragment {
     private var job: Job?=null
     private var job1: Future<Unit>?=null
    override fun createPresenter() {
        mPresenter =
            (activity as BitHomeActivity).mHomeModel?.let { HomeFragmentPresenter(it, this) }
    }

    override fun initView() {
        mPresenter?.bindModel(this as BaseFragment<Nothing, Nothing>)
        button_camera.setOnClickListener {
            startActivity(CameraActivity::class.java)
        }

        button_list.setOnClickListener {
            startActivity(BindListActivity::class.java)
            activity?.finish()
        }

        //异步
        button_obser.setOnClickListener {
          /*  job1=  doAsync {
                //异步执行
                sleep(2000)
                uiThread {
                    //更新UI
                    button_obser.text = "更新完成"
                }
            }*/

              (activity as BitHomeActivity).mHomeModel?.viewModelScope?.launch {
                  val task1= async (Dispatchers.IO){
                       delay(300)
                       "步骤1"
                   }
                   val task2= async (Dispatchers.IO){
                       delay(3000)
                       "步骤2"
                   }
                   button_obser.text=task1.await()+task2.await()
               }

            /*  job= CoroutineScope(Dispatchers.Main).launch{
                  val task1 = withContext(Dispatchers.IO) {
                      delay(2000)
                      "步骤1"
                  }
                  val task2 =withContext(Dispatchers.IO) {
                      delay(2000)
                      "步骤2"
                  }
                  button_obser.text = task1+ task2
              }*/
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
        job1?.cancel(true)
    }

    override fun initData() {
    }

    override fun setViewRes(): Int {
        return R.layout.fragment_home
    }

    override fun showTextToast(hint: String) {
        showToast(hint);
    }

    override fun bannerSuccess(bannerEntity: BaseBean) {
    }

    override fun bannerError(error: String) {
    }

    override fun showLoading(str: String?) {
    }

    override fun hideLoading() {
    }

    override fun loginExpired() {
    }
}

