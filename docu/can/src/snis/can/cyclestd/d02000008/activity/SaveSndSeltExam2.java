/*================================================================================
 * 시스템			: 2차 선발시험  필기   관리
 * 소스파일 이름	: snis.can.system.d02000008.activity.SaveSndSeltExam2.java
 * 파일설명		: 2차 선발시험 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 2차 선발시험 필기 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveSndSeltExam2  extends SnisActivity
{    
	
    public SaveSndSeltExam2()
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
   	
    	
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam2============>"+record);
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
        sDsName = "dsSndSeltExam2";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	  	|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) 
	            {
	  			    nTempCnt = insertSaveSndSeltExam2(record);
	  				nSaveCount = nSaveCount + nTempCnt;
	    	    }
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> 2차선발시험 필기 입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSaveSndSeltExam2(PosRecord record) 
    {
   	    logger.logInfo("==========================  2차 선발시험 필기   입력   ============================");
                
   	    PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;

    	param = new PosParameter();			
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, record.getAttribute("EXAM_CD"));
		param.setValueParamter(i++, record.getAttribute("ITEM_GBN_CD"));
		param.setValueParamter(i++, "101");
		param.setValueParamter(i++, record.getAttribute("CAND_NO"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));	
		param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
			 	
		dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        return dmlcount;
    }
}

