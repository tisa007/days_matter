package com.tisa7.daysmatter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Task implements Serializable {

	private static final long serialVersionUID = -5069322474339614577L;

	public static final int REPEAT_NONE = 0;
	public static final int REPEAT_ONE_WEEK = 1;
	public static final int REPEAT_ONE_MONTH = 2;
	public static final int REPEAT_ONE_YEAR = 3;

	private String id;
	private String name;
	private String category;
	private int year;
	private int month;
	private int day;
	private long time;
	private boolean isLunar;
	private int repeatMode;
	private boolean isTop;
	private int sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSort() {
		return sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setDate(boolean isLunar, int year, int month, int day) {
		this.isLunar = isLunar;
		this.year = year;
		this.month = month;
		this.day = day;
		
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, 23, 59, 59);
		this.time = c.getTimeInMillis();
	}

    public Date getDate(){
        return new Date(this.time);
    }

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public boolean isLunar() {
		return isLunar;
	}

	public int getRepeatMode() {
		return repeatMode;
	}

	public void setRepeatMode(int repeatMode) {
		this.repeatMode = repeatMode;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean top) {
		this.isTop = top;
	}

}
