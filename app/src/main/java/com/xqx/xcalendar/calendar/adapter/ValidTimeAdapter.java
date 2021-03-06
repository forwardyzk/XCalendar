package com.xqx.xcalendar.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xqx.xcalendar.R;
import com.xqx.xcalendar.calendar.entity.PlanTimeEntity;
import com.xqx.xcalendar.calendar.entity.SelectDayEntity;
import com.xqx.xcalendar.calendar.listener.ClickDayListener;
import com.xqx.xcalendar.calendar.listener.DayListener;
import com.xqx.xcalendar.calendar.utils.CalendarEntityStringUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 年月适配器
 */
public class ValidTimeAdapter extends RecyclerView.Adapter<ValidTimeViewHolder> {

    private ArrayList<PlanTimeEntity> mYearMonthsData;
    private Context context;

    public ValidTimeAdapter(ArrayList<PlanTimeEntity> yearMonthsData, Context context) {
        this.mYearMonthsData = yearMonthsData;
        if (null == mYearMonthsData) {
            mYearMonthsData = new ArrayList<>();
        }
        this.context = context;
    }

    private ClickDayListener mClickDayListener;
    private DayListener mDayListener;

    public void setClickDayListener(DayListener dayListener, ClickDayListener listener) {
        this.mClickDayListener = listener;
        this.mDayListener = dayListener;
    }

    @Override
    public ValidTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ValidTimeViewHolder ret = null;
        View v = LayoutInflater.from(context).inflate(R.layout.item_calendar_recycler_timeplan, parent, false);
        ret = new ValidTimeViewHolder(v, context);
        return ret;
    }

    @Override
    public void onBindViewHolder(ValidTimeViewHolder holder, int position) {
        PlanTimeEntity planTimeEntity = mYearMonthsData.get(position);
        holder.plan_time_txt_month.setText(planTimeEntity.getYear() + "年" + planTimeEntity.getMonth() + "月");
        //得到该月份的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, planTimeEntity.getYear());          //指定年份
        calendar.set(Calendar.MONTH, planTimeEntity.getMonth() - 1);        //指定月份 Java月份从0开始算
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<SelectDayEntity> days = new ArrayList<SelectDayEntity>();
        for (int i = 0; i < dayOfWeek - 1; i++) {
            days.add(new SelectDayEntity(0, planTimeEntity.getMonth(), planTimeEntity.getYear()));
        }
        calendar.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        calendar.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        for (int i = 1; i <= calendar.get(Calendar.DAY_OF_MONTH); i++) {
            SelectDayEntity selectDayEntity = new SelectDayEntity(i, planTimeEntity.getMonth(), planTimeEntity.getYear());
            days.add(selectDayEntity);
        }
        SelectValidTimeAdapter adapter = new SelectValidTimeAdapter(days, context);
        adapter.setClickDayListener(mDayListener, mClickDayListener);
        adapter.setAllowTime(1000);  //设置允许选择的最后一天和起始日期相差的天数
        holder.plan_time_recycler_content.setAdapter(adapter);
    }



    @Override
    public int getItemCount() {
        int ret = 0;
        if (mYearMonthsData != null) {
            ret = mYearMonthsData.size();
        }
        return ret;
    }
}
