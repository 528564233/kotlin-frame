package com.base.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class TextViewUtils {

    //隐藏软键盘并让editText失去焦点
    public static void hideKeyboard(Activity activity, IBinder token, EditText editText) {
        editText.clearFocus();
        if (token != null) {
            //这里先获取InputMethodManager再调用他的方法来关闭软键盘
            //InputMethodManager就是一个管理窗口输入的manager
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 禁止EditText输入空格
     * 今天遇到个情况这个InputFilter不好使了，找了半天原因是冲突在android:inputType="textPassword"这个属性上了
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText) {
        //过滤空格
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source.equals(" ")) return "";
            else return source;
        },});

    }


}
