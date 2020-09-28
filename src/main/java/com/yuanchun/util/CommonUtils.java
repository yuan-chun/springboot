package com.yuanchun.util;

import java.util.*;

public class CommonUtils {






    /**
     * 驼峰转换成带有下划线的命名
     * @date: 2016-11-25
     * @author: yangwl
     * @title: camelToUnderline
     * @param param
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(Const.SEPARATOR_UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 转换标签表达式为JS表达式
     *
     * @date: 2016-11-14
     * @author: yangwl
     * @title: convertLabelExprToJsExpr
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static String convertLabelExprToJsExpr(String labelExpr) {
        labelExpr = labelExpr.replaceAll("!=", "!@");
        labelExpr = labelExpr.replaceAll(">=", "!#");
        labelExpr = labelExpr.replaceAll("<=", "@@");
        labelExpr = labelExpr.replaceAll("=", "==");
        labelExpr = labelExpr.replaceAll(" and ", " && ");
        labelExpr = labelExpr.replaceAll(" or ", " || ");
        labelExpr = labelExpr.replaceAll("@@", "<=");
        labelExpr = labelExpr.replaceAll("!#", ">=");
        labelExpr = labelExpr.replaceAll("!@", "!=");
        return labelExpr;
    }

    /**
     * 将List用指定的分割符转化成字符串
     * @date: 2016-11-24
     * @author: zhangjj_crmpd
     * @title: listToString
     * @param list
     * @param separator
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String listToString(List<String> list, String separator) {
        if(objectIsNull(list)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * 将Map中Value为null的值替换成空
     *
     * @date: 2016-10-25
     * @author: zhangjj_crmpd
     * @title: mapValueIsNullRep
     * @param map
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static void mapValueIsNullRep(Map<String, Object> map) {
        if (objectIsNull(map)) {
            return;
        }
        for (Map.Entry<String, Object> me : map.entrySet()) {
            Object value = me.getValue();
            if (value == null || value.equals("null")) {
                map.put(me.getKey(), "");
            }
        }
    }

    /**
     * 将Map中Value为null的值替换成空
     * @date: 2016-12-8
     * @author: sunliang
     * @title: mapValueIsNullRepByStr
     * @param map
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static void mapValueIsNullRepByStr(Map<String, String> map) {
        if (objectIsNull(map)) {
            return;
        }
        for (Map.Entry<String, String> me : map.entrySet()) {
            Object value = me.getValue();
            if (value == null || value.equals("null")) {
                map.put(me.getKey(), "");
            }
        }
    }

    /**
     * 判断对象是否为空(支持String、list、map 判断是否为空字符串、size等于零)
     *
     * @date: 2016-10-5
     * @author: zhangjj_crmpd
     * @title: objectIsNull
     * @param object
     * @return 如果为空 返回 true 有值 返回 false
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static boolean objectIsNull(Object object) {

        /*
         * 判断对象是否为空
         */
        if (object == null) {
            return true;
        }

        /*
         * 判断对象是否 List类型 如果是判断size大小
         */
        if (object instanceof List) {
            if (((List) object).size() <= 0) {
                return true;
            }
        }

        /*
         * 判断对象是否 Map类型 如果是判断size大小
         */
        if (object instanceof Map) {
            if (((Map) object).size() <= 0) {
                return true;
            }
        }

        /*
         * 判断对象是否 Set类型 如果是判断size大小
         */
        if (object instanceof Set) {
            if (((Set) object).size() <= 0) {
                return true;
            }
        }

        /*
         * 判断对象是否String 类型
         */
        if (object instanceof String) {
            return (((String) object).isEmpty()||((String) object).trim().equals("null"));
        }

        if (object instanceof String[]){
            if(((String[]) object).length <= 0) {
                return true;
            }
        }

        return false;
    }

    /** 翻转字符串
     * @date: 2017-5-9
     * @author: wangpei
     * @title: reverse
     * @param str
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String reverse(String str){
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 空对象替换成空字符串
     *
     * @date: 2016-11-2
     * @author: wangpei
     * @title: replaceNull
     * @param in
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static Object replaceNull(Object in) {
        if (in == null) {
            return "";
        }
        return in;
    }

    /**
     * 转换成驼峰命名
     *
     * @date: 2016-11-14
     * @author: wangpei
     * @title: toCmelModel
     * @param name
     * @return
     * @exception:
     * @version: 1.0
     * @description: update_version: update_date: update_author: update_note:
     */
    public static String toCamelModel(String name) {
        name = name.toLowerCase();
        String[] temp = name.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.length; i += 1) {
            if (temp[i].length() <= 0)
                continue;
            if (i != 0) {
                sb.append(temp[i].substring(0, 1).toUpperCase());
            } else {
                sb.append(temp[i].substring(0, 1));
            }
            sb.append(temp[i].substring(1));
        }
        return sb.toString();
    }

    /**
     * 将map里的健转为驼峰命名
     * @date: 2016-12-24
     * @author: sunliang
     * @title: toCamelModelByMap
     * @param map
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static Map<String,String> toCamelModelByMap(Map<String,String> map) {
        Map<String,String> retMap = new HashMap<String,String>();
        if(!CommonUtils.objectIsNull(map)){
            for(Map.Entry<String, String> me : map.entrySet()){
                String value = me.getValue();
                String key = CommonUtils.toCamelModel(me.getKey());
                retMap.put(key, value);
            }
        }
        return retMap;

    }

    /**
     * 返回该对象的toString方法，如果对象为空 返回""
     * @date: 2016-12-8
     * @author: zhangjj_crmpd
     * @title: toString
     * @param obj
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String toString(Object obj){
        return obj==null?"":obj.toString();
    }

    /**
     * 从map中获取int，如果不存在数据的情况，会返一个0
     * @date: 2017-8-2
     * @author: zhangjj_crmpd
     * @title: getIntFromMap
     * @param map
     * @param key
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static Integer getIntFromMap(Map map, String key){
        int v = 0;
        if (map == null) {
            return v;
        }

        Object val = map.get(key);
        if (objectIsNull(val)) {
            return v;
        }
        v = Integer.valueOf((String.valueOf(val)).trim());
        return v;
    }
    /**
     * 将尾号的零全部去除
     * @date: 2017年8月31日
     * @author: zhangyw_crmpd
     * @title: delZero
     * @param src
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String delZero(String src) {
        if (src.endsWith("0"))
            return delZero(src.substring(0, src.length() - 1));
        else
            return src;
    }

    /**
     * 屏蔽短信中特殊字符；
     * @date: 2018年3月15日
     * @author: janln
     * @title: getPushShortMsg
     * @param shortMsg
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public static String getPushShortMsg(String shortMsg){
        if (!CommonUtils.objectIsNull(shortMsg)) {
            List<String> inValidStrList = new ArrayList<String>();
            inValidStrList.add("\\|");
            inValidStrList.add("\\{");
            inValidStrList.add("\\}");
            inValidStrList.add("\\>");
            inValidStrList.add("\\<");
            inValidStrList.add("\\\"");
            for (String str : inValidStrList) {
                shortMsg = shortMsg.replaceAll(str, "");
            }
        }
        return shortMsg;
    }


}
