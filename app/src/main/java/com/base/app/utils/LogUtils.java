package com.base.app.utils;

import android.util.Log;



/**
 * Log日志打印操作
 *
 * @author Weiss
 */
public class LogUtils {

    public static final boolean DEBUG =true; //BuildConfig.LOG_DEBUG;

    /**
     * 获取当前类名
     *
     * @return
     */
    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    /**
     * System.out.print打印
     *
     * @param logString
     */
    public static void print(String logString) {
        if (DEBUG) {
            System.out.println(getClassName() + "  :  " + logString);
        }
    }


    /**
     * System.out.print打印
     *
     * @param logString
     * @param tag
     */
    public static void print(String tag, String logString) {
        if (DEBUG) {
            System.out.println(tag + " : " + logString);
        }
    }


    public static void w(String logString) {
        if (DEBUG) {
            Log.w(getClassName(), logString);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void error(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(getClassName(), msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
        //if (DEBUG) {
        if (DEBUG) {
            //Log.e(getClassName(), msg);
            if (msg.length() > 4000&&msg.length()<10000) {
                for (int i = 0; i < msg.length(); i += 4000) {
                    if (i + 4000 < msg.length())
                        Log.e("bityard" + i, msg.substring(i, i + 4000));
                    else
                        Log.e("bityard" + i, msg.substring(i, msg.length()));
                }
            } else
                Log.e("bityard", msg);
        }
    }

    public static void i(String tag, String logString) {
        if (DEBUG) {
            Log.i(tag, logString);
        }
    }


    public static void w(String tag, String logString) {
        if (DEBUG) {
            Log.w(tag, logString);
        }
    }

}