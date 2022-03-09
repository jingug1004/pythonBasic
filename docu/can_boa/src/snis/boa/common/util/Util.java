package snis.boa.common.util;

import java.math.BigDecimal;
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
      public static String getTime(String sTime) {
          String sReturn = "";

          if ( sTime == null) {
              return "";
          }

          sTime = getNumber(sTime);

          switch (sTime.length())
          {
              case 4 :
                  sReturn = sTime.substring( 0,  2) + ":" 
                          + sTime.substring( 2    );
                  break;
              default :
                  sReturn = sTime;
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
	   String sValue = "";
	   if ( obj == null ) return "";
	   sValue = nullToStr(obj.toString());
	   
	   return sValue.trim();
   }

   
   public static String getFormat(String sFormat, String sValue) {
	   String sReturn = "";
		try {
			DecimalFormat decFormat  = new DecimalFormat(sFormat);
			decFormat.format(Double.parseDouble(sValue));
		
			sReturn = decFormat.format(Double.parseDouble(sValue));
		} catch ( Exception e ) {
		}
		return getStringLen(sReturn, sFormat.length());
   }
   
   public static String getFormat(String sFormat, BigDecimal dValue) {
	   String sReturn = "";
		try {
			DecimalFormat decFormat  = new DecimalFormat(sFormat);
			decFormat.format(dValue);
		
			sReturn = getStringLen(decFormat.format(dValue), sFormat.length());
		} catch ( Exception e ) {
		}
		return getStringLen(sReturn, sFormat.length());
  }

   public static String getStringLen(String sString, int nLen) {
	   if ( sString == null ) sString = "";
   	
		for ( int i = sString.length(); i < nLen; i++ ) {
			sString = " " + sString;
		}

		return sString;
   }

}
