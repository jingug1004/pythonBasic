/*================================================================================
 * 시스템			: 지급조서내역
 * 소스파일 이름	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * 파일설명		: 지급조서관리_확정내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCfmCancel extends SnisActivity {
	
	public SaveCfmCancel(){}

	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	String sValue = (String) ctx.get("SEND_VALUE"); // 실행 화면명 OR 이벤트
    	System.out.println("sValue : "+sValue);
    	if( sValue.equals("SEN") )
    	{
    		updateCfmCancelSen(ctx);
    	}
    	else
    	{
    		saveState(ctx);
    	}
    	
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sValue   = (String)ctx.get("SEND_VALUE");	//화면명        
        sDsName = "dsPcTax";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        int nCnt = 0;
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if( sValue.equals("FST") ){
		            //1차확정취소
	            	nTempCnt = updateCfmCancelFst(record);
	            }else  if( sValue.equals("SEN") ){
	            	// 2차확청취소
	            	nTempCnt = updateCfmCancelSen(record);
	            }else  if( sValue.equals("THR") ){
	            	// 3차확정취소
	            	nTempCnt = updateCfmCancelThr(record);
	            }	        	
		        nSaveCount = nSaveCount + nTempCnt; 
	        }	        
        }       
        Util.setSaveCount(ctx, nSaveCount);
    }


    /**
     * <p> 지급조서관리_1차확정 취소 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfmCancelFst(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u01", param);

        return dmlcount;
    }
    
    /**
     * <p> 지급조서관리_2차확정 취소 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfmCancelSen(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("TAX_MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("TAX_SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TAX_TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> 지급조서관리_2차확정 취소 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	protected int updateCfmCancelSen(PosContext ctx) {

		PosRowSet prs = null;
		PosRow[] pr = null;
		PosParameter param = new PosParameter();

		String PAY_YEAR = (String) ctx.get("PAY_YEAR"); // <!-- 지급년도 -->
		String MEET_CD = (String) ctx.get("MEET_CD"); // <!-- 경륜장코드(시행처)-->
		String SELL_CD = (String) ctx.get("SELL_CD"); // <!-- 운영처코드(판매처)-->
		String BRNC_CD = (String) ctx.get("BRNC_CD"); // <!-- 지점코드-->

		int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);

		prs = this.getDao("rbmdao").find("rsm5040_s07", param);
		pr = prs.getAllRow();
		
		System.out.println("pr.length : "+pr.length);

		String[] arrQuery = new String[pr.length];

		for (int prI = 0; prI < pr.length; prI++) {
			arrQuery[prI] = " UPDATE TBRD_CFM_CNTNT SET                    			  \n"+ 
			"      SND_CFM_CD = '002'	-- 	(SND_CFM_CD) 2차확정코드  \n"+                   
			"     ,UPDT_ID    = '"+SESSION_USER_ID+"'	--	(UPDT_ID     수정자               \n"+      
			"     ,UPDT_DT    = SYSDATE     --  	  수정일시		  \n"+ 
			" WHERE	MEET_CD  	= '"+pr[prI].getAttribute("TAX_MEET_CD")+"'	--	(MEET_CD)    경륜장코드	  \n"+ 
			" AND 	SELL_CD  	= '"+pr[prI].getAttribute("TAX_SELL_CD")+"'	-- 	(SELL_CD)    운영처코드	  \n"+ 
			" AND 	TSN_NO   	= '"+pr[prI].getAttribute("TAX_TSN")+"'	--	(TSN)        경주권번호	  \n"+ 
			" AND 	PAY_YEAR 	= '"+pr[prI].getAttribute("PAY_YEAR")+"'	--	(PAY_YEAR)   지급년도	  \n";
			
			 
		}

		int[] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
		int intResult = 0; // 결과값
		int failure_count = 0;

		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}

		return intResult;

	}
    
    /**
     * <p> 지급조서관리_3차확정 취소 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfmCancelThr(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u03", param);

        return dmlcount;
    }
    
    /**
     * <p> 지급조서관리_확정내역 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도    
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //주민등록번호
 
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));  	//지점코드       
        param.setValueParamter(i++, record.getAttribute("FST_CFM_CD"));	//1차확정코드
        param.setValueParamter(i++, record.getAttribute("SND_CFM_CD"));	//2차확정코드
        param.setValueParamter(i++, record.getAttribute("THR_CFM_CD"));	//3차확정코드
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수성자)
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_m02", param);

        return dmlcount;
    }
    
    /**
     * <p> 지급조서입력 전체 개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			지급조서관리 개수
     * @throws  none
     */
    protected int getFstCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String strCommNo =  record.getAttribute("COMM_NO" ).toString();
        String strSellCd =  record.getAttribute("SELL_CD" ).toString();
        if(strSellCd==null)strSellCd="03";
        // 광명은 여러개의 지점을 하나로 보기때문에 정규식으로 조건을 건다.
        
        if( !strSellCd.equals("03") && (strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") )){
        	strCommNo = "^(0[123468])$";    	
        }        
        
		if(strSellCd.equals("03")){
			strCommNo ="98";
		}
		
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5010_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 지급조서교차 전체개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			지급조서관리 개수
     * @throws  none
     */
    protected int getCrossCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5030_s01", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 지급조서검증 전체개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			지급조서관리 개수
     * @throws  none
     */
    protected int getSndCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;       
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 지급조서검증 일치여부 확인  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			일치여부( 0 : 일치 0초과 : 불일치)
     * @throws  none
     */
    protected int getValidCnt(String sPayYear) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, sPayYear);	//지급년도
        param.setWhereClauseParameter(i++, sPayYear);	//지급년도
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s04", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 지급조서확정 전체개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			지급조서관리 개수
     * @throws  none
     */
    protected int getThrCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5050_s01", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}
