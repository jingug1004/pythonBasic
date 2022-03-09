/*================================================================================
 * 시스템			: 상벌조항/항목 코드 관리
 * 소스파일 이름	: snis.can.system.d02000012.activity.SaveItemCode.java
 * 파일설명		: 상벌조항/항목 코드 관리
 * 작성자			: 왕영신
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000010.activity;

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
* @auther 왕영신
* @version 1.0
*/
public class SaveItemCode extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveItemCode()
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
        String     sDsName = "";

        logger.logInfo("codemanager start ==============");
        
        sDsName = "dsItemCodeListValue";
        if ( ctx.get(sDsName) != null ) {
        	
	        ds    = (PosDataset)ctx.get(sDsName);
	        for ( int i = 0; i < ds.getRecordCount(); i++ ) 
	        {
	           	PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
	        saveState(ctx,sDsName);
        }
        
        logger.logInfo("---------------------------------------");        
        sDsName = "dsPrizCodeListValue";
        if ( ctx.get(sDsName) != null) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        for ( int i = 0; i < ds.getRecordCount(); i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
	        saveStateDetail(ctx,sDsName);
        }

        logger.logInfo("codemanager end =================");
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx , String sDsName) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         logger.logInfo("nSize ============= " + nSize);
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(" record.getType() ==============" + record.getType());
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	nSaveCount += updateItemCode(record);
		        }
		        
		        else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nSaveCount += insertItemCode(record);
		        }
		        
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount += deleteItemCode(record);	            	
	            }		        
	        } // end for
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }    
    
    protected void saveStateDetail(PosContext ctx , String sDsName){
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         logger.logInfo("nSize2 ============= " + nSize);
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(" record.getType2() ==============" + record.getType());
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	nSaveCount += updatePrizCode(record);
		        }
		        
		        else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nSaveCount += insertPrizCode(record);
		        }
		        
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount += deletePrizCode(record);	            	
	            }		        

		        logger.logInfo("nDeleteCount2 ================= " + nDeleteCount);
		        logger.logInfo("nSaveCount2 ================= " + nSaveCount);
	        } // end for
        } // end if
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 그룹코드 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertItemCode(PosRecord record) 
    {
    	logger.logInfo("insertItemCode start ==============");
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ITEM_CD"));
        param.setValueParamter(i++, record.getAttribute("ITEM_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_item_cd_ib001", param);
        
        logger.logInfo("insertItemCode end ==============");
        return dmlcount;
    }
    
    
    
    /**
     * <p> 그룹코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateItemCode(PosRecord record)
    {
    	logger.logInfo("updateItemCode start ==============");
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ITEM_NM"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD" ));

        int dmlcount = this.getDao("candao").update("tbdb_item_cd_ub001", param);
        
        logger.logInfo("updateItemCode end ==============");
        return dmlcount;
    }

    
    
    /**
     * <p> 그룹코드  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteItemCode(PosRecord record) 
    {
    	logger.logInfo("deleteItemCode start ==============");
    	logger.logInfo("session_user_id ==================" + SESSION_USER_ID);
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD" ));
        
        int dmlcount = this.getDao("candao").delete("tbdb_item_cd_db001", param);
        
        PosParameter param1 = new PosParameter();
        int j = 0;
        param1.setWhereClauseParameter(j++, record.getAttribute("ITEM_CD" ));
        param1.setWhereClauseParameter(j++, "");

        dmlcount = dmlcount + this.getDao("candao").delete("tbdb_priz_penal_db001", param1);
        
        logger.logInfo("deleteItemCode end ==============");
        return dmlcount;
    }
    
    
    /**
     * <p> 상세코드 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertPrizCode(PosRecord record) 
    {
    	logger.logInfo("insertPrizCode start ==============");
        PosParameter param = new PosParameter();   
  
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL_CD"));
        param.setValueParamter(i++, record.getAttribute("ITEM_CD"));
        param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL_NM"));
        param.setValueParamter(i++, record.getAttribute("PENAL_SCR"));
        param.setValueParamter(i++, record.getAttribute("ADD_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("candao").insert("tbdb_priz_penal_ib001", param);
        
        logger.logInfo("insertPrizCode end ==============");
        return dmlcount;
    }
    
    
    
    /**
     * <p> 상세코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePrizCode(PosRecord record)
    {
    	logger.logInfo("updatePrizCode start ==============");
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL_NM"      ));
        param.setValueParamter(i++, record.getAttribute("PENAL_SCR"      ));
        param.setValueParamter(i++, record.getAttribute("ADD_SCR"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("PRIZ_PENAL_CD" ));

        int dmlcount = this.getDao("candao").update("tbdb_priz_penal_ub001", param);
        
        logger.logInfo("updatePrizCode end ==============");
        return dmlcount;
    }    
    
    
    /**
     * <p> 상세코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deletePrizCode(PosRecord record) 
    {
    	logger.logInfo("deletePrizCode start ==============");
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("PRIZ_PENAL_CD" ));

        int dmlcount = this.getDao("candao").delete("tbdb_priz_penal_db001", param);
        
        logger.logInfo("deletePrizCode end ==============");
        return dmlcount;
    }        
           
}
