package com.hanaph.gw.co.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * <pre>
 * Class Name : WebUtil.java
 * 설명 : Utility Class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17. JaeKap Kim		생성          
 * </pre>
 * 
 * @version : 
 * @author slawin(@irush.co.kr)
 * @since   : 2014. 10. 20.
 */
public class WebUtil {
	
	/**
	 * <pre>
	 * 1. 개요     : 게시판 목록에서 최신글 New 이미지 노출여부를 리턴한다.(등록일 3일 기준) 
	 * 2. 처리내용 : 
	 * @param newsDate 
	 * @return boolean
	 * </pre>
	 * @Method Name : getNewImage
	 * @param newsDate
	 * @return
	 */		
	public static boolean getNewImage(String newsDate){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -3);
		Date date = cal.getTime();
		
		String strCurrentDate = simpleDateFormat.format(date);
		String strNewsDate = StringUtil.nvl2(StringUtil.translate(newsDate, "-", ""), "0");
		int intCurrentDate = Integer.parseInt(strCurrentDate);
		int intNewsDate = Integer.parseInt(strNewsDate);
		
		if(intNewsDate > intCurrentDate){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     :  오늘 날짜부터 파라메터의 날짜전까지의 데이터
	 * 2. 처리내용 : 월기준 계산
	 * </pre>
	 * @Method Name : dateCal
	 * @param paramDt
	 * @return 이전날짜,금일날짜
	 */
	public static String[] dateCal(int paramDt){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();

		String today= dateFormat.format(currentDate.getTime()); 
		currentDate.add(Calendar.MONTH, paramDt);
	    String preday= dateFormat.format(currentDate.getTime());

	    String dateValue[] = {preday,today};
	    
		return dateValue;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 오늘 날짜부터 파라메터의 날짜전까지의 데이터
	 * 2. 처리내용 : 날짜기준 계산
	 * </pre>
	 * @Method Name : dateCal1
	 * @param paramDt
	 * @return 이전날짜,다음날짜
	 */
	public static String[] dateCal1(int paramDt){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();

		String today= dateFormat.format(currentDate.getTime()); 
		currentDate.add(Calendar.DATE, paramDt);
	    String preday= dateFormat.format(currentDate.getTime());

	    String dateValue[] = {preday,today};
	    
		return dateValue;	
	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 날짜 차이를 계산
	 * 2. 처리내용 : 날짜 차이를 계산
	 * </pre>
	 * @Method Name : diffOfDate
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static long diffOfDate(String begin, String end) throws Exception{
		  
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	 
		
		Date beginDate = formatter.parse(begin);
		Date endDate = formatter.parse(end);
		
		long diff = endDate.getTime() - beginDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		return diffDays;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 올해 년도의 01월 01일 전달
	 * 2. 처리내용 : 올해 년도의 01월 01일 전달
	 * </pre>
	 * @Method Name : NewYearDate
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static String[] NewYearDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		
		Calendar currentDate = Calendar.getInstance();
		String today= dateFormat.format(currentDate.getTime());
				
		/*01월 01일로 설정한 값을 초기 값으로 전달한다.*/
		currentDate.set(Calendar.MONTH, 0);
		currentDate.set(Calendar.DATE, 1);						
		
		String newYear = dateFormat.format(currentDate.getTime());		
		
		String dateValue[] = {newYear,today};
		
		return dateValue;
	}
	
}
