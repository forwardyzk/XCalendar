package com.xqx.xcalendar.calendar.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xqx.xcalendar.R;
import com.xqx.xcalendar.calendar.entity.SelectDayEntity;
import com.xqx.xcalendar.calendar.listener.ClickDayListener;
import com.xqx.xcalendar.calendar.listener.DayListener;
import com.xqx.xcalendar.calendar.utils.CalendarEntityStringUtils;

import java.util.ArrayList;


/**
 * 每个月中的天数适配器
 */
public class SelectValidTimeAdapter extends RecyclerView.Adapter<SelectValidTimeViewHolder> {

    private ArrayList<SelectDayEntity> mMonthOfDaysData;
    private Context context;

    private SelectDayEntity mCurrentSystemDay;//系统的当前时间

    private int allowTime = 0;  //允许选择的结束时间和开始时间相差的天数

    public SelectValidTimeAdapter(ArrayList<SelectDayEntity> mMonthOfDaysData, Context context) {
        this.mMonthOfDaysData = mMonthOfDaysData;
        this.context = context;
    }

    private ClickDayListener mListener;
    private DayListener mDayListener;

    public void setClickDayListener(DayListener dayListener, ClickDayListener listener) {
        this.mListener = listener;
        this.mDayListener = dayListener;
        startDay = mDayListener.getStartDay();
        endDay = mDayListener.getEndDay();
        mCurrentSystemDay = mDayListener.getSystemDay();
        Log.d("-000--", mMonthOfDaysData.size() + "-Holder开始时间:" + startDay.toString() + "结束时间:" + endDay.toString());

    }

    public void setAllowTime(int allowTime) {
        this.allowTime = allowTime - 1;
    }

    @Override
    public SelectValidTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectValidTimeViewHolder ret = null;
        View v = LayoutInflater.from(context).inflate(R.layout.item_calendar_recycler_selectday, parent, false);
        ret = new SelectValidTimeViewHolder(v);
        return ret;
    }

    SelectDayEntity startDay = null;
    SelectDayEntity endDay = null;


    @Override
    public void onBindViewHolder(SelectValidTimeViewHolder holder, final int position) {
        final SelectDayEntity selectDayEntity = mMonthOfDaysData.get(position);
        //显示日期
        if (selectDayEntity.getDay() != 0) {
            holder.select_txt_day.setText(selectDayEntity.getDay() + "");
            holder.select_ly_day.setEnabled(true);
        } else {
            holder.select_ly_day.setEnabled(false);
        }
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选出点击的日期和开始日期的间隔天数

                //1.如果开始时间还没选中,那么就把当前点击时间赋给开始时间
                //2.如果开始时间已经选中,但是结束时间还未选中
                //2.1如果选中的时间小于开始时间,重新置赋给开始时间
                //2.2如果选中的时间大于等于开始时间,那么当前时间赋给结束时间
                //3.如果开始时间和结束时间都选中了,那么就把当前时间赋给开始时间,把结束时间置为空

                if (CalendarEntityStringUtils.justDayEmpty(startDay)) {
                    startDay = selectDayEntity;
                } else if (CalendarEntityStringUtils.justDayEmpty(endDay)) {
                    if (selectDayEntity.getYear() < startDay.getYear()) {
                        startDay = selectDayEntity;
                    } else if (selectDayEntity.getYear() == startDay.getYear()
                            && selectDayEntity.getMonth() < startDay.getMonth()) {
                        startDay = selectDayEntity;
                    } else if (selectDayEntity.getYear() == startDay.getYear()
                            && selectDayEntity.getMonth() == startDay.getMonth()
                            && selectDayEntity.getDay() < startDay.getDay()) {
                        startDay = selectDayEntity;
                    } else {
                        endDay = selectDayEntity;
                    }
                } else {
                    startDay = selectDayEntity;
                    endDay = new SelectDayEntity(-1, -1, -1);
                }
//
//                String selectDayString = selectDayEntity.getYear() + "-" + selectDayEntity.getMonth() + "-" + selectDayEntity.getDay();
//                String startDayString = startDay.getYear() + "-" + startDay.getMonth() + "-" + startDay.getDay();
//
//
//                String twoDay = CalendarUtil.getTwoDay(selectDayString, startDayString);
//                int gapDate = Integer.parseInt(twoDay);
//
//                if (gapDate > allowTime) {
//                    Toast.makeText(context, "结束日期距离开始日期不能超过" + (allowTime + 1) + "天", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (null != mListener) {
                    mListener.clickDay(startDay, endDay);
                }
            }
        });


        if (startDay.getYear() == selectDayEntity.getYear() && startDay.getMonth() == selectDayEntity.getMonth() && startDay.getDay() == selectDayEntity.getDay()
                && endDay.getYear() == selectDayEntity.getYear() && endDay.getMonth() == selectDayEntity.getMonth() && endDay.getDay() == selectDayEntity.getDay()) {
            //开始和结束同一天
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_calendar_time_startend);
            holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        } else if (startDay.getYear() == selectDayEntity.getYear() && startDay.getMonth() == selectDayEntity.getMonth() && startDay.getDay() == selectDayEntity.getDay()) {
            //开始点
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_calendar_time_start);
            holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        } else if (endDay.getYear() == selectDayEntity.getYear() && endDay.getMonth() == selectDayEntity.getMonth() && endDay.getDay() == selectDayEntity.getDay()) {
            //结束点
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_calendar_time_end);
            holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        } else if (!CalendarEntityStringUtils.justDayEmpty(startDay) &&
                !CalendarEntityStringUtils.justDayEmpty(endDay) &&
                CalendarEntityStringUtils.entityToInt(selectDayEntity) >= CalendarEntityStringUtils.entityToInt(startDay) &&
                CalendarEntityStringUtils.entityToInt(selectDayEntity) <= CalendarEntityStringUtils.entityToInt(endDay)) {
            if (selectDayEntity.getDay() != 0) {
                //处于开始和结束时间之间
                holder.select_ly_day.setBackgroundResource(R.color.calendar_bg_day_selected_color);
                holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            } else {
                holder.select_ly_day.setBackgroundResource(android.R.color.transparent);
            }


        } else {
            if (selectDayEntity.getYear() == mCurrentSystemDay.getYear()
                    && selectDayEntity.getMonth() == mCurrentSystemDay.getMonth()
                    && selectDayEntity.getDay() == mCurrentSystemDay.getDay()) {
                holder.select_ly_day.setBackgroundResource(R.drawable.bg_calendar_time_system);
                holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            } else {
                holder.select_txt_day.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            }


        }

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (mMonthOfDaysData != null) {
            ret = mMonthOfDaysData.size();
        }
        return ret;
    }


}
