package com.base.app.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.base.app.utils.LanguageUtils
import com.tencent.mmkv.MMKV

class MyApplication: Application() {
    companion object{

        @SuppressLint("StaticFieldLeak")
        @JvmField
        var mContext: Context? = null

    }



    override fun onCreate() {
        super.onCreate()
        mContext = this
    }


    override fun attachBaseContext(base: Context?) {
        MMKV.initialize(base)
        super.attachBaseContext(LanguageUtils.updateLanguage(base))
        // MultiDex.install(base);
    }
}