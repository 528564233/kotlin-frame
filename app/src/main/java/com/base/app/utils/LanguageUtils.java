


package com.base.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LanguageUtils {
    public static final String ZH_SIMPLE = "zh_CN";
    public static final String VI_VN = "vi_VN";
    public static final String IN_ID = "in_ID";
    public static final String ZH_TRADITIONAL = "zh_traditional";
    public static final String JA_JP = "ja_JP";
    public static final String EN_US = "en_US";
    public static final String KO_KR = "ko_KR";
    public static final String RU_RU = "ru_RU";
    public static final String PT_PT = "pt_PT";
    public static final String KEY_LANGUAGE = "KEY_LANGUAGE";

    public static Context selectLanguage(Context context, String language) {
        Context updateContext;
        //设置语言类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateContext = createConfigurationResources(context, language);
        } else {
            applyLanguage(context, language);
            updateContext = context;
        }
        //保存设置语言的类型
        SPUtils.putString(KEY_LANGUAGE, language);
        return updateContext;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, String language) {
        //设置语言类型
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = null;
        switch (language) {
            case EN_US:
                locale = Locale.ENGLISH;
                break;
            case ZH_SIMPLE:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case ZH_TRADITIONAL:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            case VI_VN:
                locale = new Locale("vi", "VN");
                break;
            case RU_RU:
                locale = new Locale("ru", "RU");
                break;
            case IN_ID:
                locale = new Locale("in", "ID");
                break;
            case JA_JP:
                locale = Locale.JAPAN;
                break;
            case KO_KR:
                locale = Locale.KOREA;
                break;
            case PT_PT:
                locale = new Locale("pt", "PT");
                break;
        }
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private static void applyLanguage(Context context, String language) {
        //设置语言类型
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = null;
        switch (language) {
            case EN_US:
                locale = Locale.ENGLISH;
                break;
            case ZH_SIMPLE:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case ZH_TRADITIONAL:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            case VI_VN:
                locale = new Locale("vi", "VN");
                break;
            case RU_RU:
                locale = new Locale("ru", "RU");
                break;
            case IN_ID:
                locale = new Locale("in", "ID");
                break;
            case JA_JP:
                locale = Locale.JAPAN;
                break;
            case KO_KR:
                locale = Locale.KOREA;
                break;
            case PT_PT:
                locale = new Locale("pt", "PT");
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // apply locale
            configuration.setLocale(locale);
        } else {
            // updateConfiguration
            configuration.locale = locale;
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);
        }
    }

    public static Context updateLanguage(Context context) {
        String curLanguage =SPUtils.getString(KEY_LANGUAGE,EN_US);
        Locale locale = null;
        switch (curLanguage) {
            case EN_US:
                locale = Locale.ENGLISH;
                break;
            case ZH_SIMPLE:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case ZH_TRADITIONAL:
                locale = Locale.TRADITIONAL_CHINESE;
                break;
            case VI_VN:
                locale = new Locale("vi", "VN");
                break;
            case RU_RU:
                locale = new Locale("ru", "RU");
                break;
            case IN_ID:
                locale = new Locale("in", "ID");
                break;
            case JA_JP:
                locale = Locale.JAPAN;
                break;
            case KO_KR:
                locale = Locale.KOREA;
                break;
            case PT_PT:
                locale = new Locale("pt", "PT");
                break;
            default:
                locale = Locale.ENGLISH;
                break;

        }
        // Locale.setDefault(locale);
        if (null == curLanguage || TextUtils.isEmpty(curLanguage)) {
            curLanguage = KEY_LANGUAGE;
        }
        //return selectLanguage(context, curLanguage);
        return updateResources(context, locale);
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }


    public static void switchLanguage(Context context, String value) {
        selectLanguage(context, value);
    }




}
