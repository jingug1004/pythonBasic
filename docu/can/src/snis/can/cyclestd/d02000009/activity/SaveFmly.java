
/*================================================================================
 * 시스템			: 후보생 가족사항 관리
 * 소스파일 이름	: snis.can.system.d02000009.activity.SaveFmly.java
 * 파일설명		: 후보생 가족사항 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-27
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/


package snis.can.cyclestd.d02000009.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 후보생 가족사항 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveFmly  extends SnisActivity
{    
	
    public SaveFmly()
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
   	
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}  
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsFmly";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsFmly============>"+record);
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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset headDs = (PosDataset)ctx.get("dsCandIdent");
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsFmly";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        PosRecord recHead = (PosRecord) headDs.getRecord(0);

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateSaveFmly(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertSaveFmly(record,recHead);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteSaveFmly(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 후보생 가족사항  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSaveFmly(PosRecord record,PosRecord recHead) 
    {
   	    logger.logInfo("==========================  후보생 가족사항   입력   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("Fmly======>");
        	
	        param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RELN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RES_AREA")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_JOB")));			
			param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").insert("d02000009_ib002", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> 후보생 가족사항  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSaveFmly(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("Fmly 업데이트======>");        	
        
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RELN")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("NM")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RES_AREA")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_JOB")));
        	param.setValueParamter(i++, SESSION_USER_ID); 
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
        	
			i = 0;			
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
			
			dmlcount += this.getDao("candao").update("d02000009_ub002", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> 후보생가족사항  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteSaveFmly(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));
        
        int dmlcount  = this.getDao("candao").update("d02000009_db002", param);
        	
        
        return dmlcount;
    }    
}
