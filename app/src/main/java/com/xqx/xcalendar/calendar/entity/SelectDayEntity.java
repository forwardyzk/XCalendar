package com.xqx.xcalendar.calendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 选择日期 每一个单个天数的
 */
public class SelectDayEntity implements Parcelable {
    private int day;           //日期
    private int month;           //属于的月份
    private int year;           //属于的年份


    public SelectDayEntity(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public SelectDayEntity() {
    }

    protected SelectDayEntity(Parcel in) {
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static final Creator<SelectDayEntity> CREATOR = new Creator<SelectDayEntity>() {
        @Override
        public SelectDayEntity createFromParcel(Parcel in) {
            return new SelectDayEntity(in);
        }

        @Override
        public SelectDayEntity[] newArray(int size) {
            return new SelectDayEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
    }
}
