package com.mbcq.baselibrary.util.regular;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    /**
     * 保留小数后几位
     *
     * @param value
     * @param end
     * @return
     */
    public static String format(String value, int end) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(end, RoundingMode.HALF_UP);
        return bd.toString();
    }

}
