package com.xqx.xcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xqx.dialog.CommonDialog;
import com.xqx.xcalendar.calendar.CalendarView;
import com.xqx.xcalendar.calendar.listener.CalendarViewListener;

public class MainActivity extends AppCompatActivity {


    private Button btnStart;
    private TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }


    private void initView() {
        btnStart = (Button) findViewById(R.id.btnStart);
        txtTime = (TextView) findViewById(R.id.txtStart);

    }

    private void initEvent() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarView calendarView = new CalendarView(MainActivity.this);
                calendarView.setData(mStartDay, mEndDay,
                        "2015-01", "2020-12");
//                calendarView.setData("2018-06-20", "2018-06-25",
//                        "2017-10", "2019-12");
                CommonDialog.create(getSupportFragmentManager())
                        .setCustomView(calendarView)
                        .setViewListener(new CommonDialog.ViewListener() {
                            @Override
                            public void bindView(View v, final CommonDialog dialog) {
                                CalendarView view1 = (CalendarView) v;
                                view1.setCalendarViewListener(new CalendarViewListener() {
                                    @Override
                                    public void clickBack() {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void clickOk(String formatTime, String startDay, String endDay) {
                                        dialog.dismiss();
                                        Toast.makeText(MainActivity.this, formatTime, Toast.LENGTH_SHORT).show();
                                        txtTime.setText(formatTime);
                                        mStartDay = startDay;
                                        mEndDay = endDay;
                                    }
                                });
                            }
                        })
                        .setDimAmount(0.6f)
                        .show();
            }
        });
    }


    private String mStartDay = "";
    private String mEndDay = "";

}
