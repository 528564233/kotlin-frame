package com.base.app.ui.activity.camera

import android.content.Intent
import com.base.app.R
import com.base.app.base.BaseActivity
import com.base.app.base.BasePresenter
import com.base.app.ui.dialog.IDialogInterface
import com.base.app.utils.camera.CompressUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity:BaseActivity<BasePresenter<Nothing,Nothing>>(),IDialogInterface.CompressSuccess{
    var compressUtils: CompressUtils?=null
    override fun initView() {
        compressUtils= CompressUtils(this,this);
        button_first.setOnClickListener {
            compressUtils?.openPermission()
        }
    }

    override fun setViewId(): Int {
        return R.layout.activity_camera
    }

    override fun createPresenter() {

    }

    override fun dialogSuccess(data: Any) {
        //获取成功
        Glide.with(this).load(data).apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(textview_first)
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        compressUtils?.onActivityResult(requestCode,resultCode,data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        compressUtils?.handlePermission(requestCode,permissions,grantResults)
    }
}