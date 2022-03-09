/*================================================================================
 * 시스템			: 사건  관리
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 사건 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvntMana extends SnisActivity {
	
	public SaveEvntMana(){}
 
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
        
        sDsName = "dsEvntMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveEvntMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {	
	            	int nDeleteValid = getEvntHistManaKey(record);
	            	
	            	//사건에 대한 사건이력이 존재할 시, 삭제 불가능
	            	if( nDeleteValid > 0 ) {	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsgCode(ctx, "SNIS_RBM_A007");
		            		
		            		return;
		            	} 
	            	} 
	            	
	            	nDeleteCount = nDeleteCount + deleteEvntMana(record);
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 사건 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEvntMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));	//사건순번
        param.setValueParamter(i++, record.getAttribute("GEN_DT"));		//발생일자
        param.setValueParamter(i++, record.getAttribute("EVNT_NO"));	//사건번호
        param.setValueParamter(i++, record.getAttribute("EVNT_NM"));	//사건명
        
        param.setValueParamter(i++, record.getAttribute("MNG"));		//담당자
        param.setValueParamter(i++, record.getAttribute("EVNT_CNTNT"));	//사건요약
        param.setValueParamter(i++, record.getAttribute("END_YN"));		//종료여부
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        
        int dmlcount = this.getDao("rbmdao").update("rbr4010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 사건  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEvntMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수정자)
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("EVNT_SEQ"));   //사건순번

        int dmlcount = this.getDao("rbmdao").update("rbr4010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 사건에 대한 사건이력 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
     * @throws  none
     */
    protected int getEvntHistManaKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("EVNT_SEQ" ));	//사건순번
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr4010_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}