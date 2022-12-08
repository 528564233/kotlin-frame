package com.canbot.aileyou.utils

import android.app.Activity
import com.base.app.base.ProgressDialog
import com.base.app.utils.LogUtils


class ProgressUtils(activity: Activity) {
    /**
     * 显示普通进度条对话框
     *
     * @param msg
     */
    var mProgressDialog: ProgressDialog = ProgressDialog(activity)
    var mBaseActivity: Activity = activity

    fun showProgressDialog() {
        try {
            if (!mBaseActivity.isFinishing) {
                mProgressDialog.show()
            }
        } catch (e: Exception) {
            LogUtils.e(e.message)
        }
    }


    /**
     * 隐藏对话框
     */
    fun hideProgressDialog() {
        if (mBaseActivity.isFinishing()) {
            return
        }
        mProgressDialog.dismiss()
    }
}