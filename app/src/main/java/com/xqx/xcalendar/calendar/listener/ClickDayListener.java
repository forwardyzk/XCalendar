package com.xqx.xcalendar.calendar.listener;

import com.xqx.xcalendar.calendar.entity.SelectDayEntity;

public interface ClickDayListener {

    /**
     * 点击日期的监听器
     * @param startDay
     * @param endDay
     */
    void clickDay(SelectDayEntity startDay, SelectDayEntity endDay);
}
