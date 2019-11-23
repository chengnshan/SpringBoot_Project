package com.cxp.springboot2webmagic.util;

/**
 * @author : cheng
 * @date : 2019-11-09 11:12
 */
public class StringUtil {

    public static String conveterStr(String str){
        if (str == null){
            return "";
        }
        String newStr = str.replaceAll("\\u00A0+","").trim();
        if ("null".equals(newStr)){
            return "";
        }
        return newStr;
    }

    public static void main(String[] args) {
        String str = "深圳-光明新区  ";
        System.out.println(conveterStr1(str));
    }

    public static String conveterStr1(String str){
        if (str == null){
            return "";
        }
        str = str.replaceAll("\\u00A0+","").trim();
        if ("null".equals(str)){
            return "";
        }
        return str;
    }
}
