/*================================================================================
 * 시스템			: 장비내역  관리
 * 소스파일 이름	: snis.can.system.d03000002.activity.SaveEquipDetl.java
 * 파일설명		: 장비내역 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d03000002.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 장비내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveEquipDetl extends SnisActivity
{    
	
    public SaveEquipDetl()
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
        String     sDsName      = "";
        
        sDsName = "dsEquipDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsEquipDetl============>"+record);
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
        sDsName = "dsEquipDetl";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	                  {
	                      if ( (nTempCnt = updateSaveEquipDetl(record)) == 0 ) {
	                      	nTempCnt = insertSaveEquipDetl(record);
	                      }

	                  	nSaveCount = nSaveCount + nTempCnt;
	                  }
	                  
	                  if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	                  {
	                      // delete
	                  	nDeleteCount = nDeleteCount + deleteSaveEquipDetl(record);
	                  }         
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 장비내역 입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSaveEquipDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  장비내역 입력   ============================");
                
        PosParameter param = new PosParameter();       					
        int i = 0;
                
       
        param.setValueParamter(i++, (record.getAttribute("ENT_DD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("SUPR")));
        param.setValueParamter(i++, record.getAttribute("QTY"));        
        param.setValueParamter(i++, (record.getAttribute("STAT")));
        param.setValueParamter(i++, (record.getAttribute("RMK")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);        
        int dmlcount = this.getDao("candao").insert("tbdc_equip_detl_ic001", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 장비내역 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSaveEquipDetl(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPR"));
        param.setValueParamter(i++, record.getAttribute("QTY"));
        param.setValueParamter(i++, record.getAttribute("STAT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ENT_DD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_CD" ));
       
        int dmlcount = this.getDao("candao").update("tbdc_equip_detl_uc001", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p> 장비내역 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteSaveEquipDetl(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ENT_DD"));
        param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_CD"));
        
        int dmlcount  = this.getDao("candao").update("tbdc_equip_detl_dc001", param);
        	
        
        return dmlcount;
    }    
}
