package com.yuanchun.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_FROMAT_SIMPLE1 = new String("yyyy-MM-dd HH:mm:ss");
    /**
     * 时间格式：yyyy/MM/dd HH:mm:ss
     */
    public static final String TIME_FROMAT_SIMPLE2 = new String("yyyy/MM/dd HH:mm:ss");
    /**
     * 时间格式：yyyyMMddHHmmss
     */
    public static final String TIME_FROMAT_SIMPLE3 = new String("yyyyMMddHHmmss");
    /**
     * 时间格式：yyyy年MM月dd日 HH点mm分ss秒
     */
    public static final String TIME_FROMAT_SIMPLE4 = new String("yyyy年MM月dd日 HH点mm分ss秒");

    /**
     * 时间格式：yyyyMMddHH
     */
    public static final String TIME_FROMAT_SIMPLE5 = new String("yyyyMMddHH");


    /**
     * 时间格式：HH:mm:ss
     */
    public static final String TIME_FROMAT_HHMMSS = new String("HH:mm:ss");
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String DATE_FROMAT_YYYYMMDD1 = new String("yyyyMMdd");
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DATE_FROMAT_YYYYMMDD2 = new String("yyyy-MM-dd");
    /**
     * 日期格式：yyyymm
     */
    public static final String DATE_FROMAT_YYYYMM = new String("yyyyMM");

    /**
     * 获取当前日期
     * @return
     */
    public static final String getCurrentDay() {
        return DateToString(new Date(), DATE_FROMAT_YYYYMMDD1);
    }

    /**
     * DateToString
     * @param date
     * @param formatStr
     * @return
     */
    public static final String DateToString(Date date, String formatStr) {
        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        return sf.format(date);
    }
}
