package com.project.websitemonitor.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public static String trimHttpFromUrl(String url) {
		return url.replaceFirst("^(http[s]?://)", "");
	}

	public static Timestamp getFormattedDate(long timeInMillis) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeString = dateFormat.format(timeInMillis);
		Date date = null;
		try {
			date = dateFormat.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(date.getTime());
	}
}
