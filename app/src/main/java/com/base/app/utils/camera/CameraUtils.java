package com.base.app.utils.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.base.app.utils.LogUtils;
import com.base.app.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUtils {
    //图片路径
    private String mPublicPhotoPath = "";
    public static final int REQ_GALLERY = 10010;

    public String startTake(Activity context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有相机应用
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            LogUtils.e("有相机");
            //创建临时图片文件
            File photoFile = null;
            try {
                photoFile = createPublicImageFile(context);
            } catch (IOException e) {
                LogUtils.e(e.getMessage());
                e.printStackTrace();
            }
            //设置Action为拍照
            if (photoFile != null) {
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                LogUtils.e(SystemUtils.getAppProcessName(context));
                Uri photoURI = FileProvider.getUriForFile(context, SystemUtils.getAppProcessName(context) + ".fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                context.startActivityForResult(takePictureIntent, REQ_GALLERY);
            }
        }
        //将照片添加到相册中
        galleryAddPic(mPublicPhotoPath, context);
        return mPublicPhotoPath;
    }

    /**
     * 创建临时图片文件
     *
     * @param context
     * @return
     * @throws IOException
     */
    private File createPublicImageFile(Activity context) throws IOException {
        File path = null;
        if (hasSdcard()) {
            path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM);
        }
        if (!path.exists()) {
            path.mkdir();
        }
        Date date = new Date();
        String timeStamp = getTime(date, "yyyyMMdd_HHmmss", Locale.CHINA);
        String imageFileName = "Camera/" + "IMG_" + timeStamp + ".jpg";
        File image = new File(path, imageFileName);
        mPublicPhotoPath = image.getAbsolutePath();
        LogUtils.e(mPublicPhotoPath + mPublicPhotoPath);
        return image;
    }

    /* */

    /**
     * 将照片添加到相册中
     */
    public static void galleryAddPic(String mPublicPhotoPath, Context context) {
        LogUtils.e("进来了这里");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mPublicPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 判断sdcard是否被挂载
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取时间的方法
     *
     * @param date
     * @param mode
     * @param locale
     * @return
     */
    private String getTime(Date date, String mode, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat(mode, locale);
        return format.format(date);
    }

}
