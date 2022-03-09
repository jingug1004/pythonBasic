
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
* 매핑하여 뷔반사항 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 임지헌
* @version 1.0
*/
public class DeleteTbdbViolAct  extends SnisActivity
{    
	
    public DeleteTbdbViolAct()
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
        int nTempCnt     = 0;
        sDsName = "dsTbdbViolAct";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	            PosRecord record = ds.getRecord(0);         
	            deleteTbdbViolAct_All(record);        
	               
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
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
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO")));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_viol_act_ib004", param);
        	
        
        return dmlcount;
    }    
    
}
