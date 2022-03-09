/*================================================================================
 * 시스템			: 교육훈련일지
 * 소스파일 이름	: snis.can.system.d02000016.activity.SaveCfm.java
 * 파일설명		: 교육훈련일지 마감
 * 작성자			: 김영균
 * 버전			: 1.0.0
 * 생성일자		: 2008-06-12
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveCfm extends SnisActivity{

	public SaveCfm() { }
	
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
       	        
         sDsName = "dsCfm";
         
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

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // 교육개요 저장
        ds    = (PosDataset)ctx.get("dsCfm");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            
        	nTempCnt = updateCfg(record);
        	nSaveCount = nSaveCount + nTempCnt;
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
    
     protected int updateCfg(PosRecord record) 
     {
    	          
         PosParameter param = new PosParameter();       					
         int i = 0;
       
         param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
         param.setValueParamter(i++, record.getAttribute("DT"));
                                   
         int inscount = this.getDao("candao").insert("tbdb_trng_diary_ub005", param);
         
         return inscount;
     }     
}
