package com.lifesense.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lifesense.utils.NumberUtil.formatDouble;
import static com.lifesense.utils.NumberUtil.formatFloat;

/**
 * 字符串工具类
 *
 * @author rolandxu
 */
public class StringUtil {

    private static final String HEX_STRING = "0123456789ABCDEF";
    private static final String TAG = "StringUtil";

    private StringUtil() {

    }


    /**
     * 字符串是否空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmptyOrNullChar(String str) {
        if (str == null || str.length() == 0 || str.toUpperCase().equals("NULL")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串里面，都是’0‘
     *
     * @param srt
     * @return true 都是0
     * false 有至少一个字符不为0
     */
    public static boolean isAllZero(String srt) {
        char[] chars = srt.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * @param data
     * @param dataLen
     * @return
     */
    public static String fillZero(String data, int dataLen) {
        while (data.length() < dataLen) {
            data = "0" + data;
        }
        return data;
    }

    /**
     * 剪切字符串
     *
     * @param sourceString
     * @param fromIndex
     * @param subLength
     * @return
     */
    public static String subString(String sourceString, int fromIndex, int subLength) {
        String result = sourceString;
        int length = sourceString.length();
        if (fromIndex > 0 && fromIndex < length) {
            if ((fromIndex + subLength) > length) {
                subLength = length - fromIndex;
            }

            result = sourceString.substring(fromIndex, subLength + fromIndex);
        }
        return result;
    }

    /**
     * 剪切中间的字符串，替换为……
     *
     * @param sourceStirng
     * @param maxLength    最大长度
     * @param tailLength   尾部长度
     * @return
     */
    public static String subCenterString(String sourceStirng, int maxLength, int tailLength) {
        StringBuilder result = null;
        int length = sourceStirng.length();
        if (length > maxLength && maxLength > 10) {
            if (tailLength <= 0) {
                tailLength = maxLength / 2;
            }

            int headLength = maxLength - tailLength;
            result = new StringBuilder();
            result.append(sourceStirng.substring(0, headLength));
            result.append("………………");
            result.append(sourceStirng.substring(length - tailLength));
        } else {
            result = new StringBuilder(sourceStirng);
        }

        return result.toString();
    }

    /**
     * 使用variableMap替换source中的${variable_name}
     *
     * @param source      需要替换的字符串
     * @param variableMap 需要替换的变量名-变量值Map
     * @return
     */
    public static String replaceVariables(String source, Map<String, String> variableMap) {
        if (source == null)
            return null;
        if (variableMap == null || variableMap.size() == 0)
            return source;

        //用参数替换模板中的${}变量
        Matcher matcher = Pattern.compile("\\$\\{\\w+\\}").matcher(source);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String param = matcher.group(); //${variable_name}
            String variableName = param.substring(2, param.length() - 1);
            Object variableValue = variableMap.get(variableName);

            matcher.appendReplacement(sb, variableValue == null ? "" : variableValue.toString());
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * UTF-8 URL编码
     *
     * @param str
     * @return
     */
    public static String urlEncodeUtf8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LSLog.e(TAG, String.format("fail to urlEncodeUtf8:%s, exception:%s", str, e.getMessage()));
            return str;
        }
    }

