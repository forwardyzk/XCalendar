package com.xqx.xcalendar.calendar.listener;

import com.xqx.xcalendar.calendar.entity.SelectDayEntity;

/**
 * 获取开始日期和结束日期的监听
 */
public interface DayListener {

    SelectDayEntity getStartDay();

    SelectDayEntity getEndDay();
}
