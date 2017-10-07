package com.staszkox.stacktrack.domain.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextUtils
{
	public static boolean isEmpty(String text)
	{
		return text == null || "".equals(text);
	}
	
	public static boolean isNotEmpty(String text)
	{
		return !isEmpty(text);
	}
	
	public static String fallbackForEmpty(String text, String fallback)
	{
		return isNotEmpty(text) ? text : fallback;
	}
	
	public static String convertTimestampToDateString(Date date, String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		String convertedDate = df.format(date);
		return convertedDate;
	}
}
