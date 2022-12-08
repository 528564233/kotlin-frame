package com.base.app.ui.activity.home

import RxTransformerUtils
import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.base.app.base.BaseBean
import com.base.app.base.BaseModel
import com.base.app.rehelper.RxObservable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class HomeModel : BaseModel() {

    var user: MutableLiveData<Any>? = MutableLiveData()
    var banner: MutableLiveData<Any>? = MutableLiveData()

    fun getLogin(
        rxObservable: RxObservable<BaseBean>
    ) {
        apiService()
            ?.login(

            )
            ?.compose(RxTransformerUtils.switchSchedulers(this))
            ?.subscribe(rxObservable)
    }

}