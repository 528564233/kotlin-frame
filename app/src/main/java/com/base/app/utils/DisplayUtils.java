package com.base.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DisplayUtils {

    public static int getSystemWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
            //int heightPixels = outMetrics.heightPixels;
            //int densityDpi = outMetrics.densityDpi;
            //float density = outMetrics.density;
            //float scaledDensity = outMetrics.scaledDensity;
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。
        }
        return 0;
    }

    public static int getSystemHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            // int widthPixels = outMetrics.widthPixels;
            return outMetrics.heightPixels;
            //int densityDpi = outMetrics.densityDpi;
            //float density = outMetrics.density;
            //float scaledDensity = outMetrics.scaledDensity;
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。

        }
        return 0;
    }


}