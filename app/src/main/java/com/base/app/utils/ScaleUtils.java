package com.base.app.utils;

import android.content.res.Resources;

/**
 * dp px sp 转化工具
 *
 */
public class ScaleUtils {
 
    private ScaleUtils() {
    }
 
    public static int dip2px(float f) {
        return Math.round((Resources.getSystem().getDisplayMetrics().density * f) + 0.5f);
    }
 
    public static int px2dip(float f) {
        return Math.round((f / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
 
    public static int px2sp(float f) {
        return Math.round((f / Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }
 
    public static int sp2px(float f) {
        return Math.round((Resources.getSystem().getDisplayMetrics().scaledDensity * f) + 0.5f);
    }
 
 
}