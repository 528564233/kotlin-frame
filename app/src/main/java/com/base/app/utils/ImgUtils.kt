package com.canbot.aileyou.utils

import android.os.Environment
import android.text.TextUtils
import android.util.Base64
import java.io.*

class ImgUtils {


    /**
     * 将Base64编码转换为图片
     * @param base64Str
     * @param path
     * @return true
     */
    companion object {
        /**
         * faces 陌生临时图片.
         */
        @JvmField
        val FACES_TEMP = Environment.getExternalStorageDirectory().toString() + "/temp/temp/"
        @JvmStatic
                /**
                 * 将Base64编码转换为图片
                 * @param base64Str
                 * @param path
                 * @return true
                 */
        fun base64ToFile(base64Str: String?, path: String): Boolean {
            if (TextUtils.isEmpty(base64Str)) {
                return false
            }
            if (!fileIsExists(path)) {
                createOrExistsFile(File(path))
            }
            val data =
                Base64.decode(base64Str, Base64.NO_WRAP)
            for (i in data.indices) {
                if (data[i] < 0) {
                    //调整异常数据
                    data[i] = (data[i].toInt() + 256).toByte()
                }
            }
            var os: OutputStream? = null
            return try {
                os = FileOutputStream(path)
                os.write(data)
                os.flush()
                os.close()
                true
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                false
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

        @JvmStatic
        fun fileIsExists(path: String): Boolean {
            try {
                val file = File(path)
                if (file.exists() && file.isFile) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }

            return false
        }

        /**
         * 判断文件是否存在，不存在则判断是否创建成功.
         *
         * @param file 文件
         * @return `true`: 存在或创建成功<br></br>
         * `false`: 不存在或创建失败
         */
        @JvmStatic
        fun createOrExistsFile(file: File?): Boolean {
            if (file == null) {
                return false
            }

            // 如果存在，是文件则返回true，是目录则返回false
            if (file.exists()) {
                return file.isFile
            }
            return if (!createOrExistsDir(file.parentFile)) {
                false
            } else try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }

        /**
         * 判断目录是否存在，不存在则判断是否创建成功.
         *
         * @param file 文件
         * @return `true`: 存在或创建成功<br></br>
         * `false`: 不存在或创建失败
         */
        @JvmStatic
        fun createOrExistsDir(file: File?): Boolean {
            // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
            return (file != null
                    && if (file.exists()) file.isDirectory else file.mkdirs())
        }

    }
}