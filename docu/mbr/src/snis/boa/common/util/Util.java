package snis.boa.common.util;

import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;



import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.manager.PosQueryDefinition;
import com.posdata.glue.dao.manager.PosQueryManager;
import com.posdata.glue.dao.manager.PosQueryManagerImpl;
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
	
	/**<p>
	 * 설명 : 메신져 전송
		// 업무별 대상자를 조회하는 쿼리 실행.
		// 받는 사람 목록 획득.
		// 보내는 내용 생성.
		// 메신져 전송.
	 * Use :
		con.commit(); // 꼭 내부적인 commit을 하고 호출해야 트랜잭션에 문제가 안생긴다.
		Util.sendMessenger(ctx, 3, sCtrctNo, 1, 0); // 메신져 호출
	 * </p>
	 * @param ctx  : HttpConnection 을 사용하기 위해 필요하다.
	 * @return
	 */
	public static void sendMessenger(String sSndr, String sRcvr, String sMesg, String sUrl) {
		/*
		String sSndr = (String)ctx.get("SEND_ID");
		String sRcvr = (String)ctx.get("RECV_ID");
		String sMesg = (String)ctx.get("MESG");
		String sUrl  = (String)ctx.get("URL");
		*/
		System.out.println("Util.sendMessenger:");    	
		
		try {
			System.out.println("Util.sendMessenger:sRcvr="+sRcvr+",sMesg="+sMesg);    	
			// 받을 사람이 있는 경우에만 전송한다.
			if (sRcvr.length() > 0 && sMesg.length() > 0) {
				try {
					Util.sendSosfoMsg( sRcvr, sSndr, sMesg, sUrl);
				} catch(Exception e) {
					System.err.println("SendSosfoMsg:"+ e.getMessage());
				}
			}

		}
		catch(Exception e) {
			System.err.println("sendMessenger(Exception)::"+e.getMessage());
		}

		//System.out.println("Util::sendMessenger(end) " );
	}

	/**<p>
	 * 설명 : SOSFO 메신져 호출.
	 *
		위의 소스처럼 RCV_USER에 수신자 사번을 ","로 분리해서 적어줘야 합니다.
		즉, 01047  -> 01047,01047
		원래는 여러 명에게 보낼때 컴머로 구분하는데, 이경우에는 컴머로 두번 적어줘야 한번 날라 가네요.

	 * </p>
	 * @param ctx  : HttpConnection 을 사용하기 위해 필요하다.
	 * @param aRcvEmpId  : 받는사람 사번 배열
	 * @param sSendEmpId : 보내는사람 사번
	 * @param sMsg : 보낼 내용
	 * @return
	 * @throws Exception 
	 */
	private static String sendSosfoMsg( String aRcvEmpId, String sSendEmpId, String sMsg, String sUrl) throws Exception {
		System.out.println("Util.sendSosfoMsg:");
		System.out.flush();
		int nRtn = 0;
	    String SERVER_URL = "http://140.101.1.120/messenger/ConNotify.asp";
	    String SYS_NAME   = "경주사업관리"; // default
	    String MSG_CODE   = "NOTICE";    // default

	    String MSG_ID   = "1"; // default
	    String SND_USER = sSendEmpId; // default
	    String RCV_USER = aRcvEmpId;
	    String DOC_NAME = URLEncoder.encode(sMsg,"EUC-KR"); // default
	    String DOC_URL  = URLEncoder.encode(sUrl,"EUC-KR");
	    String DOC_DESC = ""; // default
	    String ALARM_TYPE = ""; // default
	    //String MAKE_DATE = CotUtil.getDateTime("yyyyMMddHHmmssZZZ");


	    // 링크 주소.
//		    String LINK_URL = CotUtil.getSnisConfig("snis_com_0001"); //http://140.101.1.11:8890/snis/
//		    String sLink = LINK_URL+"install/NoLoginSnis.jsp?system=COT&SSOTOK=&lt;&#37;usertok&#37;&gt;";
//		    DOC_URL  = URLEncoder.encode(sLink,"EUC-KR");

		/*
		  1) 시스템 명 : 연동 시스템 명(예:메일,결재,공지 등등..)은 메시지 수신 시 수신메시지 알람에 표기됩니다.
		  2) 구분 : 메신저 서버의 알람 연동 버튼에 지정한 코드 값(메신저 서버 환경설정에서 지정)
		  3) 발신자 ID : 사번 = 메신저 ID
		  4) 수신자 ID : 수신자가 1명 이상인 경우 id(사번)별 ‘,’(Comma)로 구분하여 작성하고,
		                               이 Column에 데이터가 없는 경우  메신저 온라인 사용자들에게 모두 발송 합니다.
		  5) 제목 : 해당 메시지의 제목
		  6) URL : 메신저 클라이언트에서 알람을 받았을 때 One-Click으로 해당 알람 내용을 확인할 수 있는 URL 정보  (SSO가 적용된 경우 해당문장 포함)
		  7) 내용 : 텍스트 타입의 내용
		  8) 알람방법 : 메신저 클라이언트에는 수신된 메시지에 대한 알람여부를 사용자가 설정할 수 있도록 되어 있으나,
		                            아래 자료의 조건에 의해 강제할 수 있다.
		     ‘F’(바탕화면 강제고정) : 송신한 알람은 수신자의 바탕화면에 고정된 상태로 표시되며, 사용자가 해당 메시지를 확인 하거나 [X]
		                                                        버튼을 이용하여 닫지 않는 이상 자동으로 닫히지 않는다.
		     ‘C’(강제알람): 송신한 알람은 수신자의 환경설정과 관계없이 무조건 알람 처리 한다.
		     ‘’(자동)    : 송신한 알람은 수신자의 환경설정에 따라 알람 처리 한다.
		  9) 작성일시 : 메신저 서버에서 수신자료의 KEY로 사용 (‘YYYYMMDDHHMMSSZZZ’)
		*/
	    String GOURL = "";	//SERVER_URL;
		    GOURL += "MSG_ID="+MSG_ID;
		    GOURL += "&SYS_NAME="+SYS_NAME;
		    GOURL += "&MSG_CODE="+MSG_CODE;
		    GOURL += "&SND_USER="+SND_USER;
		    GOURL += "&RCV_USER="+RCV_USER;
		    GOURL += "&DOC_NAME="+DOC_NAME;
		    GOURL += "&DOC_URL="+DOC_URL;
		    GOURL += "&DOC_DESC="+DOC_DESC;
		    GOURL += "&ALARM_TYPE="+ALARM_TYPE;
//System.out.println("GOURL : " + GOURL);
//System.out.println("RCV_USER : " + RCV_USER);

		// 수신자가 있어야만 발송함. 없으면 전체발송인데 여기서 막았다.
		if (RCV_USER.length() > 0) {
			//URL targetURL = new URL(SERVER_URL+"?"+GOURL);
			URL targetURL = new URL(SERVER_URL);
			HttpURLConnection httpCon = (HttpURLConnection) targetURL.openConnection();
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestProperty("Cache-Control", "no-cache");
			
			httpCon.setRequestProperty("Content-Length", "" + Integer.toString(GOURL.getBytes().length));
			httpCon.setUseCaches (false);
			
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true); // post방식:true
			httpCon.setDoInput(true);
			//httpCon.connect();
				
			DataOutputStream httpOut = new DataOutputStream(httpCon.getOutputStream());
			httpOut.write(GOURL.getBytes());
			httpOut.flush();
			httpOut.close();

			if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
				nRtn = aRcvEmpId.length();
			} else {
				nRtn = httpCon.getResponseCode();
			}

			/* //응답
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "EUC-KR"));
			String line = null;
			System.out.println("httpCon respone --->");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("<--- httpCon respone");
			*/

			if (httpCon != null) httpCon.disconnect();
		}

		return String.valueOf(nRtn);
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
 
}
