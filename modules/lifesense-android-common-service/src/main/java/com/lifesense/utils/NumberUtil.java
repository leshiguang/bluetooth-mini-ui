/*
 * 文件名:  NumberUtil.java
 * 版权:   广州动心信息科技有限公司
 * 创建人:  liguoliang
 * 创建时间:2017-04-18
 */

package com.lifesense.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liguoliang
 * @date 2017/4/18
 */

public class NumberUtil {

    /**
     * 判断数字是否为空或者为0
     * @param number
     * @return
     */
    public static boolean isNumberNullOrZero(Number number) {
        if (number == null || number.equals(0)) {
            return true;
        }
        return false;
    }

    /**
     *
     * 判断数字是否大于0 mt: more than
     *
     * @param number
     * @return
     */
    public static boolean isNumberMtZero(Number number) {
        if (number == null) {
            return false;
        }
        if (number instanceof Double) {
            return number.doubleValue() > 0;
        } else if (number instanceof Byte) {
            return number.byteValue() > 0;
        } else if (number instanceof Float) {
            return number.floatValue() > 0;
        } else if (number instanceof Integer) {
            return number.intValue() > 0;
        } else if (number instanceof  Long) {
            return number.longValue() > 0;
        } else if (number instanceof  Short) {
            return  number.shortValue() > 0;
        }
        return false;
    }

    /**
     *
     * 判断数字列表是否全部大于0
     *
     * @param numberList
     * @return
     */
    public static boolean isNumberListMtZero(List<Number> numberList) {
        if (numberList == null || numberList.isEmpty()) {
            return false;
        }
        boolean isMtZero = true;
        for (Number number : numberList) {
            if (!isNumberMtZero(number)) {
                isMtZero = false;
                break;
            }
        }
        return isMtZero;
    }

    /**
     *
     * 判断数字是否小于0 mt: less than
     *
     * @param number
     * @return
     */
    public static boolean isNumberLtZero(Number number) {
        if (number == null) {
            return false;
        }
        if (number instanceof Double) {
            return number.doubleValue() < 0;
        } else if (number instanceof Byte) {
            return number.byteValue() < 0;
        } else if (number instanceof Float) {
            return number.floatValue() < 0;
        } else if (number instanceof Integer) {
            return number.intValue() < 0;
        } else if (number instanceof  Long) {
            return number.longValue() < 0;
        } else if (number instanceof  Short) {
            return  number.shortValue() < 0;
        }
        return false;
    }

    /**
     * 四舍五入格式化double，指定保留newScale位小数
     *
     * @param newScale 保留小数位数
     * @param d        输入
     * @return 格式化后的小数
     */
    public static double formatDouble(int newScale, double d) {
        BigDecimal b = new BigDecimal(d);
        double f1 = b.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     * 四舍五入格式化float，指定保留newScale位小数
     *
     * @param newScale 保留小数位数
     * @param f        输入
     * @return 格式化后的小数
     */
    public static float formatFloat(int newScale, float f) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(newScale, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    /**
     * 转化为整数
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static Integer getIntValue(String str, Integer defaultValue) {
        Integer result = defaultValue;
        try {
            result = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 转化为长整数
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static Long getLongValue(String str, Long defaultValue) {
        Long result = defaultValue;
        try {
            result = Long.valueOf(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

}