    /**
     * 字符串数组是否包含字符串
     *
     * @param strArray 要查找的字符串数组
     * @param str      要查找的字符串
     * @return
     */
    public static boolean isArrayContains(String[] strArray, String str) {
        boolean result = false;

        if (strArray == null || str == null) {
            return result;
        }

        for (int i = 0; i < strArray.length; i++) {
            String tempString = strArray[i];
            if (tempString.equals(str)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 检查字符串是否为url
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        boolean result = false;
        try {
            new URL(str);
            result = true;
        } catch (MalformedURLException e) {

        }
        return result;
    }


    /**
     * 验证是否为email格式
     */
    public static boolean isEmail(String line) {
        if (StringUtil.isEmptyOrNull(line)) {
            return false;
        }

        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(line);
        return m.find();
    }

    public static boolean isValidPhoneNum(String phonenum, String citycode) {
        boolean ret = false;
        if (citycode.equalsIgnoreCase("+86") || citycode.equalsIgnoreCase("86")) {
            if (phonenum.length() == 11 || phonenum.length() == 12) {
                ret = true;
            }
        } else if (citycode.equalsIgnoreCase("+886")) {
            if (phonenum.length() == 9 || phonenum.length() == 10) {
                ret = true;
            }
        } else if (citycode.equalsIgnoreCase("+852")) {
            if (phonenum.length() == 8) {
                ret = true;
            }
        } else if (citycode.equalsIgnoreCase("+853")) {
            if (phonenum.length() == 8) {
                ret = true;
            }
        }

        return ret;
    }

    public static boolean isNumber(String string) {
        return Pattern.compile("^[0-9]+(.[0-9]*)?$").matcher(string).matches();
    }

    /**
     * 将String List合成为字符串
     *
     * @param strings
     * @param seperator 分割符
     * @return
     */
    public static String getStringArrayListString(List<String> strings, String seperator) {
        StringBuilder sb = new StringBuilder();
        int length = strings.size();
        int index = 0;
        for (String string : strings) {
            index++;
            sb.append(string);
            if (index < length)
                sb.append(seperator);
        }
        return sb.toString();
    }

    /**
     * 将String List合成为字符串
     *
     * @param strings
     * @param seperator 分割符
     * @return
     */
    public static String getStringArrayListString(String[] strings, String seperator) {
        StringBuilder sb = new StringBuilder();
        int length = strings.length;
        int index = 0;
        for (String string : strings) {
            index++;
            sb.append(string);
            if (index < length)
                sb.append(seperator);
        }
        return sb.toString();
    }

    /**
     *   
     *      * 使用java正则表达式去掉多余的.与0  
     *      * @param s  
     *      * @return   
     *      
     */
    @Deprecated
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }
        return s;
    }

