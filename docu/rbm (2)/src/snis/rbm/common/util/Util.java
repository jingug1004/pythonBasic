package snis.rbm.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.manager.PosQueryDefinition;
import com.posdata.glue.dao.manager.PosQueryManager;
import com.posdata.glue.dao.manager.PosQueryManagerImpl;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.biz.activity.PosMiPlatformConstants;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class Util {

    public static String sDateSeparator  = "-";
    public static String sDateFormat     = "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd";
    public static String sDateTimeFormat = "yyyy" + sDateSeparator + "MM" + sDateSeparator + "dd HH:mm:dd";
//    public static String sReturnParam = "";
    public static String MEET_CD = "001";
    public static String BIZ_EXECPTION = "bizException";
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
    //@SuppressWarnings("unchecked")
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
	   String sValue = (String) obj;
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
   	 * QueryKey로 SQL Statement 반환
     * @param ctx PosContext
     * @param queryKey queryKey Value
   	 * @return String   String형으로 SQL Statement.
   	 */   	
	//@SuppressWarnings("static-access")
	public static String getQuery(PosContext ctx, String queryKey) {
    	PosQueryManager queryManager;
    	    	
    	//"queryManager" -> applicationContext.xml 에 정의된 bean Id
    	queryManager = (PosQueryManagerImpl)ctx.getBeanFactory().getBeanObject("queryManager");
    	
    	/*
    	if (queryManager == null) 
    		throw new Exception("QueryKey Not Found!");
    	*/
    	PosQueryDefinition pqd = queryManager.getQueryDefinition(queryKey);
    	/*
    	if (pqd == null)
    		throw new Exception("Query Not Found!");
    	*/
    	return pqd.getQueryStatement();
   		
   	}
   	
   	/**
   	 * QueryKey로 RowSet 반환
     * @param ctx PosContext
     * @param dao PosGenericDao
     * @param queryKey queryKey Value
     * @param sQueryStr String 추가될 SQL Statement
     * @param param PosParameter Parameter
   	 * @return PosRowSet   RowSet
   	 */   	   	
   	public static PosRowSet getRowSet(PosContext ctx, PosGenericDao dao, String queryKey, String sQueryStr, PosParameter param) {   				
   		String query = getQuery(ctx, queryKey);      		
        query += sQueryStr != null ? sQueryStr : "";        
        return dao.findByQueryStatement(query, param);    
  	}
   	
   	public static int update(PosContext ctx, PosGenericDao dao, String queryKey, String sQueryStr, PosParameter param) {
   		String query = "";
   		if (queryKey != null && "".equals(queryKey))
   			query = getQuery(ctx, queryKey);      		
        query += sQueryStr != null ? sQueryStr : "";        
        return dao.updateByQueryStatement(query, param);       		
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

   	public static void clearReturnParam(PosContext ctx) {
   		ctx.put("GBL_RET_VALUE", "");
   		addResultKey(ctx, "GBL_RET_VALUE"); 
   	}
   	public static void setReturnParam(PosContext ctx, String sKey, String sValue) {
   		//GBL_RET_VALUE
   		String sRetVal = getCtxStr(ctx, "GBL_RET_VALUE");    		
   		sRetVal = sRetVal + sKey + "=" + sValue + "^";
   		ctx.put("GBL_RET_VALUE", sRetVal);   		
   	}
   	
   	public static void addReturnParam(PosContext ctx) {
//		ctx.put("GBL_RET_VALUE", sReturnParam);
		addResultKey(ctx, "GBL_RET_VALUE");   		
   	}
   	
   	public static String getReturnParam(PosContext ctx, String sKey) {
   		String sRetVal = getCtxStr(ctx, "GBL_RET_VALUE"); 
   		String sRet = "";
   		String[] arrList = sRetVal.split("\\^");
   		for (int i=0; i<arrList.length; i++) {
   			String[] arrKey = arrList[i].split("\\=");
   			
   			if (arrKey[0].equals(sKey)) {
   				sRet = arrKey[1];
   				break;
   			}
   		}
   		return sRet;
   	}
   	
   	/**
   	 * 설명 : SESSION USER ID 반환
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param ctx	PosContext
   	 * @return
   	 */
   	public static String getSessionUserId(PosContext ctx) {
   		String sUserId = "";
        if ( sUserId == null || sUserId.equals("") ) {
    		if ( ctx.get("gdsUser") != null ) {
    	    	PosDataset dsUser = (PosDataset) ctx.get("gdsUser");    	    	
    	    	PosRecord record = dsUser.getRecord(0);    	        
    	    	sUserId = Util.trim(record.getAttribute("USER_ID".trim()));
    		}
        }
   		return trim(sUserId);
   	}
   	
   	/**
   	 * 설명 : 확정상태코드로 회차/일차/경주번호 확정을 반환
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param statCd	회차 상태코드
   	 * @return	int	1:회차별, 2:일차별, 3:경주별	
   	 */
   	public static int getStatIdx(String statCd) {
   		if (statCd.substring(0, 1).equals("2")) return 2;
   		else if (statCd.substring(0, 1).equals("3")) return 3;
   		else return 1;
   	}
   	
   	/**
   	 * 설명 : Object 를 String 으로 변환
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param obj	Object
   	 * @return		String
   	 */
   	public static String getString(Object obj) {
   		return obj == null ? "" : (String)obj;
   	}
   	
   	/**
   	 * 설명 : column Value 반환
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param pr	PosRow
   	 * @param key	Column Id
   	 * @return		String Column Value
   	 */
   	public static String getRosStr(PosRow pr, String key) {
   		Object obj = pr.getAttribute(key);
   		return obj == null ? "" : (String)obj;
   	}
   	
   	public static String getRecordStr(PosRecord record, String key) {
   		Object obj = record.getAttribute(key);
   		return obj == null ? "" : (String)obj;
   	}
   	   	
    
   	/**
   	 * 설명 : 연도/회차/일차/경주번호 별 확정여부/취소가능여부/상태명칭/확정가능여부
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param dao			PosGenericDao
   	 * @param sStndYear		연도
   	 * @param sStatCd		상태코드
   	 * @param sTms			회차
   	 * @param sDayOrd		일차
   	 * @param sRaceNo		경주번호
   	 * @return				String[] {확정여부,취소가능여부,상태명칭,확정가능여부}
   	 */
   	public static String[] getTmsState(PosGenericDao dao, String sStndYear, String sStatCd, String sTms, String sDayOrd, String sRaceNo) {
        String[] arrRet = {"N","N","","Y", ""};
        
    	PosParameter param = new PosParameter();
    	int iParam = 0;

    	param.setWhereClauseParameter(iParam++, sStndYear);
    	param.setWhereClauseParameter(iParam++, sTms);
    	param.setWhereClauseParameter(iParam++, sDayOrd);
    	param.setWhereClauseParameter(iParam++, sRaceNo);
    	param.setWhereClauseParameter(iParam++, sStatCd);
    	
    	PosRowSet prs = dao.find("common_tms_cfm_yn", param);
        
    	if (prs != null && prs.count() > 0) {
    		PosRow pr[] = prs.getAllRow();
    		
    		arrRet[0] = Util.getRosStr(pr[0], "CD1");
    		arrRet[1] = Util.getRosStr(pr[0], "CD2");
    		arrRet[2] = Util.getRosStr(pr[0], "CD3");
    		arrRet[3] = Util.getRosStr(pr[0], "CD4");
    		arrRet[4] = Util.getRosStr(pr[0], "CD5");
    	}    	
    
        return arrRet;
   	}
   	
   	/**
   	 * 설명 : 회차/일차/경주별 확정 처리
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param ctx	PosContext
   	 * @param dao	PosGenericDao
   	 * @param sessionUserId
   	 */
   	public static String saveTmsCfm(PosContext ctx, PosGenericDao dao, String sessionUserId) {
        PosParameter param = null;
        
        boolean bAdd = false;
        String sStatCd = Util.getCtxStr(ctx, "STAT_CD");
        String sCfmYn = Util.getCtxStr(ctx, "CFM_YN");
        String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
        String sTms = Util.getCtxStr(ctx, "TMS");
        String sDayOrd = Util.getCtxStr(ctx, "DAY_ORD");
        String sRaceNo = Util.getCtxStr(ctx, "RACE_NO");
                
        String arrState[] = getTmsState(dao, sStndYear, sStatCd, sTms, sDayOrd, sRaceNo);
        
        /*
         * 확정 요청인 경우 확정가능여부 확인 || 취소 요청인 경우 취소가능여부 확인
         */
        if ((sCfmYn.equals("1") && arrState[3].equals("N")) || (sCfmYn.equals("0") && arrState[1].equals("N"))) {
	        Util.setSvcMsg(ctx, arrState[2] + "입니다! 처리할 수 없습니다!");
	    	return Util.BIZ_EXECPTION;
        }        

        int nStat = Util.getStatIdx(sStatCd);
        String sQueryKey = "";
        
        if (nStat == 3) sQueryKey = "race_cfm";
        else if (nStat == 2) sQueryKey = "day_cfm";
        else sQueryKey = "tms_cfm";
        
        int nIdx = 0;
        param = new PosParameter();

        param.setWhereClauseParameter(nIdx++, sStndYear);
        param.setWhereClauseParameter(nIdx++, sTms);   

        if (nStat > 1) param.setWhereClauseParameter(nIdx++, sDayOrd);
        if (nStat > 2) param.setWhereClauseParameter(nIdx++, sRaceNo);

        PosRowSet prs = dao.find("common_" + sQueryKey + "_select", param);
        
        bAdd = prs.getAllRow() == null || prs.getAllRow().length == 0 ? true : false;
        
        nIdx = 0;
        param = null;
        param = new PosParameter();

        
        if (bAdd) {
            param.setValueParamter(nIdx++, sStndYear);
            param.setValueParamter(nIdx++, sTms);
            if (nStat > 1) param.setValueParamter(nIdx++, sDayOrd);
            if (nStat > 2) param.setValueParamter(nIdx++, sRaceNo);
            param.setValueParamter(nIdx++, sStatCd);
            param.setValueParamter(nIdx++, sCfmYn);
            param.setValueParamter(nIdx++, sessionUserId);
            
            dao.update("common_" + sQueryKey + "_insert", param);        	
        } else {   	
            if (nStat == 2) param.setValueParamter(nIdx++, sCfmYn);
            param.setValueParamter(nIdx++, sStatCd);
            if (nStat == 2) param.setValueParamter(nIdx++, sCfmYn);
            param.setValueParamter(nIdx++, sCfmYn);
            if (nStat == 2) param.setValueParamter(nIdx++, sStatCd);
            param.setValueParamter(nIdx++, sessionUserId);
            param.setValueParamter(nIdx++, sStndYear);
            param.setValueParamter(nIdx++, sTms);
            if (nStat > 1) param.setValueParamter(nIdx++, sDayOrd);
            if (nStat > 2) param.setValueParamter(nIdx++, sRaceNo);
            
            dao.update("common_" + sQueryKey + "_update", param);
        }

        
        param = null;
        param = new PosParameter();
        nIdx = 0;
              
        param.setValueParamter(nIdx++, sStndYear);
        param.setValueParamter(nIdx++, sTms);
        if (nStat > 1) param.setValueParamter(nIdx++, sDayOrd);
        if (nStat > 2) param.setValueParamter(nIdx++, sRaceNo);
        param.setValueParamter(nIdx++, sStndYear);
        param.setValueParamter(nIdx++, sTms);
        if (nStat > 1) param.setValueParamter(nIdx++, sDayOrd);
        if (nStat > 2) param.setValueParamter(nIdx++, sRaceNo);
        param.setValueParamter(nIdx++, sStatCd);
        param.setValueParamter(nIdx++, sCfmYn);
        param.setValueParamter(nIdx++, sessionUserId);
        
        dao.update("common_" + sQueryKey + "_detl_insert", param);   
        
        return PosBizControlConstants.SUCCESS;   	
   	}
   	
   	   	
   	/**
   	 * 설명 : 회차별 배정 확정시 선수별 배정구분 및 후보선수 등록 (saveBizUpdate가 호출 함)
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param ctx	PosContext
   	 * @param dao	PosGenericDao
   	 * @return		String 처리결과
   	 * @throws Exception
   	 */
   	public static String saveTmsAssign(PosContext ctx, PosGenericDao dao) throws Exception {
   		PosParameter param = new PosParameter();
   		int nIdx = 0;
        param.setValueParamter(nIdx++, ctx.get("ASSIGN_CFM_CD"));
        param.setValueParamter(nIdx++, ctx.get("STND_YEAR"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "CRA_TMS"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "CCRC_TMS"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "BCRC_TMS"));
        
        dao.update("tbjb_tms_assign_fo0010_update2", param);   	
        
        param = null;
        param = new PosParameter();
        nIdx = 0;        
        param.setValueParamter(nIdx++, ctx.get("STND_YEAR"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "CRA_TMS"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "CCRC_TMS"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "BCRC_TMS"));

        dao.update("tbjb_subs_racer_delete", param);   	

        if ("001".equals(ctx.get("ASSIGN_CFM_CD").toString())) {
	        param = null;
	        param = new PosParameter();
	        nIdx = 0;        
	        param.setValueParamter(nIdx++, getSessionUserId(ctx));
	        param.setValueParamter(nIdx++, getSessionUserId(ctx));
	        param.setValueParamter(nIdx++, ctx.get("STND_YEAR"));
	        param.setValueParamter(nIdx++, getCtxStr(ctx, "CRA_TMS"));
	        param.setValueParamter(nIdx++, getCtxStr(ctx, "CCRC_TMS"));
	        param.setValueParamter(nIdx++, getCtxStr(ctx, "BCRC_TMS"));
	        dao.update("tbjb_subs_racer_insert", param);   	
        }
        return PosBizControlConstants.SUCCESS;   		
   	}
   	   	

   	//[심판관리-경주기록관리]순위확정, 경주확정시 확정구분코드 업데이트 같이 해줘야함.
   	public static String saveCFM( PosContext ctx, PosGenericDao dao ) {
   		   		
   		/*
				UPDATE TBJC_RACE_INFO 
				SET CFM_CD = ?
				WHERE STND_YEAR = ?
				AND MEET_CD = '001'
				AND TMS = ?
				AND DAY_ORD = ?
				AND RACE_NO = ?	
   		 */
   		
   		PosParameter param = new PosParameter();
   		int nIdx = 0;
        param.setValueParamter(nIdx++, getCtxStr(ctx, "CFM_CD"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "STND_YEAR"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "TMS"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "DAY_ORD"));
        param.setValueParamter(nIdx++, getCtxStr(ctx, "RACE_NO"));
        
        dao.update("jju0001_tbjc_race_info_ua002", param);   		   		
   		
   		return PosBizControlConstants.SUCCESS;
   	}
   	

   	/**
   	 * 설명 : 확정 및 취소 처리시 동일 Transaction 에서 처리해야할 작업을 실행한다.
   	 * 2009. 02. 27 : OffLine_Js
   	 * @param ctx	PosContext
   	 * @param dao	PosGenericDao
   	 * @return		String 처리결과
   	 */
   	//@SuppressWarnings("unchecked")
	public static String saveBizUpdate(PosContext ctx, PosGenericDao dao) {
   		String sMethod = Util.getCtxStr(ctx, "BIZ_METHOD");
   		
   		if ("".equals(sMethod)) return PosBizControlConstants.SUCCESS;
   		
   		Object oRet = null;
    	try {    		
			Class cls = Class.forName("snis.rbm.common.util.Util");
			Object clsObj = cls.newInstance();
			
			Method method = cls.getMethod(sMethod, new Class[]{PosContext.class, PosGenericDao.class});
			oRet = method.invoke(clsObj, new Object[] {ctx, dao});
			method = null;
			clsObj = null;
			
		} catch (Exception e) {
			oRet = e.getMessage();
			e.printStackTrace();
		}	
		
		return (String)oRet;
   		
   	}
	
	/**
   	 * 설명 : 날짜형식체크
   	 * 2011. 10. 12 : OffLine_Js
   	 * @param ctx	PosContext
   	 * @param dao	PosGenericDao
   	 * @return		String 처리결과
   	 */
	public static boolean checkDate(String sDate) {
		boolean bOK = false;

	    sDate = getNumber(sDate);

	    if ( sDate.length() != 8 ) {
	        return bOK;
	    }
	    
	    int nYear  = Integer.parseInt(sDate.substring(0, 4));
	    int nMonth = Integer.parseInt(sDate.substring(4, 6));
	    int nDay   = Integer.parseInt(sDate.substring(6, 8));
	    
	    if ( nYear < 1950) {
	    	return false;
	    }
	    
	    if ( ( nMonth == 1 ) || ( nMonth == 3  ) || ( nMonth == 5  ) || ( nMonth == 7 )
	      || ( nMonth == 8 ) || ( nMonth == 10 ) || ( nMonth == 12 ) ) {
	       if ( nDay <= 31 ) {
	    	   bOK = true;
	       }
	    } else if( ( nMonth == 4 ) || ( nMonth == 6 ) || ( nMonth == 9 ) || ( nMonth == 11 ) ) {
	       if ( nDay <= 30 ) {
	    	   bOK = true;
	       }
	    } else if ( nMonth == 2 ) {
	       if ( ( nYear % 4 == 0 ) && ( nYear % 100 != 0 ) || ( nYear % 400 == 0 ) ) {
	          if ( nDay <= 29 ) {
	        	  bOK = true;
	          }
	       } else {
	          if ( nDay <= 28 ) {
	        	  bOK = true;
	          }
	       }
	    }

	    if ( !bOK || (nDay == 0 ) ) {
	        return false;
	    }

	    return true;
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
		sendMessenger(sSndr, sRcvr, "", sMesg, sUrl);
	}
	public static void sendMessenger(String sSndr, String sRcvr, String sTitle, String sMesg, String sUrl) {
		try {
			System.out.println("Util.sendMessenger:sRcvr="+sRcvr+",sMesg="+sMesg);    	
			// 받을 사람이 있는 경우에만 전송한다.
			if (sRcvr.length() > 0 && sMesg.length() > 0) {
				try {
					Util.sendSosfoMsg( sRcvr, sSndr, sTitle, sMesg, sUrl);
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
		return sendSosfoMsg(aRcvEmpId, sSendEmpId, "", sMsg, sUrl);
	}
	
	private static String sendSosfoMsg( String aRcvEmpId, String sSendEmpId, String sTitle, String sMsg, String sUrl) throws Exception {
		int nRtn = 0;
		/* 신규메신저 교체 반영 2015.2.4
		 http://140.101.1.131:8089/notify/sendNotify.jsp
		 code =  EXT로 고정해주세요
		 sendNM =  보낸사람 이름
		 sendID =  보낸사람 사번
		 recvID =    받는사람 사번@kspo.or.kr
		 notyTitle =  알림 제목
		 notyContent = 알림 내용
		 url	=   호출 url
		*/
	    String SERVER_URL = "http://140.101.1.131:8089/notify/sendNotify.jsp";
	    String MSG_CODE   = "RACE";    // default
	    String SND_USER  = sSendEmpId; // default
	    String RCV_USER  = aRcvEmpId;
	    String DOC_TITLE = URLEncoder.encode(sTitle,"utf-8"); // default
	    String DOC_MSG   = URLEncoder.encode(sMsg,"utf-8"); // default
	    String DOC_URL   = URLEncoder.encode(sUrl,"utf-8");
	    
	    
	    String GOURL = SERVER_URL;
        String urlParameters = "" ;
        urlParameters += "code="+MSG_CODE;
        urlParameters += "&sendID="+SND_USER;
        urlParameters += "&recvID="+RCV_USER  + "@kspo.or.kr";
        urlParameters += "&notyTitle="+DOC_TITLE;
        urlParameters += "&url="+DOC_URL;
        urlParameters += "&notyContent="+DOC_MSG;

		// 수신자가 있어야만 발송함. 없으면 전체발송인데 여기서 막았다.
		if (RCV_USER.length() > 0) {
			URL targetURL = new URL(SERVER_URL);
			HttpURLConnection httpCon = (HttpURLConnection) targetURL.openConnection();
			
			httpCon.setDoOutput(true); // post방식:true
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);

            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpCon.setRequestProperty("Cache-Control", "no-cache");
            httpCon.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            httpCon.setRequestMethod("POST");
            httpCon.connect();
				
            DataOutputStream httpOut = new DataOutputStream(httpCon.getOutputStream());
            httpOut.write(urlParameters.getBytes("EUC-KR"));
            httpOut.flush();
            httpOut.close();

            System.out.println("==결과확인==");
            
            InputStreamReader rd = new InputStreamReader(httpCon.getInputStream());
            rd.close();

			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "EUC-KR"));
			String line = null;
			System.out.println("httpCon respone --->");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("<--- httpCon respone");

			if (httpCon != null) httpCon.disconnect();
		}

		return String.valueOf(nRtn);
	}
}