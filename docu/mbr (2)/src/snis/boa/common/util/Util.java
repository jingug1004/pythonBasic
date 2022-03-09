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
     *  Null���� Default������ ����
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
     *  ���ڿ����� ���ڰ��� return
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
     *  ���ڿ� ���ڸ����� ��ǥ�� ��´�.
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
     *  �ѱ�
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
     *  ����
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
     *  ���� ��¥�� �����Ѵ�.
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
     *  ��¥����
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
      *  ��¥����
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
     *  ��¥����
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
     *  ���� ������ ��¥�� ����Ѵ�.
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
      *  Result�� Key�� �����Ѵ�.
      **************************************************************************/
    public static void addResultKey(PosContext ctx, String sResultKey)
    {
        // Original Result Key List ����
        List resultKeyList = (List) ctx.get(PosMiPlatformConstants.RESULT_KEY_LIST);
        resultKeyList.add(sResultKey);
         
        // ���ؽ�Ʈ�� ���
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
     *  Result�� Key�� �����Ѵ�.
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

	// ���ڿ��� ����Ʈ ������ üũ�ؼ� ���ڿ����̸�  return�Ѵ�.(�ѱ�:2����Ʈ)
	public static int lengthB(String instr){    	
		int strlen = 0;    					
		for(int j = 0; j < instr.length(); j++){
			char c = instr.charAt(j);
			if ( c  <  0xac00 || 0xd7a3 < c ) 
				strlen++;
			 else  
				strlen+=2;  //�ѱ��̴�..			
		}
		return strlen;	
	}
	
	/**<p>
	 * ���� : �޽��� ����
		// ������ ����ڸ� ��ȸ�ϴ� ���� ����.
		// �޴� ��� ��� ȹ��.
		// ������ ���� ����.
		// �޽��� ����.
	 * Use :
		con.commit(); // �� �������� commit�� �ϰ� ȣ���ؾ� Ʈ����ǿ� ������ �Ȼ����.
		Util.sendMessenger(ctx, 3, sCtrctNo, 1, 0); // �޽��� ȣ��
	 * </p>
	 * @param ctx  : HttpConnection �� ����ϱ� ���� �ʿ��ϴ�.
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
			// ���� ����� �ִ� ��쿡�� �����Ѵ�.
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
	 * ���� : SOSFO �޽��� ȣ��.
	 *
		���� �ҽ�ó�� RCV_USER�� ������ ����� ","�� �и��ؼ� ������� �մϴ�.
		��, 01047  -> 01047,01047
		������ ���� ���� ������ �ĸӷ� �����ϴµ�, �̰�쿡�� �ĸӷ� �ι� ������� �ѹ� ���� ���׿�.

	 * </p>
	 * @param ctx  : HttpConnection �� ����ϱ� ���� �ʿ��ϴ�.
	 * @param aRcvEmpId  : �޴»�� ��� �迭
	 * @param sSendEmpId : �����»�� ���
	 * @param sMsg : ���� ����
	 * @return
	 * @throws Exception 
	 */
	private static String sendSosfoMsg( String aRcvEmpId, String sSendEmpId, String sMsg, String sUrl) throws Exception {
		System.out.println("Util.sendSosfoMsg:");
		System.out.flush();
		int nRtn = 0;
	    String SERVER_URL = "http://140.101.1.120/messenger/ConNotify.asp";
	    String SYS_NAME   = "���ֻ������"; // default
	    String MSG_CODE   = "NOTICE";    // default

	    String MSG_ID   = "1"; // default
	    String SND_USER = sSendEmpId; // default
	    String RCV_USER = aRcvEmpId;
	    String DOC_NAME = URLEncoder.encode(sMsg,"EUC-KR"); // default
	    String DOC_URL  = URLEncoder.encode(sUrl,"EUC-KR");
	    String DOC_DESC = ""; // default
	    String ALARM_TYPE = ""; // default
	    //String MAKE_DATE = CotUtil.getDateTime("yyyyMMddHHmmssZZZ");


	    // ��ũ �ּ�.
//		    String LINK_URL = CotUtil.getSnisConfig("snis_com_0001"); //http://140.101.1.11:8890/snis/
//		    String sLink = LINK_URL+"install/NoLoginSnis.jsp?system=COT&SSOTOK=&lt;&#37;usertok&#37;&gt;";
//		    DOC_URL  = URLEncoder.encode(sLink,"EUC-KR");

		/*
		  1) �ý��� �� : ���� �ý��� ��(��:����,����,���� ���..)�� �޽��� ���� �� ���Ÿ޽��� �˶��� ǥ��˴ϴ�.
		  2) ���� : �޽��� ������ �˶� ���� ��ư�� ������ �ڵ� ��(�޽��� ���� ȯ�漳������ ����)
		  3) �߽��� ID : ��� = �޽��� ID
		  4) ������ ID : �����ڰ� 1�� �̻��� ��� id(���)�� ��,��(Comma)�� �����Ͽ� �ۼ��ϰ�,
		                               �� Column�� �����Ͱ� ���� ���  �޽��� �¶��� ����ڵ鿡�� ��� �߼� �մϴ�.
		  5) ���� : �ش� �޽����� ����
		  6) URL : �޽��� Ŭ���̾�Ʈ���� �˶��� �޾��� �� One-Click���� �ش� �˶� ������ Ȯ���� �� �ִ� URL ����  (SSO�� ����� ��� �ش繮�� ����)
		  7) ���� : �ؽ�Ʈ Ÿ���� ����
		  8) �˶���� : �޽��� Ŭ���̾�Ʈ���� ���ŵ� �޽����� ���� �˶����θ� ����ڰ� ������ �� �ֵ��� �Ǿ� ������,
		                            �Ʒ� �ڷ��� ���ǿ� ���� ������ �� �ִ�.
		     ��F��(����ȭ�� ��������) : �۽��� �˶��� �������� ����ȭ�鿡 ������ ���·� ǥ�õǸ�, ����ڰ� �ش� �޽����� Ȯ�� �ϰų� [X]
		                                                        ��ư�� �̿��Ͽ� ���� �ʴ� �̻� �ڵ����� ������ �ʴ´�.
		     ��C��(�����˶�): �۽��� �˶��� �������� ȯ�漳���� ������� ������ �˶� ó�� �Ѵ�.
		     ����(�ڵ�)    : �۽��� �˶��� �������� ȯ�漳���� ���� �˶� ó�� �Ѵ�.
		  9) �ۼ��Ͻ� : �޽��� �������� �����ڷ��� KEY�� ��� (��YYYYMMDDHHMMSSZZZ��)
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

		// �����ڰ� �־�߸� �߼���. ������ ��ü�߼��ε� ���⼭ ���Ҵ�.
		if (RCV_USER.length() > 0) {
			//URL targetURL = new URL(SERVER_URL+"?"+GOURL);
			URL targetURL = new URL(SERVER_URL);
			HttpURLConnection httpCon = (HttpURLConnection) targetURL.openConnection();
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestProperty("Cache-Control", "no-cache");
			
			httpCon.setRequestProperty("Content-Length", "" + Integer.toString(GOURL.getBytes().length));
			httpCon.setUseCaches (false);
			
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true); // post���:true
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

			/* //����
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
  	 * NULL���� üũ
  	 * �Է°��� NULL�̰ų� ����("")�� false�� ��ȯ�ϰ�, �ƴ� ��� true�� ��ȯ�Ѵ�.
  	 * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
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
    * NULL ����
    * �Է°��� NULL�� ��� ""��, �Է°��� NULL�� �ƴ� ���� �Է°��� �״�� �����Ѵ�.
    * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
    * @return String
    */
   	public static String NVL(Object value) {
           	return NVL(value, "");
   	}
   /**
    * NULL ����
    * �Է°��� NULL�� ��� str��, �Է°��� NULL�� �ƴ� ���� �Է°��� �״�� �����Ѵ�.
    * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
    * @param str �Է°��� NULL�� ��� ���� �� �⺻��
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
    * NULL ����
    * �Է°��� NULL�� ��� str��, �Է°��� NULL�� �ƴ� ���� �Է°��� �״�� �����Ѵ�.
    * �Է°��� �������̶�� �����ϰ�, �Էµ� format�� �°� �����Ѵ�.
    * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
    * @param str �Է°��� NULL�� ��� ���� �� �⺻��
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
    * NULL ����
    * �Է°��� NULL�� ��� str��, �Է°��� NULL�� �ƴ� ���� �Է°��� ���ڷ� ��ȯ�Ͽ� �����Ѵ�.
    * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
    * @param str �Է°��� NULL�� ��� ���� �� �⺻��
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
    * NULL ����
    * �Է°��� NULL�� ��� str��, �Է°��� NULL�� �ƴ� ���� �Է°��� ���ڷ� ��ȯ�Ͽ� �����Ѵ�.
    * @param value �Է°��� toString() ������ Object���̾�� �Ѵ�.
    * @param str �Է°��� NULL�� ��� ���� �� �⺻��
    * @return Double ��
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
   	 * ���� ���ڸ� String ������ ��ȯ ( ex: 20010101 )
   	 * @return String   String������ ��ȯ��  ��������.
   	 */
   	public static String getCurrDate() {
   	  java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
   	  String str = date.toString();
   	  return str.substring(0,4) + str.substring(5,7) + str.substring(8,10);
   	}



   	/**
   	 * ���� �ð��� String ������ ��ȯ ( ex: 122034 (�ú���) )
   	 * @return String   String������ ��ȯ��  ����ð�.
   	 */
   	public static String getCurrTime() {
   	  java.sql.Timestamp dDate = new java.sql.Timestamp(System.currentTimeMillis());
   	  String sTime = dDate.toString().substring(11,13) + dDate.toString().substring(14,16) +
   	                 dDate.toString().substring(17,19);
   	  return sTime;
   	}
 
}
