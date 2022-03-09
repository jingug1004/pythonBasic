
/*================================================================================
 * 시스템			: 정기시험 자전거 분해조립   관리
 * 소스파일 이름	: snis.can.system.d02000021.activity.SavePerioExam.java
 * 파일설명		: 정기시험 자전거 분해조립 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000017.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 정기시험 자전거 분해조립 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveMakeCycle  extends SnisActivity
{    
	
    public SaveMakeCycle()
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
        
        sDsName = "dsCycAssem";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsCycAssem============>"+record);
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

		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //종목구분 저장       
        ds1   = (PosDataset) ctx.get("dsCycleAssem");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
             	 if ( (nTempCnt = updateCycAssem(record)) == 0 ) {
                  	nTempCnt = insertCycAssem(record);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteCycAssem(record);
             }
        }
		Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> 자전거 분해조립  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertCycAssem(PosRecord record) 
    {
   	    logger.logInfo("==========================  자전거 분해조립  입력   ============================");
                
        //PosParameter param = new PosParameter();  
   	    PosParameter param = null;
        int i = 0;
        int dmlcount = 0;
       
        String sRecAssem      = Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME")));
        String sRecAssemDelay = Util.trim(String.valueOf(record.getAttribute("ADD_TIME")));
       
       
        if(!sRecAssem.equals("")){
        	param = new PosParameter();
	        i=0;
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "201");
			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));			
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_cyc_assem_ib001", param);
        }
        
        if(!sRecAssemDelay.equals("")) {      
	    	param = new PosParameter();
	    	i = 0;
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "202");
			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));			
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ADD_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_cyc_assem_ib001", param);
        }
        
        
        return dmlcount;
    }
    
    
    /**
     * <p> 자전거 분해조립  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateCycAssem(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        int dmlcount = 0;
 		
        String sRecAssem      = Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME")));
        String sRecAssemDelay = Util.trim(String.valueOf(record.getAttribute("ADD_TIME")));
             
        if(!sRecAssem.equals("")){
        	param = new PosParameter();   
        	i = 0; 
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));        
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0; 
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").update("tbdb_cyc_assem_ub001", param);
        }
        
        if(!sRecAssemDelay.equals("")) {
			
        	param = new PosParameter();
        	i = 0; 
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ADD_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));  
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0; 
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, "202");
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").update("tbdb_cyc_assem_ub001", param);
        }     
          
        return dmlcount;
    }

    
    
    /**
     * <p> 자전거 분해조립  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteCycAssem(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("DT" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        
        int dmlcount  = this.getDao("candao").update("tbdb_cyc_assem_db001", param);
        	
        
        return dmlcount;
    }    
}
