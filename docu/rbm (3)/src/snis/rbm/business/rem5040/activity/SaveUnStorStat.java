/*================================================================================
 * 시스템			: 지정좌석실 관리
 * 소스파일 이름	: snis.rbm.business.rem5040.activity.SaveUnGoodStor.java
 * 파일설명		: 지정좌석실 상품 출고 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-08-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem5040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveUnStorStat extends SnisActivity {
	
	public SaveUnStorStat(){
		
		
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
    	String sUnStorDt = "";
        String StatCd    = "";
        String OldStatCd = "";
        String BrncCd    = "";
        String sAprvId   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsGoodUnStor";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        sUnStorDt    = (String)ctx.get("UNSTOR_DT");
	        OldStatCd    = (String)ctx.get("OLD_STAT_CD");
	        StatCd       = (String)ctx.get("NEW_STAT_CD");
	        String brncCd = (String)ctx.get("BRNC_CD");
	        String teamCd = ctx.get("TEAM_CD").toString().substring(2);
	        
			if ("002".equals(StatCd)) {
		        PosParameter param = new PosParameter();
	        	PosRowSet rowset;
	    		int i = 0;            
	    		
	    		param.setWhereClauseParameter(i++, teamCd);			//팀코드            
	    		rowset = this.getDao("rbmdao").find("rbs6020_s03", param);
	    		if (rowset.hasNext()) {				// 지점의 지점장 조회(각 부서(실,팀)의 부서장)
	    			PosRow row = rowset.next();
					sAprvId = (String)row.getAttribute("CAPTAIN_ID");
				} else {
	    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E027");
	    			return;
	    		}	    		
    		}
    		
    		/*
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	        		nSaveCount += updateUnGoodStorStat(record, sUnStorDt, StatCd, sAprvId);
		        }		        
	        }
	        */
			updateUnGoodStorStat(brncCd, sUnStorDt, StatCd, sAprvId);
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }


    /**
     * <p> IP 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateUnGoodStorStat(String brncCd, String UnStorDt, String StatCd, String AprvId) 
    {	
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, StatCd);								//상태
        param.setValueParamter(i++, StatCd);								//승인자 사번
    	param.setValueParamter(i++, AprvId);								//승인자 사번
        param.setValueParamter(i++, StatCd);								//승인자 사번
        param.setValueParamter(i++, SESSION_USER_ID);						//입력자/수정자 사번
        param.setValueParamter(i++, brncCd);		//지점코드
        //param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//입고일자
        //param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//연번
        param.setValueParamter(i++, UnStorDt);								//출고일자
        
        int dmlcount = this.getDao("rbmdao").update("rem5040_u02", param);
        
        return dmlcount;
    }
    
    
    
}
