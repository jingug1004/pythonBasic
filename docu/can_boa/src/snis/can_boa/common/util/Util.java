package snis.can_boa.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.biz.activity.PosMiPlatformConstants;

public class Util {

    public static String sDateSeparator  = "-";
    public static String sDateFormat     = "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd";
    public static String sDateTimeFormat = "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd HH:mm:dd";

    public Util()
    {
    }
    
    /**************************************************************************
     *  Null값을 Default값으로 변경
     **************************************************************************/
    public static String nullToStr(String sString, String sDefault)
     {
         if ( sString == null || sString.equals("null") ) {
             return sDefault;
         }

         return sString;
     }

     public static String nullToStr(String sString)
     {
         return nullToStr(sString, "");
     }

     public static int nullToInt(String sString, int nDefault)
     {
         int nReturn = nDefault;

         try {
             nReturn = Integer.parseInt(sString.trim());
         } catch ( Exception e ) {
         }

         return nReturn;
     }

     public static int nullToInt(String sString)
     {
         return nullToInt(sString, 0);
     }


     /**************************************************************************
     *  문자열에서 숫자값만 return
     **************************************************************************/
     public static String getNumber(String sString)
     {
         return getNumber(sString, "");
     }

     public static String getNumber(String sString, String sAddString)
     {
         String sReturn = "";
         String sNumber = "0123456789" + sAddString;

         if ( sString == null ) { return sReturn; }

         for (int i = 0; i < sString.length(); i++ ) {
             if ( sNumber.indexOf(sString.charAt(i)) > -1 ) {
                 sReturn = sReturn + sString.charAt(i);
             }
         }
         return sReturn;
     }


     /**************************************************************************
     *  숫자에 세자리마다 쉼표를 찍는다.
     **************************************************************************/
     public static String getNumFormat(double dNumber)
     {
         NumberFormat  numFormat = NumberFormat.getCurrencyInstance();
         DecimalFormat decFormat = (DecimalFormat) numFormat;

         String pattern = "";

         if ( ( dNumber % 1 ) == 0 ) {
             pattern = "###,###,###,##0";
         } else {
             pattern = "###,###,###,##0.##";
         }

         decFormat.applyPattern(pattern);
         return decFormat.format(dNumber);
     }

     public static String getNumFormat(String sString)
     {
         try
         {
             double dNumber = Double.parseDouble(sString);
             return getNumFormat(dNumber);
         }
         catch (NumberFormatException ex)
         {
             return "0";
         }
     }

     public static String getNumFormat(long nNumber)
     {
         double dNumber = new Long(nNumber).doubleValue();
         return getNumFormat(dNumber);
     }


     /**************************************************************************
     *  한글
     **************************************************************************/
     public static String toKor(String sString)
     {
         String sReturn = "";
         if ( sString == null ) { return sReturn; }
         try {
             sReturn = new String(sString.getBytes("8859_1"), "KSC5601");
         } catch(Exception e) {
         }

         return sReturn;
     }


     /**************************************************************************
     *  영문
     **************************************************************************/
     public static String toEng(String sString)
     {
         String sReturn = "";
         if ( sString == null ) { return sReturn; }
         try {
             sReturn = new String(sString.getBytes("KSC5601"), "8859_1");
         } catch(Exception e) {
         }

         return sReturn;
     }


     /*****************************************************************************
     *  현재 날짜를 리턴한다.
     *****************************************************************************/
     public static String getCurrentDate(String sDateFormat) {

         java.util.Date   dateCurrent   = new java.util.Date();
         SimpleDateFormat simDateFormat = new SimpleDateFormat(sDateFormat);

         return simDateFormat.format(dateCurrent);
     }

     public static String getCurrentDate() {
         return getCurrentDate(sDateFormat);
     }

     public static String getCurrentDateTime() {
         return getCurrentDate(sDateTimeFormat);
     }


