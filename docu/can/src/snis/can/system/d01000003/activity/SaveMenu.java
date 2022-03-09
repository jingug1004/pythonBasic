/*================================================================================
 * 시스템			: 메뉴 관리
 * 소스파일 이름	: snis.can.system.d01000003.activity.MenuManager.java
 * 파일설명		: 메뉴 관리
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.system.d01000003.activity;

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
* @auther 김영철
* @version 1.0
*/
public class SaveMenu extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMenu()
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
        sDsName = "dsMenuGrpValue";
        logger.logInfo("codemanager start ==============");
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
    	
        sDsName = "dsMenuListValue";
        logger.logInfo("codemanager start ==============");
        
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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        int nDTempCnt     = 0;

        sDsName = "dsMenuGrpValue";
        
        String sPersonalDataYn; //개인정보 
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        
	         logger.logInfo("========== NSIZE " + nSize + "=======================");

	         nTempCnt = 0;
	         nDTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
	        	if(sPersonalDataYn == null) sPersonalDataYn=""; //개인정보
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateMenu(record);
		        }else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateMenu(record);
		        	if(nTempCnt == 0) nTempCnt = insertMenu(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			    	nDTempCnt = deleteMenu(record);
			    	nDeleteCount += nDTempCnt;
			    	if(nDTempCnt > 0) deleteAuthMenu(record);
		        }
		        nSaveCount 		= nSaveCount 	+ 	nTempCnt;
		       // nDeleteCount 	= nDeleteCount 	+ 	nDTempCnt;
	        } // end for
        }
         
        sDsName = "dsMenuListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	         logger.logInfo("========== NSIZE " + nSize + "=======================");

	         nTempCnt = 0;
	         nDTempCnt = 0;
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
	        	if(sPersonalDataYn == null) sPersonalDataYn=""; //개인정보
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateMenu(record);
		        }else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateMenu(record);
		        	if(nTempCnt == 0) nTempCnt = insertMenu(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			    	nDTempCnt = deleteMenu(record);
			    	nDeleteCount += nDTempCnt;
			    	if(nTempCnt > 0) deleteAuthMenu(record);
		        }
		        nSaveCount = nSaveCount + nTempCnt;
		       // nDeleteCount 	= nDeleteCount 	+ nDTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 메뉴 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMenu(PosRecord record)
    {
    	logger.logInfo("updateMenu ============================");
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        int dmlcount = this.getDao("candao").update("tbda_mn_ua001", param);
        
        logger.logInfo("updateMenu ============================");
        return dmlcount;
    }

    /**
     * <p> 메뉴 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertMenu(PosRecord record) 
    {
    	logger.logInfo("insertMenu ============================");
        PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"));
        param.setValueParamter(i++, record.getAttribute("MN_NM"));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"));
        param.setValueParamter(i++, record.getAttribute("URL"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        int dmlcount = this.getDao("candao").update("tbda_mn_ia001", param);
        
        logger.logInfo("insertMenu ============================");
        return dmlcount;
    }



    
    /**
     * <p> 메뉴 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteMenu(PosRecord record)
    {
    	logger.logInfo("========== deleteMenu =======================");
    	logger.logInfo("========== deleteMenu =======================");
    	logger.logInfo("========== deleteMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        
        int dmlcount = this.getDao("candao").update("tbda_mn_da001", param);
        return dmlcount;
    }

    
    /**
     * <p> 메뉴권한 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAuthMenu(PosRecord record)
    {
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	logger.logInfo("========== deleteAuthMenu =======================");
    	
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("MN_ID"));
        
        int dmlcount = this.getDao("candao").update("tbda_mn_da002", param);
        return dmlcount;
    }    
}
