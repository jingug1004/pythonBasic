/*================================================================================
 * 시스템			: 등급변경 입력
 * 소스파일 이름	: snis.boa.system.e02020020.activity.SaveRacerDegree.java
 * 파일설명		: 등급변경 입력
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02020020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 선수등급을 저장(입력/수정)하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveRacerDegree extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveRacerDegree()
    {
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        sDsName = "dsOutRacerDegree";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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

        // 선수등급이력 입력
        sDsName = "dsOutRacerDegree";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	         nTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	        	if ( updateDegree(record, ctx) < 1 ) {
	        		nTempCnt = insertDegree(record, ctx);
	        	}
		        	
	        	updateRacerMaster(record, ctx);
			    
		        nSaveCount 		= nSaveCount 	+ 	nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    /**
     * <p> 선수등급이력 입력 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int insertDegree(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"      ));
        param.setValueParamter(i++, Util.getCurrentDate("yyyyMMdd"));
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        param.setValueParamter(i++, ctx   .get         ("RMK"           ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbeb_racer_dgre_ib001", param);

        return dmlcount;
    }
    
    /**
     * <p> 선수등급이력 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateDegree(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        param.setValueParamter(i++, ctx   .get         ("RMK"           ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"      ));
        param.setWhereClauseParameter(i++, Util.getCurrentDate("yyyyMMdd"));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_dgre_ub001", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 선수등급 수정(선수마스터) </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacerMaster(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("NEW_RACER_GRD" ));
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"      ));

        int dmlcount = this.getDao("boadao").update("tbec_racer_master_ub001", param);
        
        return dmlcount;
    }
}
