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

public class SaveEquip extends SnisActivity 
{
	public SaveEquip() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//      사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
    	        return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds1;
  
        int nSize1 = 0;

        String sDsName = "";

        //개인상태
        sDsName = "dsEquipList";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsEquipList------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			saveEquip(ctx); 
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
     protected void saveEquip(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsEquipList");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateEquip(record)) == 0 ) {
                   	nTempCnt = insertEquip(record);
                 }    	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteEquip(record);
             }
         }
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
        /**
         * <p> 개인상태 입력 </p>
         * @param   record	PosRecord 데이타셋에 대한 하나의 레코드
         * @return  dmlcount int insert 레코드 개수
         * @throws  none
         */
        protected int insertEquip(PosRecord record) 
        {
        	logger.logInfo("insertEquip start ============================");
            PosParameter param = new PosParameter();       					
            int i = 0;
            
            param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ENT_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STATE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
            param.setValueParamter(i++, SESSION_USER_ID);
            param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));

            int dmlcount = this.getDao("candao").insert("d08000001_ib001", param);
            
            logger.logInfo("insertEquip end ============================");
            return dmlcount;
        }
        
        /**
         * <p> 개인상태 갱신  </p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		update 레코드 개수
         * @throws  none
         */
        protected int updateEquip(PosRecord record) 
        {
        	logger.logInfo("updateEquip start============================");
        	PosParameter param = new PosParameter();       					
        	int i = 0;

        	param.setValueParamter(i++, Util.trim(record.getAttribute("ENT_DT")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("STATE")));
            param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
	   		param.setValueParamter(i++, SESSION_USER_ID);
	   		param.setValueParamter(i++, Util.trim(record.getAttribute("USER_GBN")));
	
	   		i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
           
	   		int dmlcount = this.getDao("candao").update("d08000001_ub001", param);
	
	   		logger.logInfo("updateEquip end ============================");
	   		return dmlcount;
        }
        
    
        /**
         * <p> 개인상태 삭제</p>
         * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
         * @return  dmlcount	int		delete 레코드 개수
         * @throws  none
         */
        protected int deleteEquip(PosRecord record) 
        {
       	 	logger.logInfo("deleteEquip start============================");
            PosParameter param = new PosParameter();    
            PosParameter param2 = new PosParameter();
            int i = 0;
                
	   		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
                                
            int dmlcount = this.getDao("candao").delete("d08000001_db001", param);
            
            i = 0;

            param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_GBN")));
            param2.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EQUIP_NO")));
            
            //정비내역 삭제
            this.getDao("candao").delete("d08000001_db005", param2);
            
            logger.logInfo("deleteEquip end============================");
            return dmlcount;
        }        
}
