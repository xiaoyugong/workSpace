package com.parkbobo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	private static DateUtils dateUtils;

	public static synchronized DateUtils getInstance() {
		if (dateUtils == null) {
			dateUtils = new DateUtils();
		}
		return dateUtils;
	}

	public String parseDate(Date d) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleDateFormat.format(d);
		
	}
	
	public String formatTime(Long time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (time != null) {
			return sdf.format(new Date(time));
		} else {
			return "";
		}
	}
	
	public String formatYear() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		return sf.format(new Date());
	}

	public String formatMonth() {
		SimpleDateFormat sf = new SimpleDateFormat("MM");
		return sf.format(new Date());
	}

	public String formatDate(String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(new Date());
	}

	public String formatWeekDay() {
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日，EEE", Locale.CHINA);
		return df.format(new Date());
	}
}