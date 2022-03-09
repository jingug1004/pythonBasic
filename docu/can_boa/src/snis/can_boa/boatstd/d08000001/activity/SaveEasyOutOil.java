/*================================================================================
 * 시스템			: 후보생관리
 * 소스파일 이름	: snis.can_boa.boatstd.d09000001.activity.SaveHealth.java
 * 파일설명		: 건강기록
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d08000001.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/

public class SaveEasyOutOil extends SnisActivity 
{
	public SaveEasyOutOil() { }
	
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
    	
        PosDataset ds1;
  
        int nSize1 = 0;

        String sDsName = "";

        //개인상태
        sDsName = "dsOilEasyOut";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsOilEasyOut------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			SaveEasyOutOil(ctx); 
		}
		//saveOil(ctx);
		
        return PosBizControlConstants.SUCCESS;
    }
  
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소 : 개인상태
     * @return  none
     * @throws  none
     */
     protected void SaveEasyOutOil(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsOilEasyOut");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
        	
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
               record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	 nSaveCount += updateOutOil(record);
            }
            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
             	nDeleteCount = nDeleteCount + deleteOutOil(record);
            }
         }
         // 유류 사용량이 0으로 들어간 데이타를 삭제해야 되지 않나? 불필요한 데이타가 존재하게 됨...
         // 향후 삭제 루틴을 이부분에 추가해야 함(commented by TOMMY)
         // ...
         // ...
        
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
     }
     

	     /**
	      * <p> 출고내역 갱신  </p>
	      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	      * @return  dmlcount	int		update 레코드 개수
	      * @throws  none
	      */
        protected int updateOutOil(PosRecord record){
        	logger.logInfo("insertOutOil start ============================");
        	PosParameter param;       					
            int i = 0;
            int dmlcount = 0;
        	String sOutDt = Util.trim(record.getAttribute("OUT_DT"));
        	String arrQuant[][] = new String[2][4];
            
        	arrQuant[0][0] = Util.NVL(record.getAttribute("OUT_QUANT11"), "0");
        	arrQuant[0][1] = Util.NVL(record.getAttribute("OUT_QUANT12"), "0");
        	arrQuant[0][2] = Util.NVL(record.getAttribute("OUT_QUANT13"), "0");
        	arrQuant[0][3] = Util.NVL(record.getAttribute("OUT_QUANT14"), "0");
        	arrQuant[1][0] = Util.NVL(record.getAttribute("OUT_QUANT21"), "0");
        	arrQuant[1][1] = Util.NVL(record.getAttribute("OUT_QUANT22"), "0");
        	arrQuant[1][2] = Util.NVL(record.getAttribute("OUT_QUANT23"), "0");
        	arrQuant[1][3] = Util.NVL(record.getAttribute("OUT_QUANT24"), "0");
        	
            for (int j=0;j<2;j++) {
            	for (int k=0;k<4;k++) {
            		i = 0;
            		param = new PosParameter();
                    param.setValueParamter(i++, sOutDt);
                    param.setValueParamter(i++, String.format("%03d", j+1));
                    param.setValueParamter(i++, String.format("%03d", k+1));            
                    param.setValueParamter(i++, arrQuant[j][k]);
                    param.setValueParamter(i++, SESSION_USER_ID);
                    dmlcount += this.getDao("candao").insert("d08000001_iub001", param);
            	}
            }

            
            logger.logInfo("insertOutOil end ============================");
            return dmlcount;
        }
        
        /**
         * <p> 출고내역 삭제</p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		delete 레코드 개수
         * @throws  none
         */
        protected int deleteOutOil(PosRecord record){
       	 	logger.logInfo("deleteOutOil start============================");
            PosParameter param = new PosParameter();       					
            
            int i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("OUT_DT")));
                    
            int dmlcount = this.getDao("candao").delete("d08000001_iub002", param);
            
            logger.logInfo("deleteOutOil end============================");
            return dmlcount;
        }        
}
