package com.xqx.xcalendar.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xqx.xcalendar.R;
import com.xqx.xcalendar.calendar.adapter.ValidTimeAdapter;
import com.xqx.xcalendar.calendar.entity.PlanTimeEntity;
import com.xqx.xcalendar.calendar.entity.SelectDayEntity;
import com.xqx.xcalendar.calendar.listener.CalendarViewListener;
import com.xqx.xcalendar.calendar.listener.ClickDayListener;
import com.xqx.xcalendar.calendar.listener.DayListener;

import java.util.ArrayList;

/**
 * 日历选择范围--View
 */
public class CalendarView extends LinearLayout {

    private Context mContext;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View.inflate(context, R.layout.view_calendar, this);
        initView();
    }


    private ImageButton calendar_back;
    private TextView calendar_show_days;
    private Button calendar_ok;
    private TextView calendar_start_day;          //开始时间
    private TextView calendar_end_day;           //结束时间

    private RecyclerView calendar_recycler;
    private ValidTimeAdapter adapter;
    private ArrayList<PlanTimeEntity> mYearMonthDatas;

    private void initView() {
        calendar_back = (ImageButton) findViewById(R.id.calendar_back);
        calendar_show_days = (TextView) findViewById(R.id.calendar_show_days);
        calendar_ok = (Button) findViewById(R.id.calendar_ok);
        calendar_start_day = (TextView) findViewById(R.id.calendar_start_day);
        calendar_end_day = (TextView) findViewById(R.id.calendar_end_day);
        calendar_recycler = (RecyclerView) findViewById(R.id.calendar_recycler);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mContext,   // 上下文
                        LinearLayout.VERTICAL,  //垂直布局,
                        false);

        calendar_recycler.setLayoutManager(layoutManager);

        calendar_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mlistener) {
                    mlistener.clickBack();
                }
            }
        });
        calendar_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (justDayEmpty(mStartDay)) {
                    Toast.makeText(mContext, "请选择开始时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (justDayEmpty(mEndDay)) {
                    Toast.makeText(mContext, "请选择结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (null != mlistener) {
                    String mStartDayStr = entityToString(mStartDay);
                    String mEndDayStr = entityToString(mEndDay);
                    mlistener.clickOk(mStartDayStr + "~" + mEndDayStr, mStartDayStr, mEndDayStr);
                }
            }
        });
    }


    public static SelectDayEntity mStartDay;
    public static SelectDayEntity mEndDay;

    /**
     * @param currentStartTime 当前选中的开始时间2015-01-01
     * @param currentEndTime   当前选中的结束时间2018-01-01
     * @param rangStarTime     日期展示的开始时间 2018-01
     * @param rangEndTime      日期展示的结束的时间 2018-09
     */
    public void setData(String currentStartTime, String currentEndTime, String rangStarTime, String rangEndTime) {
        int[] currentStartArray = stringToArrayYearMonthDay(currentStartTime);
        int[] currentEndArray = stringToArrayYearMonthDay(currentEndTime);
        int[] rangStarArray = stringToArrayYearMonth(rangStarTime);
        int[] rangEndArray = stringToArrayYearMonth(rangEndTime);
        setData(currentStartArray[0], currentStartArray[1], currentStartArray[2],
                currentEndArray[0], currentEndArray[1], currentEndArray[2],
                rangStarArray[0], rangStarArray[1],
                rangEndArray[0], rangEndArray[1]);
    }

    /**
     * @param currentStartYear  当前的选中的开始年
     * @param currentStartMonth 当前的选中的开始月
     * @param currentStartDay   当前选中的开始日
     * @param currentEndYear    当前的选中的结束年
     * @param currentEndMonth   当前的选中的结束月
     * @param currentEndDay     当前的选中的结束日
     * @param startRangeYear    设置的范围,开始的年
     * @param startRangeMonth   设置的范围,开始的月
     * @param endRangeYear      设置的范围,结束的年
     * @param endRangeMonth     设置的范围,开始的月
     */
    public void setData(int currentStartYear, int currentStartMonth, int currentStartDay,
                        int currentEndYear, int currentEndMonth, int currentEndDay,
                        int startRangeYear, int startRangeMonth, int endRangeYear, int endRangeMonth) {
        mStartDay = new SelectDayEntity(currentStartDay, currentStartMonth, currentStartYear);
        mEndDay = new SelectDayEntity(currentEndDay, currentEndMonth, currentEndYear);
        setShowStatAndEndDay(mStartDay, mEndDay);
        setYearMonthRange(startRangeYear, startRangeMonth, endRangeYear, endRangeMonth);
        adapter = new ValidTimeAdapter(mYearMonthDatas, mContext);
        //设置开始和结束时间再设置适配器之前
        adapter.setClickDayListener(dayListener, new ClickDayListener() {
            @Override
            public void clickDay(SelectDayEntity startDay, SelectDayEntity endDay) {
                adapter.notifyDataSetChanged();
                mStartDay = startDay;
                mEndDay = endDay;
                setShowStatAndEndDay(startDay, endDay);
            }
        });
        calendar_recycler.setAdapter(adapter);
    }


    /**
     * 设置范围
     *
     * @param startYear  开始的年
     * @param startMonth 开始的月份,从1开始
     * @param endYear    结束的年
     * @param endMonth   结束的月份,从1开始
     */
    private void setYearMonthRange(int startYear, int startMonth, int endYear, int endMonth) {
        mYearMonthDatas = new ArrayList<>();
        //2000.02~2018.06设置范围
        //第一步:2000.02~2000.12
        //第二步:2001.01~2017.12
        //第三步:2018.01~2018.06
        if (startMonth < 1) {
            startMonth = 1;
        } else if (startMonth > 12) {
            startMonth = 12;
        }
        if (endMonth < 1) {
            endMonth = 1;
        } else if (endMonth > 12) {
            endMonth = 12;
        }

        for (int year = startYear; year <= endYear; year++) {
            int tempStarMonth = 1;
            int tempEndMonth = 1;
            if (year == startYear) {
                tempStarMonth = startMonth;
                tempEndMonth = 12;
            } else if (year < endYear) {
                tempStarMonth = 1;
                tempEndMonth = 12;
            } else if (year == endYear) {
                tempStarMonth = 1;
                tempEndMonth = endMonth;
            }

            for (int month = tempStarMonth; month <= tempEndMonth; month++) {
                mYearMonthDatas.add(new PlanTimeEntity(year, month));
            }

        }
    }


    private DayListener dayListener = new DayListener() {
        @Override
        public SelectDayEntity getStartDay() {
            return mStartDay;
        }

        @Override
        public SelectDayEntity getEndDay() {
            return mEndDay;
        }
    };

    private void setShowStatAndEndDay(SelectDayEntity startDay, SelectDayEntity endDay) {
        if (justDayEmpty(endDay)) {
            calendar_end_day.setText("结束" + "\n" + "时间");
        } else {
            String s = entityToString(endDay);
            calendar_end_day.setText(s);
        }
        if (justDayEmpty(startDay)) {
            calendar_start_day.setText("开始" + "\n" + "时间");
        } else {
            String s = entityToString(startDay);
            calendar_start_day.setText(s);
        }


        if (!justDayEmpty(startDay) && !justDayEmpty(endDay)) {
            String startDayStr = entityToString(startDay);
            String endDayStr = entityToString(endDay);

            String twoDay = CalendarUtil.getTwoDay(endDayStr, startDayStr);

            int daysCount = Integer.parseInt(twoDay);
            calendar_show_days.setText("已选择" + (daysCount + 1) + "天");
        } else {
            calendar_show_days.setText("已选择0天");
        }


    }

    private CalendarViewListener mlistener;

    public void setCalendarViewListener(CalendarViewListener listener) {
        this.mlistener = listener;
    }

    /**
     * 判断时间是否为空
     *
     * @param entity
     * @return
     */
    private boolean justDayEmpty(SelectDayEntity entity) {
        if (null == entity || entity.getYear() == -1 || entity.getMonth() == -1 || entity.getDay() == -1) {
            return true;
        }
        return false;
    }

    /**
     * bean类转化为字符串
     *
     * @param dayEntity
     * @return
     */
    private String entityToString(SelectDayEntity dayEntity) {
        return dayEntity.getYear() + "-" + dayEntity.getMonth() + "-" + dayEntity.getDay();
    }

    /**
     * 字符串转化为年月日数组[年,月,日]
     *
     * @param time
     * @return
     */
    private int[] stringToArrayYearMonthDay(String time) {
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
    private int[] stringToArrayYearMonth(String time) {
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
    private SelectDayEntity stringToEntity(String time) {
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

}
