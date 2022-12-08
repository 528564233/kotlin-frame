package com.base.app.utils.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.base.app.R
import com.base.app.ui.dialog.DoubleSelectDialog
import com.base.app.ui.dialog.IDialogInterface
import com.base.app.utils.LogUtils
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

class CompressUtils(context: Activity, compressSuccess: IDialogInterface.CompressSuccess) :
    IDialogInterface.NoticeSuccess {
    var context: Activity? = null
    var compressSuccess: IDialogInterface.CompressSuccess? = null

    init {
        this.context = context
        this.compressSuccess = compressSuccess
    }

    val WRITE_EXTERNAL_STORAGE = 6001
    val CAMERA = 6002
    val READ_EXTERNAL_STORAGE = 6003
    var mPublicPath = ""
    private var mPath: String? = null

    fun compressImg(path: String) {
        if (context == null) return
        Luban.with(context).load(File(path)).ignoreBy(100)
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    compressSuccess?.showLoading()
                }

                override fun onSuccess(file: File?) {
                    compressSuccess?.hideLoading()
                    if (file != null) {
                        compressSuccess?.dialogSuccess(file)
                    }

                }

                override fun onError(e: Throwable?) {
                    compressSuccess?.hideLoading()
                    mPath?.let { compressSuccess?.dialogSuccess(it) }

                }
            }).launch()
    }

    override fun dialogSuccess(string: String, type: Int) {
        when (type) {
            IDialogInterface.CAMERA -> {
                mPublicPath = cameraUtils.startTake(context)
            }
            IDialogInterface.ALBUM -> {
                chooseAlbumPic()
            }
        }
    }

    override fun dialogCancel() {

    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CameraUtils.REQ_GALLERY) {
            //if (resultCode != Activity.RESULT_OK) return
            try {
                val uri = Uri.parse(mPublicPath)
                mPath = uri?.path
                mPath?.let { compressImg(it) }
            } catch (e: NullPointerException) {
                LogUtils.e(e.message);
            }
        } else if (requestCode == IDialogInterface.ALBUM) {
            if (data != null) {
                val imageUri = data.data
                mPath = BitmapUtils.getRealPathFromUri(context, imageUri)
                mPath?.let { compressImg(it) }
            }
        }
    }


    fun openPermission() {
        if (context == null) return
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context!!,
                    Manifest.permission.CAMERA
                )
            ) {
                showPermission()
            }
            // 申请权限
            ActivityCompat.requestPermissions(
                context!!,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA
            )
        } else {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 申请权限
                ActivityCompat.requestPermissions(
                    context!!,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE
                )
                return
            }
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 申请权限
                ActivityCompat.requestPermissions(
                    context!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE
                )
                return
            }
            permissionSuccess()
        }
    }

    private fun showPermission() {
        Toast.makeText(
            context,
            context?.getString(R.string.text_p_open_permission),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun permissionSuccess() {
        if (context == null) return
        doubleSelectDialog?.showDialog(
            context!!.getString(R.string.text_upload_idcode),
            context!!.getString(R.string.text_camera_),
            context!!.getString(R.string.text_album),
            IDialogInterface.CAMERA,
            IDialogInterface.ALBUM,
            this
        )
    }

    private val doubleSelectDialog: DoubleSelectDialog?
        get() {
            return context?.let { DoubleSelectDialog(it) }
        }

    private val cameraUtils: CameraUtils
        get() {
            return CameraUtils()
        }


    /**
     * 选择相册照片
     */
    fun chooseAlbumPic() {
        val i = Intent()
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        if (Build.VERSION.SDK_INT < 19) {
            i.action = Intent.ACTION_GET_CONTENT
        } else {
            i.action = Intent.ACTION_OPEN_DOCUMENT
        }
        context?.startActivityForResult(
            Intent.createChooser(i, "Chooser"),
            IDialogInterface.ALBUM
        )
    }

    fun handlePermission(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (context == null) return;
        when (requestCode) {
            CAMERA ->                 // 摄像头权限申请
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // 申请权限
                        ActivityCompat.requestPermissions(
                            context!!,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            WRITE_EXTERNAL_STORAGE
                        )
                        return
                    }
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // 申请权限
                        ActivityCompat.requestPermissions(
                            context!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_EXTERNAL_STORAGE
                        )
                        return
                    }
                    permissionSuccess()
                } else {
                    // 被禁止授权
                    showPermission()
                }
            WRITE_EXTERNAL_STORAGE -> // 摄像头权限申请
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // 申请权限
                        ActivityCompat.requestPermissions(
                            context!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_EXTERNAL_STORAGE
                        )
                        return
                    }
                    // 获得授权
                    permissionSuccess()
                } else {
                    // 被禁止授权
                    showPermission()
                }
            READ_EXTERNAL_STORAGE ->                 // 摄像头权限申请
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    permissionSuccess()
                } else {
                    // 被禁止授权
                    showPermission()
                }
        }

    }

}