package com.base.app.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理公共类
 */
public class StringUtils {

    public static String SubString(String sourStr, int length) {
        String reMsg = "";

        if (sourStr.length() <= length) {
            reMsg = sourStr;
        } else {
            reMsg = sourStr.substring(0, length - 1) + "...";
        }
        return reMsg;
    }

    /**
     * 判断空字符串
     * 字符串为“null”时也判断为空
     *
     * @param str
     * @return
     */
    public static boolean IsStringNull(String str) {
        if (str == null || "null".equalsIgnoreCase(str) || str.trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 针对用户银行账户隐藏特定位数，以"*"代替
     *
     * @param sStr
     * @param showLastSize
     * @return
     */
    public static String HideCardNum(String sStr, int showLastSize) {
        if (sStr != null) {
            int size = sStr.length();

            if (showLastSize < size) {
                String tmpStr = sStr.substring(size - showLastSize);
                String tmpStr2 = sStr.substring(0, 4);
                StringBuffer hideSb = new StringBuffer();

                for (int i = 4; i < size - showLastSize; i++) {
                    hideSb.append('*');
                    if ((i + 1) % 4 == 0) {
                        hideSb.append(' ');
                    }
                }
                return tmpStr2 + " " + hideSb.toString() + tmpStr;
            }
        }

        return "";
    }

    /**
     * 隐蔽手机号码中间部分，以"*"代替
     *
     * @param cellPhoneStr
     * @return
     */
    public static String HideCellPhone(String cellPhoneStr) {
        if (cellPhoneStr != null && cellPhoneStr.length() == 11) {
            StringBuffer sb = new StringBuffer(cellPhoneStr);
            int hideSize = 8;

            for (int i = 3; i <= hideSize; i++) {
                sb.setCharAt(i, '*');
            }

            return sb.toString();
        }

        return "";
    }

    /**
     * 银行卡账号格式化
     *
     * @param str
     * @return
     */
    public static String bankFormat(String str) {
        if (IsStringNull(str)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(str);
        int length = str.length() / 4 + str.length();

        for (int i = 0; i < length; i++) {
            if (i % 5 == 0) {
                sb.insert(i, " ");
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 格式化科学计算E
     *
     * @param num
     * @return
     */
    public static String formatFloat(float num) {
        BigDecimal bd = new BigDecimal(""+num);
        return bd.stripTrailingZeros().toPlainString();//保留两位数字，并且是截断不进行四舍五
    }

    /**
     * 格式化科学计算E
     *
     * @param num
     * @return
     */
    public static String formatFloat(String num) {
        BigDecimal bd = new BigDecimal(num);
      return bd.stripTrailingZeros().toPlainString();//保留两位数字，并且是截断不进行四舍五
    }

    /**
     * 格式化科学计算E
     *
     * @param num
     * @return
     */
    public static String formatFloat(double num) {
        BigDecimal bd = new BigDecimal(""+num);
        return bd.stripTrailingZeros().toPlainString();//保留两位数字，并且是截断不进行四舍五
    }

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content  传入的字符串
     * @param frontNum 保留前面字符的位数
     * @param endNum   保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    /**
     * 把有效数据之后的0删掉
     *
     * @param sourVal
     * @return
     */
    public static int removeLastZero(int sourVal) {
        int tmpVal = sourVal;
        if (tmpVal > 0) {
            while (tmpVal % 10 == 0) {
                tmpVal = tmpVal / 10;
            }
        }
        return tmpVal;
    }


    /**
     * 判断密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */
    public static int passwordStrong(String str) {
        if (TextUtils.equals("", str)) {
            return 0;
        }
        String regex = ".*[a-z]+.*";
        String regex2 = ".*[A-Z]+.*";
        String regex3 = ".*[0-9]+.*";
        //输入的纯数字为弱
        if (str.matches("^[0-9]{1,32}")) {
            return 1;
        }
        //输入的纯小写字母为弱
        else if (str.matches("^[a-z]{1,32}")) {
            return 1;
        }
        //输入的纯大写字母为弱
        else if (str.matches("^[A-Z]{1,32}")) {
            return 1;
        }
        //输入的纯字符为弱
        else if (str.matches("^[\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 1;
        }
        //输入的大写字母和数字，输入的字符密码为二级
        else if (str.matches("^[A-Z0-9]{1,32}")) {
            return 2;
        }
        //输入的小写字母和数字，输入的字符小于7个密码为二级
        else if (str.matches("^[a-z0-9]{1,32}")) {
            return 2;
        }
        //输入的大写字母和小写字母，输入的字符密码为二级
        else if (str.matches("^[A-Za-z]{1,32}")) {
            return 2;
        }
        //输入的大写字母和字符，输入的字符密码为二级
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }
        //输入的小写字母和字符，输入的字符密码为二级
        else if (str.matches("^[a-z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }
//输入的小写字母和字符，输入的字符密码为二级
        else if (str.matches("^[0-9\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 2;
        }

        //输入的大写字母和小写字母和数字，输入的字符个密码为三级
        else if (str.matches("^[A-Za-z0-9]{1,32}")) {
            return 3;
        }
        //输入的大写字母和小写字母和字符，输入的字符个密码为三级
        else if (str.matches("^[A-Za-z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、]{1,32}")) {
            return 3;
        }
        //输入的大写字母和字符和数字，输入的字符大于8个密码为强
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、0-9]{1,32}")) {
            return 3;
        }
        //输入的字符和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches("^[\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、a-z0-9]{1,32}")) {
            return 3;
        }
        //输入的字符大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches("^[A-Z\"`~!@#$%^&*()_\\-+=<>?:\\\"{}|,./;'\\[\\]·~！@#￥%……&*（）——+={}|《》？：“”【】、；‘'，。、a-z0-9]{1,32}")) {
            return 4;
        } else if ((Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex2).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
        ) {
            return 4;
        } else if ((Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex2).matcher(str).matches())
                | (Pattern.compile(regex).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
                | (Pattern.compile(regex2).matcher(str).matches() && Pattern.compile(regex3).matcher(str).matches())
        ) {
            return 3;
        } else if (Pattern.compile(regex).matcher(str).matches() | Pattern.compile(regex2).matcher(str).matches() | Pattern.compile(regex3).matcher(str).matches()) {

            return 2;
        }
        return 1;
    }

    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }


    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    /*数字和字母可以有特殊符号*/
    public static boolean isPass(String str) {
        String regex = "^.*(?=.{6,16})(?=.*\\d)(?=.*[a-z])[A-Za-z0-9!@#$%^&*?].*$";
        boolean isRight = str.matches(regex);
        return isRight;
    }

    public static boolean isPhone(String phoneStr) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(phoneStr);
        if (isNum.matches() && phoneStr.length() > 4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCode(String phoneStr) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (phoneStr.equals("")) {
            return false;
        } else {
            Matcher isNum = pattern.matcher(phoneStr);
            if (isNum.matches()) {
                return true;
            } else {
                return false;
            }
        }

    }

}
