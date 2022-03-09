/*================================================================================
 * 시스템			: 지급조서내역
 * 소스파일 이름	: snis.rbm.business.rsm5010.activity.SavePayCntnt
 * 파일설명		: 지급조서관리_상세내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePcTaxCross extends SnisActivity {
	
	public SavePcTaxCross(){}

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
    	saveState(ctx);
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
        
        sDsName = "dsPcTax";
        
        String sPayYear = (String)ctx.get("PAY_YEAR");
        String sMeetCd = (String)ctx.get("MEET_CD");
        String sSellCd = (String)ctx.get("SELL_CD");
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        //verifyPcTax(ds);	//데이터 검증
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            // 기존 내용 삭제
	            if(i==0)	deletePcTaxAll(record, sPayYear, sMeetCd, sSellCd);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		        	
		        	nTempCnt = savePcTax(record);	//당첨내역 저장
		        	saveDetlCntnt(record);			//상세내역 저장
		        	saveCfmCntnt(record);			//확정내역 저장
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> 지급조서관리_확정내역 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int savePcTax(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //주민등록번호
        
        param.setValueParamter(i++, record.getAttribute("REFUND"));  	//환급금액
        param.setValueParamter(i++, record.getAttribute("SELL_AMT"));	//판매금액
        param.setValueParamter(i++, record.getAttribute("JIGEUP_AMT"));	//지급금액
        param.setValueParamter(i++, record.getAttribute("COST"));		//필요경비
        param.setValueParamter(i++, record.getAttribute("GITA1"));		//소득세
        
        param.setValueParamter(i++, record.getAttribute("GITA2"));		//주민세
        param.setValueParamter(i++, record.getAttribute("GITA_PAY"));	//차인지금액
        param.setValueParamter(i++, record.getAttribute("JIGEUP_DT"));	//지급일자
        param.setValueParamter(i++, record.getAttribute("F_SSN"));		//외국인등록번
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)

        int dmlcount = this.getDao("rbmdao").update("rsm5030_i01", param);
	
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
        param.setValueParamter(i++, "002");								//1차확정코드
        param.setValueParamter(i++, record.getAttribute("SND_CFM_CD"));	//2차확정코드
        param.setValueParamter(i++, record.getAttribute("THR_CFM_CD"));	//3차확정코드
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수성자)
                		
        int dmlcount = this.getDao("rbmdao").update("rsm5010_m02", param);
	
        return dmlcount;
    }
    
    
    /**
     * <p> 지급조서관리_상세내역 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveDetlCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //주민등록번호
        
        param.setValueParamter(i++, record.getAttribute("CUST_NM"));	//고객성명
        param.setValueParamter(i++, record.getAttribute("CUST_ADDR"));	//고객주소
        param.setValueParamter(i++, record.getAttribute("FORE_NO"));	//외국인등록번호
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수성자)

        int dmlcount = this.getDao("rbmdao").update("rsm5010_m01", param);
	
        return dmlcount;
    }
    
    /**
     * <p> 당첨지급내역 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePcTaxAll(PosRecord record, String sPayYear, String sMeetCd, String SellCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//지급년도
        param.setValueParamter(i++, sMeetCd);	//시행처
        param.setValueParamter(i++, SellCd);	//판매처
        
        int dmlcount = this.getDao("rbmdao").update("rsm5030_d01", param);
        this.getDao("rbmdao").update("rsm5030_d02", param);
        this.getDao("rbmdao").update("rsm5030_d03", param);

        return dmlcount;
    }
    
    
    /**
     * <p> 지급조서교차_데이터 검증 </p>
     * @param   PosDataset	ds	  데이타셋에 대한 하나의 레코드
     * @return  int	        nRtn  0:이상없음 -1:이상
     * @throws  none
     */
    protected String verifyPcTax(PosDataset ds) {
    	String sMeetCd, sSellCd, sTns, sCustSsn, sJigeupDt, sJigeupYear, sCheckJigeup = "";
    	String sRtn = "";
    	
    	for ( int i = 0; i < ds.getRecordCount(); i++ ) {
    		PosRecord record = ds.getRecord(i);
    		
    		sMeetCd     = (String)record.getAttribute("MEET_CD");
			sSellCd     = (String)record.getAttribute("SELL_CD");
			sTns        = (String)record.getAttribute("TNS");
			sCustSsn    = (String)record.getAttribute("CUST_SSN");
			sJigeupDt   = (String)record.getAttribute("JIGEUP_DT");
			sJigeupYear = sJigeupDt.substring(0, 4);
			
			if(i == 0)	sCheckJigeup = sJigeupYear;
			
			if( !sCheckJigeup.equals(sJigeupYear) ) {
				//잘못된 지급년도
			}
			
			if(sMeetCd.equals("001") || sMeetCd.equals("002") ||
			   sMeetCd.equals("003") || sMeetCd.equals("004") ) {
			} else {
				//잘못된 MEET_CD
			}
			
			if(sSellCd.equals("02") || sSellCd.equals("04")) {	
			} else {
				//잘못된 SELL_CD
			}
			
			if( sTns.length() != 14) {
				//잘못된 TSN
			}
			
			if( sCustSsn.length() != 14) {
				//잘못된 주민번호
			}
			
			if( !Util.checkDate(sJigeupDt) ) {
				//잘못된 지급일자
			}
    	}
    	return sRtn;
    }
}
