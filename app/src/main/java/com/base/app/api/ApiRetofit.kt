package com.base.app.api


import com.base.app.application.MyApplication
import com.base.app.utils.AppUtils
import com.base.app.utils.LogUtils
import com.base.app.api.BaseUrl
import com.lzy.okgo.cookie.CookieJarImpl
import com.lzy.okgo.cookie.store.SPCookieStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiRetofit {
    companion object {
        private var mRetrofit: Retrofit? = null
        private var sOkHttpClient: OkHttpClient? = null
        //超时时间
        private val CONN_TIMEOUT = 60
        private val READ_TIMEOUT = 60
        private val WRITE_TIMEOUT = 60

        /**
         * Retrofit初始化
         *
         * @return
         */
        @JvmStatic
        fun createApi(): Retrofit? {
            if (mRetrofit == null) {
                synchronized(ApiRetofit::class.java) {
                    if (mRetrofit == null) {
                        mRetrofit = Retrofit.Builder()
                            .client(getOkHttpClient()!!)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BaseUrl.BASE_URL)
                            .build()
                    }
                }
            }
            return mRetrofit
        }

        private fun getOkHttpClient(): OkHttpClient? {
            if (sOkHttpClient == null) {
                if (!LogUtils.DEBUG) {
                    sOkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain): Response {
                                // 以拦截到的请求为基础建立一个新的请求对象，而后插入Header
                                val request = chain.request().newBuilder()
                                    .addHeader("Accept-Language", "zh-CN")
                                    .build()
                                return chain.proceed(request)
                                // 开始请求
                            }
                        }) // =                   .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getContext())))
                        .connectTimeout(CONN_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
                        .cookieJar(CookieJarImpl(SPCookieStore(MyApplication.mContext)))
                        .build()
                }else{
                    val logInterceptor = okhttp3.logging.HttpLoggingInterceptor(HttpLogger())
                    sOkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(logInterceptor)
                        .addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain): Response {
                                // 以拦截到的请求为基础建立一个新的请求对象，而后插入Header
                                val request = chain.request().newBuilder()
                                    .addHeader("Accept-Language", "zh-CN")
                                    .build()
                                return chain.proceed(request)
                                // 开始请求
                                // 开始请求
                            }
                        }) // =                   .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getContext())))
                        .connectTimeout(CONN_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
                        .cookieJar(CookieJarImpl(SPCookieStore(MyApplication.mContext)))
                        .build()
                    if (AppUtils.isApkDebug(MyApplication.mContext)) {
                        logInterceptor.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
                    }
                }

            } else {

            }
            return sOkHttpClient
        }

        @JvmStatic
        fun clearOkHttpClient() {
            sOkHttpClient = null
            mRetrofit = null
        }

        class HttpLogger : okhttp3.logging.HttpLoggingInterceptor.Logger {
            private val mMessage = StringBuilder()
            override fun log(message: String) {
                /// 请求或者响应开始
                if (!LogUtils.DEBUG) {
                    return
                }
                if (message.startsWith("--> POST")) {
                    mMessage.delete(0, mMessage.length)
                }
                mMessage.append(
                    """
                    $message
                    
                    """.trimIndent()
                )
                // 请求或者响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    try {
                        LogUtils.e(String.format("mMessage=%s", mMessage))
                    } catch (e: Exception) {
                        LogUtils.e("erro---" + e.message)
                    }
                    mMessage.delete(0, mMessage.length)
                }
            }
        }
    }


}