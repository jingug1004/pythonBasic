/*================================================================================
 * 시스템			: 세부교육일정
 * 소스파일 이름	: snis.can.cyclestd.d02000007.activity.SaveChoiceDefault.java
 * 파일설명		: 코드 관리
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000014.activity;

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

public class SaveClass extends SnisActivity 
{
	public SaveClass() { }
	
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
    	
        PosDataset ds1;
        int    nSize1 = 0;
        String     sDsName = "";
   
        sDsName = "dsClsOrgan";
        if ( ctx.get(sDsName) != null ) {
	        ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsClsOrgan------------------->"+record);
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
		int nDeleteCount = 0; 

		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //종목구분 저장       
        ds1   = (PosDataset) ctx.get("dsClsOrgan");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	// if ( (nTempCnt = updateCls_Organ_Detl(record)) == 0 ) {
                  	nTempCnt = insertCls_Organ_Detl(record);
               //  }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteCls_Organ_Detl(record);
             }
         }
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
     }
     	
     
     /**
      * <p> 경륜반편성 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("insertCls_Organ_Detl start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        int dmlcount = 0;
              

        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
        param.setValueParamter(i++, SESSION_USER_ID);
        	
    	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
    	param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
        param.setValueParamter(i++, SESSION_USER_ID);

        dmlcount = this.getDao("candao").insert("tbdb_cls_organ_detl_ib000", param);
        
		logger.logInfo("insertCls_Organ_Detl end============================");
		return dmlcount;
     }
     
     /**
      * <p> 경륜반편성 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertCls_Organ_Detl1(PosRecord record) 
     {
		logger.logInfo("insertCls_Organ_Detl start============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));	
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdb_cls_organ_detl_ib001", param);

		logger.logInfo("insertCls_Organ_Detl end============================");
		return dmlcount;
     }
     
     /**
      * <p> 경륜반편성 수정  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("updateCls_Organ_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		
//		String test1 = Util.trim(record.getAttribute("RACER_PERIO_NO"));
//		String test2 = Util.trim(record.getAttribute("CLS"));
//		String test3 = Util.trim(record.getAttribute("CAND_NO"));
//		String test4 = Util.trim(record.getAttribute("GUD_LECTR_ID"));
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("GUD_LECTR_ID")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").update("tbdb_cls_organ_detl_ub001", param);

		logger.logInfo("updateCls_Organ_Detl end============================");
		return dmlcount;
     }
     
 
     /**
      * <p> 경륜반편성 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteCls_Organ_Detl(PosRecord record) 
     {
		logger.logInfo("deleteCls_Organ_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CLS_CD")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").delete("tbdb_cls_organ_detl_db001", param);

		logger.logInfo("deleteCls_Organ_Detl end============================");
		return dmlcount;
     }

}