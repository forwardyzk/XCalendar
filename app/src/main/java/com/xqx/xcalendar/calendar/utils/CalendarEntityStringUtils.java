package com.xqx.xcalendar.calendar.utils;

import android.text.TextUtils;

import com.xqx.xcalendar.calendar.entity.SelectDayEntity;

/**
 * 字符串,bean,判断bean
 */
public class CalendarEntityStringUtils {

    /**
     * 判断时间是否为空
     *
     * @param entity
     * @return
     */
    public static boolean justDayEmpty(SelectDayEntity entity) {
        if (null == entity || entity.getYear() == -1 || entity.getMonth() == -1 || entity.getDay() == -1) {
            return true;
        }
        return false;
    }

    /**
     * bean类转化为字符串,格式化:2010-01-04
     *
     * @param dayEntity
     * @return
     */
    public static String entityToStringFormat(SelectDayEntity dayEntity) {
        return dayEntity.getYear() + "-" + dayEntity.getMonth() + "-" + dayEntity.getDay();
    }

    /**
     * bean类转化为字符串,展示,2019年01月06日
     *
     * @param dayEntity
     * @return
     */
    public static String entityToStringShow(SelectDayEntity dayEntity) {
        return dayEntity.getYear() + "年" + dayEntity.getMonth() + "月" + dayEntity.getDay() + "日";
    }

    /**
     * 字符串转化为年月日数组[年,月,日]
     *
     * @param time
     * @return
     */
    public static int[] stringToArrayYearMonthDay(String time) {
        int[] array = new int[]{-1, -1, -1};
        if (TextUtils.isEmpty(time)) {
            return array;
        }
        String[] split = time.split("-");
        if (split.length != 3) {
            return array;
        }
        try {
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int day = Integer.parseInt(split[2]);
            array[0] = year;
            array[1] = month;
            array[2] = day;
        } catch (Exception e) {

        }
        return array;

    }

    /**
     * 字符串转化为年月数组[年,月]
     *
     * @param time
     * @return
     */
    public static int[] stringToArrayYearMonth(String time) {
        int[] array = new int[]{-1, -1};
        if (TextUtils.isEmpty(time)) {
            return array;
        }
        String[] split = time.split("-");
        if (split.length != 2) {
            return array;
        }
        try {
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            array[0] = year;
            array[1] = month;
        } catch (Exception e) {

        }
        return array;

    }


    /**
     * 字符串转化为bean类
     *
     * @param time
     * @return
     */
    public static SelectDayEntity stringToEntity(String time) {
        if (TextUtils.isEmpty(time)) {
            return new SelectDayEntity(-1, -1, -1);
        }
        String[] split = time.split("-");
        if (split.length != 3) {
            return new SelectDayEntity(-1, -1, -1);
        }
        try {
            int year = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int day = Integer.parseInt(split[2]);
            return new SelectDayEntity(day, month, year);
        } catch (Exception e) {

        }
        return new SelectDayEntity(-1, -1, -1);
    }


    /**
     * 将日期转化为int值
     *
     * @param entity
     * @return
     */
    public static int entityToInt(SelectDayEntity entity) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(entity.getYear());
        if (entity.getMonth() < 10) {
            stringBuffer.append("0").append(entity.getMonth());
        } else {
            stringBuffer.append(entity.getMonth());
        }
        if (entity.getDay() < 10) {
            stringBuffer.append("0").append(entity.getDay());
        } else {
            stringBuffer.append(entity.getDay());
        }

        return Integer.parseInt(stringBuffer.toString());

    }

}
