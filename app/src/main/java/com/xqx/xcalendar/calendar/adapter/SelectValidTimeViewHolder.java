package com.xqx.xcalendar.calendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xqx.xcalendar.R;


/**
 * 每个月中的天数适配器ViewHolder
 */
public class SelectValidTimeViewHolder extends RecyclerView.ViewHolder {

    public TextView select_txt_day;      //日期文本
    public LinearLayout select_ly_day;       //父容器

    public SelectValidTimeViewHolder(View itemView) {
        super(itemView);
        select_ly_day = (LinearLayout) itemView.findViewById(R.id.select_ly_day);
        select_txt_day = (TextView) itemView.findViewById(R.id.select_txt_day);
    }
}
