package com.base.app.ui.activity.list

import RxTransformerUtils
import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.app.base.BaseBean
import com.base.app.base.BaseModel
import com.base.app.rehelper.RxObservable
import com.base.app.utils.LogUtils
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class BindListModel : BaseModel() {
    var listData: MutableLiveData<Any>? = MutableLiveData()
    var edName: MutableLiveData<String> = MutableLiveData("")

    fun getData(
        page:Int
    ){
        viewModelScope.launch {
            listData?.value= "1001"
           var bean= withContext(Dispatchers.IO){
                var bean=BindListBean()
                var beanList=ArrayList<BindListBean.DataBean>()
                for (index in 0 until 10) {
                    delay(200)
                    var dataList =BindListBean.DataBean()
                    dataList.setMoney("第几个$index")
                    beanList.add(dataList)
                }
                bean.setList(beanList)
                bean.setPage(page)
                bean
            }
            listData?.value= bean
        }
    }

}