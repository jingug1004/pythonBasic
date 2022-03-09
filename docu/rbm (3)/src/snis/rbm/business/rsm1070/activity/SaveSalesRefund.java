/*
 * ================================================================================
 * 시스템 : 
 * 관리 소스파일 이름 : snis.rbm.system.rsm1070.activity.SaveSalesRefund.java 파일
 * 설명 : 환불액 관리
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011- 
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rsm1070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSalesRefund extends SnisActivity {

	public SaveSalesRefund(){
		
	}
	
	
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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsRefund";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveRefund(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 삼진아웃활동결과  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRefund(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        
        param.setValueParamter(i++, record.getAttribute("DIV_TOTAL"));		//매출종액
        param.setValueParamter(i++, record.getAttribute("REFUND"));			//환불액

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));		//시행처
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//기준년도
        param.setValueParamter(i++, record.getAttribute("TMS"));			//회차

        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));		//회차 차수
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));		//경주번호
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));		//판매처
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//comm 번호
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));			//div 번호
        param.setValueParamter(i++, SESSION_USER_ID);						//작성자
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자 
        
                
        int dmlcount = this.getDao("rbmdao").update("rsm1070_m01", param);
        
        return dmlcount;
    }
    
    

}
