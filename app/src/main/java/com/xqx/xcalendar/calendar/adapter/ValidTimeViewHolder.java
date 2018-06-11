package com.xqx.xcalendar.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xqx.xcalendar.R;


/**
 * 年月适配器中的ViewHolder
 */
public class ValidTimeViewHolder extends RecyclerView.ViewHolder{

    public TextView plan_time_txt_month;
    public RecyclerView plan_time_recycler_content ;
    public Context context;

    public ValidTimeViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        plan_time_recycler_content = (RecyclerView) itemView.findViewById(R.id.plan_time_recycler_content);
        plan_time_txt_month = (TextView) itemView.findViewById(R.id.plan_time_txt_month);

        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(context,
                        7,  // 每行显示item项数目
                        GridLayoutManager.VERTICAL, //水平排列
                        false
                );

        plan_time_recycler_content.setLayoutManager(layoutManager);
    }
}
