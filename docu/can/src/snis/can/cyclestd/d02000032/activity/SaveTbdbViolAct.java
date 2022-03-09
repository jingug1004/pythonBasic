
/*================================================================================
 * 시스템			: 경륜 위반사항  관리
 * 소스파일 이름	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * 파일설명		: 경륜 위반사항 관리
 * 작성자			: 임지헌
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-25 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/


package snis.can.cyclestd.d02000032.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 위반사항 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 임지헌
* @version 1.0
*/
public class SaveTbdbViolAct  extends SnisActivity
{    
	
    public SaveTbdbViolAct()
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
 	   	
        sDsName = "dsTbdbViolAct";
        if ( ctx.get(sDsName) != null ) {

	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbViolAct============>"+record);
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
        //int nTempCnt     = 0;
        sDsName = "dsTbdbViolAct";

        ds   		 = (PosDataset) ctx.get(sDsName);
        nSize 		 = ds.getRecordCount();
	/*
	        for ( int i = 0; i < nSize; i++ ) {

	            PosRecord record = ds.getRecord(i);  
	            if (i == 0){   
		            deleteTbdbViolAct_All(record);        	            	
	            }
	           try {
	                	nTempCnt = insertTbdbViolAct(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            nSaveCount += nTempCnt;
	           		      
        }     // end if
*/  
        for ( int i = 0; i < nSize; i++ ){
	    PosRecord record = ds.getRecord(i);
		    switch (record.getType())
		    {
		        case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		        	nDeleteCount = nDeleteCount + deleteTbdbViolAct(record);
		        	break;
		        case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		        	nSaveCount = nSaveCount + insertTbdbViolAct(record);
		        	break;	
		        case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
		        	nSaveCount = nSaveCount + updateTbdbViolAct(record);
		        	break;	
		    }
        }

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
}
    
    
    /**
     * <p> 경륜경주기록측정마스타  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertTbdbViolAct(PosRecord record) 
    {
   	    logger.logInfo("==========================  위반사항   입력   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("insertTbdbViolAct======>");
        	
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("SEQ"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WHEEL_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_PLC")));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO2"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REPORT_DT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_DESC")));
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_viol_act_ib001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> 위반사항  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
*/
    protected int updateTbdbViolAct(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("updateTbdbViolAct 업데이트======>");        	
            param.setValueParamter(i++, Util.trim(record.getAttribute("WHEEL_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_PLC")));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO2"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REPORT_DT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_DESC")));
			param.setValueParamter(i++, SESSION_USER_ID);
			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("SEQ"));
			dmlcount += this.getDao("candao").update("tbdb_viol_act_ib002", param);    
       
        return dmlcount;
    }
   
    
    
    /**
     * <p> 위반사항 한 레코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteTbdbViolAct(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        int dmlcount  = this.getDao("candao").update("tbdb_viol_act_ib003", param);
        	
        
        return dmlcount;
    }    
    /**
     * <p> 위반사항 전체 레코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteTbdbViolAct_All(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_viol_act_ib004", param);
        	
        
        return dmlcount;
    }    
    

    
}