    public static boolean isAllValue(int[] ints, int value) {
        boolean isAllZero = true;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] != value) {
                return false;
            }
        }
        return isAllZero;
    }

    /**
     * @param hex
     * @return
     */
    public static byte[] hexStr2Bytes(String hex) {
        String upperHex = hex.toUpperCase();
        int len = (upperHex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = upperHex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    public static String integerList2hexString(List<Integer> list) {
        StringBuilder buf = new StringBuilder(list.size() * 2);
        for (int aByte : list) {
            if ((aByte & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(aByte & 0xff, 16));
        }
        return buf.toString().toLowerCase();
    }

    public static String bytes2HexStr(byte[] bArray) {
        return bytes2HexStr(bArray, null);
    }

    /**
     * @param bArray
     * @param intervalStr
     * @return
     */
    public static String bytes2HexStr(byte[] bArray, String intervalStr) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String temp;
        for (byte b : bArray) {
            temp = Integer.toHexString(0xFF & b);
            if (temp.length() < 2) {
                sb.append(0);
            }
            sb.append(temp.toUpperCase());
            if (intervalStr != null && !intervalStr.equals("")) {
                sb.append(intervalStr);
            }
        }
        return sb.toString();
    }

    public static String SHA1Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = sourceString;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            resultString = bytes2HexStr(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * @param asc
     * @return
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        if (len >= 2) {
            len = len / 2;
        }

        byte[] bbt = new byte[len];
        byte[] abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }


    private static byte toByte(char c) {
        return (byte) HEX_STRING.indexOf(c);
    }

    public static String ArrayToString(String[] arr) {
        StringBuilder bf = new StringBuilder();
        for (String str : arr) {
            bf.append(str);
        }
        return bf.toString();
    }

    /**
     * 隐去手机号中间4位 137****6969
     *
     * @param phoneNum
     * @return
     */
    public static String getProcessPhoneNum(String phoneNum) {
        if (isBlank(phoneNum)) {
            return "";
        }

        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(7);
    }

//    /**
//     * 匹配显示数据格式（四舍五入）
//     *
//     * @param value
//     * @param persistAfterDot 保留小数点后几位
//     * @return
//     */
//    public static String roundFormat(double value, int persistAfterDot) {
//        if (persistAfterDot == 0) {
//            return String.valueOf(Math.round(value));
//        } else {
//            value = value * (Math.pow(10, persistAfterDot));
//            value = Math.round(value) / (Math.pow(10.0, persistAfterDot));
//            return String.valueOf(value);
//        }
//    }
//
//    public static String roundFormat(double value, int persistAfterDot, boolean isInt) {
//        double result = new BigDecimal(value).setScale(persistAfterDot, BigDecimal.ROUND_HALF_UP).doubleValue();
//        String str = String.format("%." + persistAfterDot + "f", result);
//        if (isInt && str.contains(".")) {
//            str = str.substring(0, str.lastIndexOf("."));
//        }
//        return str;
//    }

    private static Map<String, DecimalFormat> gFormatterMap = new Hashtable<>();

    /**
     * 保留一位小数，四舍五入 double
     *
     * @param d
     * @return 格式化后的小数
     */
    @Deprecated
    public static String doubleFormatString(double d) {
//        long templ = Math.round(d * 10);
//        double retd = templ / 10.0;
//        int intd = (int) retd;
//        if (retd == intd) {
//            return String.valueOf(intd);
//        }
//        return String.format("%.1f", retd);
        String str = numberFormatStringWithDecimalOptional(1, d);
        return format(str);
    }

    /**
     * 保留decimalCount位小数，四舍五入，没有小数则直接返回整数字符串
     *
     * @param persistAfterDot 小数位数
     * @param number          数字
     * @return 格式化后的小数字符串
     */
    public static String numberFormatStringWithDecimalOptional(int persistAfterDot, Number number) {
        String ret = null;
        if (number == null) {
            ret = null;
        } else if (number instanceof Float) {
            ret = floatFormatStringWithDecimalOptional(persistAfterDot, number.floatValue());
        } else if (number instanceof Double) {
            ret = doubleFormatStringWithDecimalOptional(persistAfterDot, number.doubleValue());
        } else {
            ret = number.toString();
        }
        return format(ret);
    }

    /**
     * 保留persistAfterDot位小数，四舍五入 float，没有小数则直接返回整数字符串
     *
     * @param persistAfterDot 小数位数
     * @param f
     * @return 格式化后的小数字符串
     */
    public static String floatFormatStringWithDecimalOptional(int persistAfterDot, float f) {
        float ff = formatFloat(persistAfterDot, f);//保留小数点1位，四舍五入
        StringBuilder patternbuilder = new StringBuilder("#.");
        for (int i = 0; i < persistAfterDot; i++) {
            patternbuilder.append("#");
        }
        DecimalFormat formatter = getFormatter(patternbuilder.toString());
        String str = formatter.format(ff);
        return format(str);
    }

    /**
     * 保留persistAfterDot位小数，四舍五入 float
     *
     * @param persistAfterDot 小数位数
     * @param f
     * @return 格式化后的小数字符串
     */
    public static String floatFormatString(int persistAfterDot, float f) {
        float ff = formatFloat(persistAfterDot, f);//保留小数点1位，四舍五入
        String str = String.valueOf(ff);
        return format(str);
    }

    /**
     * 保留persistAfterDot位小数，四舍五入 double，没有小数则直接返回整数字符串
     *
     * @param persistAfterDot 小数位数
     * @param d
     * @return 格式化后的小数字符串
     */
    public static String doubleFormatStringWithDecimalOptional(int persistAfterDot, double d) {
        double dd = formatDouble(persistAfterDot, d);//保留小数点1位，四舍五入
        StringBuilder patternbuilder = null;
        if (persistAfterDot > 0){
            patternbuilder = new StringBuilder("#.");
        }
        else{
            patternbuilder = new StringBuilder("");
        }
        for (int i = 0; i < persistAfterDot; i++) {
            patternbuilder.append("#");
        }
        DecimalFormat formatter = getFormatter(patternbuilder.toString());
        String str = formatter.format(dd);
        return format(str);
    }
    private static String format(String str){
        String oldStr=str;
        try {
            if (str.contains(",")) {
                str = str.replace(",", ".");
            }
        } catch (Exception e) {
            str = oldStr;
        }

        return str;
    }

    /**
     * 保留persistAfterDot位小数，四舍五入 double
     *
     * @param persistAfterDot 小数位数
     * @param d
     * @return 格式化后的小数字符串
     */
    public static String doubleFormatString(int persistAfterDot, double d) {
        double dd = formatDouble(persistAfterDot, d);//保留小数点1位，四舍五入
        String str = String.valueOf(dd);
        return format(str);
    }

    /**
     * 保留两位小数，四舍五入 double
     *
     * @param d
     * @return 格式化后的小数
     */
    @Deprecated
    public static String doubleFormatString2(double d) {
//        long templ = Math.round(d * 100);
//        double retd = templ / 100.0;
//        int intd = (int) retd;
//        if (retd == intd) {
//            return String.valueOf(intd);
//        }
//        //        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//        //       return df.format(d);
//
//        return String.format("%.2f", d);
        String str = numberFormatStringWithDecimalOptional(2, d);
        return format(str);
    }


    /**
     * @param pattern
     * @return
     */
    private static DecimalFormat getFormatter(String pattern) {
        DecimalFormat ret = gFormatterMap.get(pattern);
        if (ret == null) {
            ret = new DecimalFormat(pattern);
            DecimalFormatSymbols symbols=new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            ret.setDecimalFormatSymbols(symbols);
            gFormatterMap.put(pattern, ret);
        }
        return ret;
    }

    /**
     * 生成16位UUID
     *
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return boolean
     */
    public static boolean isBlank(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        double number = 0.17d;
        System.out.println(doubleFormatString(number));
//        String testString = "abcdefghijklmnopqrstuvwxyz";
//        System.out.println(subCenterString(testString, 20, 5));
//        System.out.println(subCenterString(testString, 80, 0));
//        System.out.println(subCenterString(testString, -20, 5));
//        System.out.println(subCenterString(testString, 20, -5));
//
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//        hashMap.put("key1", "key1value");
//        hashMap.put("key2", "key2value");
//        hashMap.put("key3", "key3value");
//        System.out.println(replaceVariables("${key2} love ${key1}, but ${key3} donot", hashMap));
//        System.out.println(replaceVariables("${key22} love ${key1}, but ${key3} donot", hashMap));
//
//        System.out.println(urlEncodeUtf8("cn中国"));
//
//        String hostReg = "^([{a-zA-Z0-9\\-\\.}]+\\.(qq.com|wechat.com))|(qq.com|wechat.com|112.90.138.87)$";
//        System.out.println("hk.qq.com".matches(hostReg));
//        System.out.println("abc.abc.qq.com".matches(hostReg));
//        System.out.println("hkwl-pre.qq.com".matches(hostReg));
//        System.out.println("qq.com".matches(hostReg));
//        System.out.println("abc.wechat.com".matches(hostReg));
//        System.out.println("wechat.com".matches(hostReg));
//        System.out.println("abc.qq.wechat.com".matches(hostReg));
//        System.out.println("112.90.138.87".matches(hostReg));
//        System.out.println("iqq.com".matches(hostReg));
//        System.out.println("qq.com.abc.com".matches(hostReg));
//        System.out.println("112.90.138.88".matches(hostReg));
//        System.out.println("qqcom".matches(hostReg));
//        System.out.println("hk.qq.com".matches("^([{a-zA-Z0-9\\-\\.}]+\\.(qq.com|wechat.com))|(qq.com|wechat.com|112.90.138.87)$"));
//
//        System.out.println("=====list======");
//        List<String> list = new ArrayList<String>();
//        list.add("abc");
//        list.add("cde");
//        System.out.println(list.contains("abc"));
//        System.out.println(list.contains("cde"));
//        System.out.println(list.contains("a"));
//        System.out.println(list.contains("f"));

    }
}