     /*****************************************************************************
     *  날짜형식
     *****************************************************************************/
     public static String getDateTime(String sDate) {
         String sReturn = "";

         if ( sDate == null) {
             return "";
         }

         sDate = getNumber(sDate);

         switch (sDate.length())
         {
             case 6 :
                 sReturn = sDate.substring( 0,  4) + sDateSeparator +
                           sDate.substring( 4    ) + sDateSeparator;
                 break;
             case 8 :
                 sReturn = sDate.substring( 0,  4) + sDateSeparator +
                           sDate.substring( 4,  6) + sDateSeparator +
                           sDate.substring( 6    );
                 break;
             case 12 :
                 sReturn = sDate.substring( 0,  4) + sDateSeparator +
                           sDate.substring( 4,  6) + sDateSeparator +
                           sDate.substring( 6,  8) + " " +
                           sDate.substring( 8, 10) + ":" +
                           sDate.substring(10    );
                 break;
             case 14 :
                 sReturn = sDate.substring( 0,  4) + sDateSeparator +
                           sDate.substring( 4,  6) + sDateSeparator +
                           sDate.substring( 6,  8) + " " +
                           sDate.substring( 8, 10) + ":" +
                           sDate.substring(10, 12) + ":" +
                           sDate.substring(12    );

                 break;
             default :
                 sReturn = sDate;
                 break;
         }

         return sReturn;
     }


     /*****************************************************************************
     *  날짜형식
     *****************************************************************************/
     public static String getDateFormat(String sDate) {
         sDate = getNumber(sDate);

         if ( sDate.length() >= 8 ) {
             sDate = sDate.substring(0, 8);
         }

         return getDateTime(getNumber(sDate));
     }

     public static String getDateTimeFormat(String sDateTime) {
         return getDateTime(sDateTime);
     }


