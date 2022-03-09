package com.beauty.common;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.beauty.BeautyConstants;
import com.beauty.kakao.KakaoClient;

public final class DateUtil {
	private static TimeZone timeZone;

	static {
		try {
			timeZone = TimeZone.getTimeZone("GMT+09:00");
		} catch (Exception e) {
		}
	}

	public static Date getDate() {

		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

		return cal.getTime();
	}

	public static Date getDate(long offset) {

		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
		cal.setTime(new Date(cal.getTime().getTime() + (offset * 1000)));

		return cal.getTime();
	}

	public static Date getDate(Date date, long offset) {

		return new Date(date.getTime() + (offset * 1000));
	}

	public static String getDateString(String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.format(getDate());
	}

	public static String getDateString() {
		return getDateString("yyyy-MM-dd HH:mm:ss");
	}

	public static long getDateLong(String format) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return Long.parseLong(simpleFormat.format(getDate()));
	}

	public static long getDateLong() {
		return getDateLong("yyyyMMddHHmmss");
	}

	public static long getDateLongS() {
		return getDateLong("yyyyMMddHHmmssSSS");
	}

	public static Date getDate(int year, int month, int day, int hour,
			int minute, int second) {

		GregorianCalendar cal = new GregorianCalendar(timeZone, Locale.KOREAN);
		cal.set(year, month - 1, day, hour, minute, second);
		return cal.getTime();
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.format(date);
	}

	public static String getDateToString(String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		Calendar now = Calendar.getInstance(timeZone, Locale.KOREAN);
		return simpleFormat.format(now.getTime());
	}

	public static Date stringToDate(String dateString, String format)
			throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.parse(dateString);
	}

	public static long dateToLong(Date date, String format) {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return Long.parseLong(simpleFormat.format(date));
	}

	public static Date longToDate(long dateLong, String format)
			throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		simpleFormat.setTimeZone(timeZone);
		return simpleFormat.parse(Long.toString(dateLong));
	}

	public static String longToString(long dateLong, String format)
			throws ParseException {
		return dateToString(longToDate(dateLong, "yyyyMMddHHmmss"), format);
	}

	public static int getAfterDays(Date date1, Date date2) {

		return (int) ((date1.getTime() - date2.getTime()) / 86400000);
	}

	public static int getAfterSeconds(Date date1, Date date2) {

		return (int) ((date1.getTime() - date2.getTime()) / 1000);
	}

	public static int getAfterMilliSeconds(Date date1, Date date2) {

		return (int) (date1.getTime() - date2.getTime());
	}

	/**
	 * �ش� ���� ù ��° ��¥�� ���Ѵ�.
	 * @param year
	 * @param month
	 * @param format
	 * @return
	 */
	public static String getCurMonthFirstDate(String year, String month,
			String format) {

		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);

		int curYear = Integer.parseInt(year);
		int curMonth = Integer.parseInt(month);

		cal.set(curYear, curMonth - 1, 1);
		int curMinDay = cal.getActualMinimum(Calendar.DATE);

		Date curDate = DateUtil.getDate(curYear, curMonth, curMinDay, 0, 0, 0);

		return DateUtil.dateToString(curDate, format);
	}

	/**
	 * ���� ������ ���Ѵ�.
	 * @return
	 */
	public static String getDay()
	{
		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		String[] days = {"", "��", "��", "ȭ", "��", "��", "��", "��"};
		return days[curDay];
	}

	/**
	 * ���� ������ ���ڷ� ���Ѵ�.
	 * @return
	 */
	public static int getIntDay()
	{
		Calendar cal = Calendar.getInstance(timeZone, Locale.KOREAN);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static boolean isPastDay(String date) {
		boolean result = false;
		try {
			Date pdate = DateUtil.stringToDate(date, "yyyyMMddHHmmss");
			Date cdate = DateUtil.getDate();
			int days = DateUtil.getAfterDays(cdate, pdate);
			if (days > 3) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
		}
		return result;
	}



	public static void main(String[] args) throws Exception {
//		System.out.println(getAfterDays(1));
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
//		System.out.println(passwordEncoding.encode("appletest"));
		System.out.println(passwordEncoding.encode("12345"));
//		
		//KakaoClient client = new KakaoClient(BeautyConstants.KAKO_API_KEY);
		//client.getUserData("VbVSEQF6uxTwCxkiYNiv9_a_1GfP0TeY-yek4AopdeIAAAFa-s6KZg");
		
		 DecimalFormat df=new DecimalFormat("#.#");
		 double number = 3;
//		 df.format(number);
		 System.out.println(df.format(number));
		//System.out.println(client.getAuth("authorization_code", "http://localhost:8000", "I5kzaKmfgQH9L3516DVniYWIaYUd49iaHjMfADhi5HYSuY3n858qls85YfVCRLRU3MVmzAopdgcAAAFa-m7p9g"));
		
//		System.out.println(getBefor30Days());
		//		String coo = "37.4787416,126.8818627";
		//		String[] split = coo.split(",");
		//		System.out.println(split[0] + " | " + split[1]);
		//	  Date dt_month = DateUtil.stringToDate("1982-02-04 12:45:00", "yyyy-MM-dd HH:mm:ss");
		//	  Date dt_year = DateUtil.stringToDate("1982-06-06 09:36:00", "yyyy-MM-dd HH:mm:ss");
		//	  System.out.println(DateUtil.getAfterSeconds(dt_year,dt_month));
	}

	public static String getAfterYears(int year) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, year);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterYears(int year, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, year);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterMonths(int month) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, month);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterMonths(int month, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, month);
		return simpleFormat.format(now.getTime());
	}

	public static Date getBefor30Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		cal.get(cal.DATE);

		//		java.util.Date weekago = cal.getTime();
		//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
		//				Locale.getDefault());
		return cal.getTime();
		//		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		//		Calendar now = Calendar.getInstance();
		//		now.add(Calendar.DATE, day);
		//		return simpleFormat.format(now.getTime());
	}
	
	/**
	 * ���� ��¥�� ����, day�� ���� ��¥�� ����
	 * @param day
	 * @return
	 */
	public static String getAfterDays(int day) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, day);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterDays(int day, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, day);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterHours(int hour) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, hour);
		return simpleFormat.format(now.getTime());
	}

	public static String getAfterMinute(int minute) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minute);
		return simpleFormat.format(now.getTime());
	}

	public static String getyyyyMMddHHmmssSSS() {
		return getDateString("yyyyMMddHHmmssSSS");
	}

	public boolean isHoliday(Date date) 
	{
		boolean isHoliday = false;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			isHoliday = true;
		}

		// Now write logic to check the date for potential
		// matches among a list of public holidays stored
		// in an external location
		return isHoliday;
	}

	public static boolean isHoliday() 
	{
		boolean isHoliday = false;

		Calendar cal = Calendar.getInstance();

		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			isHoliday = true;
		}

		// Now write logic to check the date for potential
		// matches among a list of public holidays stored
		// in an external location
		return isHoliday;
	}

	/**
	 * Calendar���� YYYYMMDDH24MISSMMM ����
	 * 
	 * @param c
	 *            Calendar
	 * @param isMillisecond
	 *            Millisecond �߰� ����
	 * @return YYYYMMDDH24MISSMMM
	 */
	public static String toDTime(Calendar c, boolean isMillisecond) {

		StringBuffer sb = new StringBuffer(17);

		/** �� */
		if (c.get(Calendar.YEAR) < 10)
			sb.append('0');
		sb.append(c.get(Calendar.YEAR));

		/** �� */
		if (c.get(Calendar.MONTH) + 1 < 10)
			sb.append('0');
		sb.append(c.get(Calendar.MONTH) + 1);

		/** �� */
		if (c.get(Calendar.DAY_OF_MONTH) < 10)
			sb.append('0');
		sb.append(c.get(Calendar.DAY_OF_MONTH));

		/** �� */
		if (c.get(Calendar.HOUR_OF_DAY) < 10)
			sb.append('0');
		sb.append(c.get(Calendar.HOUR_OF_DAY));

		/** �� */
		if (c.get(Calendar.MINUTE) < 10)
			sb.append('0');
		sb.append(c.get(Calendar.MINUTE));

		/** �� */
		if (c.get(Calendar.SECOND) < 10)
			sb.append('0');
		sb.append(c.get(Calendar.SECOND));

		/** MILLISECOND */
		if (isMillisecond) {
			int mil = c.get(Calendar.MILLISECOND);
			if(mil == 0){
				sb.append("000");
			}else if(mil < 10){
				sb.append("00");
			}else if(mil < 100){
				sb.append("0");
			}

			sb.append(mil);
		}

		return sb.toString();
	}	

	public static Date getYesterday ( Date today )
	{
		if ( today == null ) 
			throw new IllegalStateException ( "today is null" );
		Date yesterday = new Date ( );
		yesterday.setTime ( today.getTime ( ) - ( (long) 1000 * 60 * 60 * 24 ) );

		return yesterday;
	}
	
	public static Date get7DayAgoDate() {
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280,  "KST"));
		cal.add(Calendar.DATE, -7);
		Date weekago = cal.getTime();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		return weekago;
	}
	
	public static Date getDayAgoDate(int day) {
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280,  "KST"));
		cal.add(Calendar.DATE, day);
		Date weekago = cal.getTime();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		return weekago;
	}
	
	public static String getBeforeDays(int day, String format) {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format, Locale.KOREA);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -day);
		return simpleFormat.format(now.getTime());
	}

}
