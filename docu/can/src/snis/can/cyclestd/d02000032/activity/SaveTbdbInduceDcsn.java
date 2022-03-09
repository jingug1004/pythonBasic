
/*================================================================================
 * 시스템			: 경륜 유도원기록  관리
 * 소스파일 이름	: snis.can.system.d02000032.activity.SaveTbdbInduceDcsn.java
 * 파일설명		: 경륜 유도원기록 관리
 * 작성자			: 임지헌
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-27   
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
* 매핑하여 유도원기록 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 임지헌
* @version 1.0
*/
public class SaveTbdbInduceDcsn  extends SnisActivity
{    
	
    public SaveTbdbInduceDcsn()
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
        	
        sDsName = "dsTbdbInduceDcsn";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbInduceDcsn============>"+record);
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
        sDsName = "dsTbdbInduceDcsn";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	         	
	            PosRecord record = ds.getRecord(i);  
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateTbdbInduceDcsn(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertTbdbInduceDcsn(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteTbdbInduceDcsn(record);
               }      
	            nSaveCount += nTempCnt;
	        } // end for     		      
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 유도원기록  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertTbdbInduceDcsn(PosRecord record) 
    {
   	    logger.logInfo("==========================  유도원기록   입력   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;  

	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RECORD_MAN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_CONT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE_DESC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("AVOID_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_CAUSE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_DEAL")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_EFFECT")));
		        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_induce_dcsn_002", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> 유도원기록  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
    */ 
    protected int updateTbdbInduceDcsn(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("updateTbdbInduceDcsn 업데이트======>");        	

	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE1_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE2_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE3_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE4_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE5_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE6_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE7_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE8_DIFF")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RECORD_MAN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_YN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_CONT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE_DESC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("AVOID_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_TIME")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_PLAC")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("ACDNT_CAUSE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_DEAL")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GUIDE_EFFECT")));    
   
			param.setValueParamter(i++, SESSION_USER_ID);

		//	i = 0;			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));

			dmlcount += this.getDao("candao").update("tbdb_induce_dcsn_003", param);    
       
        return dmlcount;
    }
   
    
    
    /**
     * <p> 유도원기록 한 레코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteTbdbInduceDcsn(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_induce_dcsn_004", param);
        	
        return dmlcount;
    }    
    
}