     /**************************************************************************
     *  월의 마지막 날짜를 계산한다.
     **************************************************************************/
     public static String getLastDay(String sDate)
     {
         String[] lastday = { "31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};

         int nYear  = 0;
         int nMonth = 0;

         nYear  = Integer.parseInt(sDate.substring(0, 4));
         nMonth = Integer.parseInt(sDate.substring(4, 6));

         if((nYear%4 == 0) && (nYear%100 != 0) || (nYear%400 == 0)) {
             lastday[1] = "29";
         }

         return lastday[nMonth - 1];
     }

     /**************************************************************************
      *  Result의 Key를 생성한다.
      **************************************************************************/
    public static void addResultKey(PosContext ctx, String sResultKey)
    {
        // Original Result Key List 얻음
        List resultKeyList = (List) ctx.get(PosMiPlatformConstants.RESULT_KEY_LIST);
        resultKeyList.add(sResultKey);
         
        // 컨텍스트에 등록
        ctx.put(PosMiPlatformConstants.RESULT_KEY_LIST, resultKeyList);
    }

    public static void setSvcMsg(PosContext ctx, String sMessage)
    {
        ctx.put("ServiceMessage", sMessage);
    }

    public static void setSvcMsgCode(PosContext ctx, String sMessageCode)
    {
        ctx.put("ServiceMessageCode", sMessageCode);
    }

    public static void setSearchCount(PosContext ctx, int nCount)
    {
    	if ( nCount <= 0 ) return;
        ctx.put("SearchCount", new Integer(nCount));
    }

    public static void setSaveCount(PosContext ctx, int nCount)
    {
    	if ( nCount <= 0 ) return;
        ctx.put("SaveCount", new Integer(nCount));
    }

    public static void setDeleteCount(PosContext ctx, int nCount)
    {
    	if ( nCount <= 0 ) return;
        ctx.put("DeleteCount", new Integer(nCount));
    }

    /**************************************************************************
     *  Result의 Key를 생성한다.
     **************************************************************************/
   public static String trim(Object obj)
   {
	   //String sValue = (String) obj;
	   String sValue = String.valueOf(obj);
	   sValue = nullToStr(sValue);

	   return sValue.trim();
   }


  	/**
  	 * NULL인지 체크
  	 * 입력값이 NULL이거나 공백("")인 false를 반환하고, 아닌 경우 true를 반환한다.
  	 * @param value 입력값은 toString() 가능한 Object형이어야 한다.
  	 * @return boolean
  	 */
	public static boolean isNull(Object value) {
       	if(value == null) {
			return true;
		} else {
			if(value.toString().trim().equals("")) {
				return true;
			}
		}
		return false;
	}   
   
   /**
    * NULL 제거
    * 입력값이 NULL인 경우 ""를, 입력값이 NULL이 아닌 경우는 입력값을 그대로 리턴한다.
    * @param value 입력값은 toString() 가능한 Object형이어야 한다.
    * @return String
    */
   	public static String NVL(Object value) {
           	return NVL(value, "");
   	}
   /**
    * NULL 제거
    * 입력값이 NULL인 경우 str를, 입력값이 NULL이 아닌 경우는 입력값을 그대로 리턴한다.
    * @param value 입력값은 toString() 가능한 Object형이어야 한다.
    * @param str 입력값이 NULL인 경우 리턴 할 기본값
    * @return String
    */
   	public static String NVL(Object value, String str) {
   		if(isNull(value)) {
                   	return str;
   	        } else {
           	        return value.toString();
   	        }
   	}
   /**
    * NULL 제거
    * 입력값이 NULL인 경우 str를, 입력값이 NULL이 아닌 경우는 입력값을 그대로 리턴한다.
    * 입력값이 숫자형이라고 가정하고, 입력된 format에 맞게 리턴한다.
    * @param value 입력값은 toString() 가능한 Object형이어야 한다.
    * @param str 입력값이 NULL인 경우 리턴 할 기본값
    * @return String
    */
   	public static String NVL(Object value, String str, String format) {
   		NumberFormat formatter = new DecimalFormat(format);
   		if(isNull(value)) {
                   	return str;
   		} else if(NVL(value, 0) == 0) {
   			return str;
   	        } else {
   			return formatter.format(NVL(value, 0));
   	        }
   	}
   /**
    * NULL 제거
    * 입력값이 NULL인 경우 str를, 입력값이 NULL이 아닌 경우는 입력값을 숫자로 변환하여 리턴한다.
    * @param value 입력값은 toString() 가능한 Object형이어야 한다.
    * @param str 입력값이 NULL인 경우 리턴 할 기본값
    * @return Integer
    */
   	public static int NVL(Object value, int str) {
   		if(isNull(value)) {
           	        return str;
   	        } else {
           	        if(value.toString().equals("")) {
                   	        return str;
   	                } else {
           	                return Integer.parseInt(value.toString());
   	                }
           	}
   	}
   /**
    * NULL 제거
    * 입력값이 NULL인 경우 str를, 입력값이 NULL이 아닌 경우는 입력값을 숫자로 변환하여 리턴한다.
    * @param value 입력값은 toString() 가능한 Object형이어야 한다.
    * @param str 입력값이 NULL인 경우 리턴 할 기본값
    * @return Double 값
    */
   	public static double NVL(Object value, double str) {
   		if(isNull(value)) {
           	        return str;
   	        } else {
           	        if(value.toString().equals("")) {
                   	        return str;
   	                } else {
           	                return Double.parseDouble(value.toString());
   	                }
           	}
   	}
   	
   	/**
   	 * 현재 일자를 String 형으로 반환 ( ex: 20010101 )
   	 * @return String   String형으로 변환된  현재일자.
   	 */
   	public static String getCurrDate() {
   	  java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
   	  String str = date.toString();
   	  return str.substring(0,4) + str.substring(5,7) + str.substring(8,10);
   	}



   	/**
   	 * 현재 시간을 String 형으로 반환 ( ex: 122034 (시분초) )
   	 * @return String   String형으로 변환된  현재시간.
   	 */
   	public static String getCurrTime() {
   	  java.sql.Timestamp dDate = new java.sql.Timestamp(System.currentTimeMillis());
   	  String sTime = dDate.toString().substring(11,13) + dDate.toString().substring(14,16) +
   	                 dDate.toString().substring(17,19);
   	  return sTime;
   	}
   	
   	/**
   	 * PosContext 에서 String Key 반환
   	 * @param ctx		PosContext
   	 * @param sKey		key Value
   	 * @return			String
   	 */
   	public static String getCtxStr(PosContext ctx, String sKey) {
   		Object obj = ctx.get(sKey);
   		return obj == null ? "" : (String) obj;
   	}

	// 문자열을 바이트 단위로 체크해서 문자열길이를  return한다.(한글:2바이트)
	public static int lengthB(String instr){    	
		int strlen = 0;    					
		for(int j = 0; j < instr.length(); j++){
			char c = instr.charAt(j);
			if ( c  <  0xac00 || 0xd7a3 < c ) 
				strlen++;
			 else  
				strlen+=2;  //한글이다..			
		}
		return strlen;	
	}
}