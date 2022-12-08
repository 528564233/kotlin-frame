package com.base.app.api


import com.base.app.base.BaseBean

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface BaseInaddress {

    //获取验证码
    @FormUrlEncoded
    @POST("/api/user/login/vfcode/get")
    fun sendCode(@Field("telNo") telNo: String?, @Field("type") type: String?): Observable<BaseBean>


    //退出登录
    @POST("/api/sso/user_logout")
    fun exitLogin(
    ): Observable<BaseBean>


    //登录
    @FormUrlEncoded
    @POST("/api/user/login")
    fun login(
    ): Observable<BaseBean>


    /**
     * 上传图片
     */
    @Multipart
    @POST("/xxxxxx")
    fun upload(
        @Part file: MultipartBody.Part,
        @PartMap map: Map<String, RequestBody>
    ): Observable<BaseBean>

/*
 images1: File,
    var map = ArrayList<MultipartBody.Part>()
    val requestBody1: RequestBody = RequestBody.create("image/png".toMediaTypeOrNull(), images1)
    val part1 = MultipartBody.Part.createFormData("images", images1.name, requestBody1)
    val map2: MutableMap<String, RequestBody> =HashMap()
    map2["countryId"] = RequestBody.create(null,countryId)
    map.add(part1)*/
    /**
     *kyc上传
     */
    @Multipart
    @POST("/api/mine/profile/kyc2")
    fun profileKyc(
        @Part images: @JvmSuppressWildcards List<MultipartBody.Part>,
        @PartMap map: @JvmSuppressWildcards Map<String, RequestBody>
    ): Observable<BaseBean>

    /*val body: RequestBody = RequestBody.create(
        "application/json; charset=utf-8".toMediaTypeOrNull(),
        JsonUtils.toJSONString(map)
    )*/
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("/swap/delivery/order")
    fun coinOrder(
        @Body info: RequestBody,
    ): Observable<BaseBean>


    /*var map = HashMap<String, Any>()
    map["orderId"] = orderId
    map["orderType"] = orderType.toInt()
    map["symbol"] = symbol.toLowerCase()
    val body: RequestBody = RequestBody.create(
        "application/json; charset=utf-8".toMediaTypeOrNull(),
        JsonUtils.toJSONString(map)
    )
    apiService()
    ?.revokeOrderU(body)
    ?.compose(RxTransformerUtils.switchSchedulers(this))
    ?.subscribe(rxObservable)*/
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @HTTP(method = "DELETE", path = "/swap/future/order", hasBody = true)
    fun revokeOrderU(
        @Body info: RequestBody,
    ): Observable<BaseBean>

}