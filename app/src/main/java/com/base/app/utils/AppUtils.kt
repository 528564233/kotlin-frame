package com.base.app.utils

import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView
import com.base.app.bean.EventBean
import org.greenrobot.eventbus.EventBus


class AppUtils {
    companion object {
        @JvmStatic
        fun sendMessage(code: Int, o: Any) {
            var eventBean = EventBean()
            eventBean.setCode(code)
            eventBean.setJob(o)
            EventBus.getDefault().post(eventBean)
        }

        @JvmStatic
        fun isServiceRunning(context: Context, serviceName: String): Boolean {
            if (("") == serviceName || serviceName == null)
                return false
            var isWork = false
            val myAM =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val myList =
                myAM.getRunningServices(100)
            if (myList.size <= 0) {
                return false
            }
            for (i in myList.indices) {
                val mName = myList[i].service.className
                if (mName == serviceName) {
                    isWork = true
                    break
                }
            }
            return isWork
        }

        @JvmStatic
        fun isApkDebug(context: Context?):Boolean{
            try {
                if(context==null)return false;
                val info = context.applicationInfo
                return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            } catch (e: Exception) {
            }
            return false
        }

        //复制
        @JvmStatic
        fun copy(context: Context, data: String?) {
            // 获取系统剪贴板
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
            // newHtmlText、
            // newIntent、
            // newUri、
            // newRawUri
            val clipData = ClipData.newPlainText(null, data)

            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData)
        }

        //粘贴
        @JvmStatic
        fun paste(context: Context, textView: TextView) {
            // 获取系统剪贴板
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // 获取剪贴板的剪贴数据集
            val clipData = clipboard.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                // 从数据集中获取（粘贴）第一条文本数据
                val text = clipData.getItemAt(0).text
                textView.text = text
            }
        }



    }
}