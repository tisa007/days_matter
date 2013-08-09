package com.tisa7.daysmatter;

import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateUtil {

	public static int getRemainDays(Date date) {
		if (date == null) {
			return 0;
		}
//        DateTime dateTime;
//        Days.daysBetween();
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 1);
		long diff = date.getTime() - c.getTimeInMillis();
		Log.d("today", c.getTime().toString());
		Log.d("that", date.toString());
		Log.d("diff", ""+diff);
		return (int) (Math.round(diff / 1000d / 60 / 60 / 24));
	}

}
