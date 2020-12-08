package com.mbcq.baselibrary.util.system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    /**
     * 剔除数字
     *
     * @param value
     */
    public static String removeDigital(String value) {

        Pattern p = Pattern.compile("[\\d]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除字母
     *
     * @param value
     */
    public static String removeLetter(String value) {
        Pattern p = Pattern.compile("[a-zA-z]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 替换
     *
     * @param value
     * @param replaceParam
     */
    public static String replaceLetter(String value, String replaceParam) {
        Pattern p = Pattern.compile("[a-zA-z]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll(replaceParam);
        return result;
    }
}