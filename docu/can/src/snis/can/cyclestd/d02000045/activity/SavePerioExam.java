/*================================================================================
 * 시스템		    : 학사관리
 * 소스파일 이름	: snis.can.cyclestd.d02000008.activity.SearchPerioExam.java
 * 파일설명		: 선수자격시험
 * 작성자		    : 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000045.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class SavePerioExam extends SnisActivity 
{
	public SavePerioExam() { }
	
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
        String     sDsName = "";
        int        nSize   = 0;

        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
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
     * @param   ctx	PosContext 종목구분 저장소
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
		int nSaveCount   = 0; 

    	PosDataset ds;
        String     sDsName  = "";
        int        nSize    = 0;
        int        nTempCnt = 0;
 	
 		ArrayList alItemCd = new ArrayList();

 		// 자전거실기 저장
		alItemCd.clear();
		alItemCd.add("301");
		alItemCd.add("302");
		alItemCd.add("303");
		alItemCd.add("304");
		alItemCd.add("305");
		alItemCd.add("306");
		 
    	ctx.put("EXAM_CD",     "004");
    	ctx.put("ITEM_GBN_CD", "003");

		//실기 저장       
        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
        	for ( int i = 0; i < nSize; i++ ) {
                PosRecord record = ds.getRecord(i);
                for ( int j = 0; j < alItemCd.size(); j++ ) {
                	ctx.put("ITEM_CD", alItemCd.get(j));
	                if ( (nTempCnt = updatePerioExam(record, ctx, (j + 1))) == 0 ) {
	                	nTempCnt = insertPerioExam(record, ctx, (j + 1));
	                }
	                
	                nSaveCount = nSaveCount + nTempCnt;
                }
            }
        }
        
		Util.setSaveCount  (ctx, nSaveCount     );
     }
     
     /**
      * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
      * @param   ctx	 PosContext	환산기준표 저장소
      * @return  none
      * @throws  none
      */
 	protected int insertPerioExam(PosRecord record, PosContext ctx, int nSeq) 
 	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, ctx   .get         ("EXAM_CD"       ));
		param.setValueParamter(i++, ctx   .get         ("ITEM_GBN_CD"   ));
		param.setValueParamter(i++, ctx   .get         ("ITEM_CD"       ));
		param.setValueParamter(i++, record.getAttribute("CAND_NO"       ));
        param.setValueParamter(i++, record.getAttribute("EXAM_REC_" + nSeq));
        param.setValueParamter(i++, record.getAttribute("EXAM_SCR_" + nSeq));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);
 		
 		int dmlcount = this.getDao("candao").update("tbdb_racer_exam_cyc_ib001", param);
 		
 		return dmlcount;
 	}
 	
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx	 PosContext	환산기준표 저장소
     * @return  none
     * @throws  none
     */
	protected int updatePerioExam(PosRecord record, PosContext ctx, int nSeq) 
	{
        PosParameter param = new PosParameter();

        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("EXAM_REC_" + nSeq));
        param.setValueParamter(i++, record.getAttribute("EXAM_SCR_" + nSeq));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setWhereClauseParameter(i++, ctx   .get         ("EXAM_CD"       ));
		param.setWhereClauseParameter(i++, ctx   .get         ("ITEM_GBN_CD"   ));
		param.setWhereClauseParameter(i++, ctx   .get         ("ITEM_CD"       ));
		param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"       ));

		int dmlcount = this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
		
		return dmlcount;
	}
}
