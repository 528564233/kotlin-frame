package com.base.app.utils;

import com.tencent.mmkv.MMKV;

import java.util.List;

/**
 * SharedPreferences统一管理工具类
 */
public class SPUtils {
    private static final String LIST_TAG = ".LIST";


    /**
     * 保存对象数据至SharedPreferences, key默认为类名, 如
     * <pre>
     * PreferencesHelper.putData(saveUser);
     * </pre>
     *
     * @param data 不带泛型的任意数据类型实例
     */
    public static <T> void putData(T data) {
        putData(data.getClass().getName(), data);
    }

    /**
     * 根据key保存对象数据至SharedPreferences, 如
     * <pre>
     * PreferencesHelper.putData(key, saveUser);
     * </pre>
     *
     * @param data 不带泛型的任意数据类型实例
     */
    public static <T> void putData(String key, T data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MMKV mmkv = MMKV.defaultMMKV();
                mmkv.encode(key, JsonUtils.toJSONString(data));
            }
        }).start();

    }

    /**
     * 根据key将数据从SharedPreferences中取出, 如
     * <pre>
     * User user = PreferencesHelper.getData(User.class)
     * </pre>
     */
    public static <T> T getData(String key, Class<T> clz) {
        MMKV mmkv = MMKV.defaultMMKV();
        return JsonUtils.parseObject(mmkv.decodeString(key, ""), clz);
    }

    public static <T> List<T> getDataList(String key, Class<T> clz) {
        MMKV mmkv = MMKV.defaultMMKV();
        String s = mmkv.decodeString(key, "");
        if (s.equals("")) {
            return null;
        }
        return JsonUtils.parseArray(s, clz);
    }

    /**
     * 简易字符串保存, 仅支持字符串
     */
    public static void putString(String key, String data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    /**
     * 简易字符串获取, 仅支持字符串
     */
    public static String getString(String key, String defValue) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeString(key, defValue);
    }

    public static void putInt(String key, int data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    public static int getInt(String key, int defValue) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeInt(key, defValue);
    }

    public static void putBoolean(String key, boolean data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    public static boolean getBoolean(String key, boolean defaultData) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeBool(key, defaultData);
    }

    public static void putFloat(String key, float data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    public static void putDouble(String key, Double data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    public static double getDouble(String key, Double data) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeDouble(key, data);
    }

    public static float getFloat(String key, float defaultData) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeFloat(key, defaultData);
    }

    public static void putLong(String key, long data) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(key, data);
    }

    public static float getLong(String key, long defaultData) {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeLong(key, defaultData);
    }

    public static void clear() {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.clearAll();
    }

    /**
     * 删除保存的对象
     */
    public static void remove(String key) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.remove(key);

    }

    /**
     * 删除保存的对象
     */
    public static void remove(Class clz) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.remove(clz.getName());
    }

    /**
     * 删除保存的数组
     */
    public static void removeList(Class clz) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.remove(clz.getName() + LIST_TAG);
    }


}
