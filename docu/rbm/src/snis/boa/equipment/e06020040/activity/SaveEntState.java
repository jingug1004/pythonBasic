/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020040.activity.SaveEntState.java
 * 파일설명		: 입고등록에 따른 주문입고상태를 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 입고등록에 따른 입고상태를 저장한다.(주문테이블)
* @auther 유재은 
* @version 1.0
*/
public class SaveEntState extends SnisActivity
{    
    public SaveEntState()
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
    	//사용자 정보 확인
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        savePartsNowCntState(ctx);
        saveEntState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * 부품 현재고 update
     * @param ctx
     */
    public void savePartsNowCntState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
        
    	PosDataset ds;
        int nSize        = 0;
    	
        ds    = (PosDataset) ctx.get("dsOutEnt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + updatePartsNowCnt(record);       	
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 입고 상태 update
     * @param ctx
     */
    public void saveEntState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
        
    	PosDataset ds;
        int nSize        = 0;
    	
        ds    = (PosDataset) ctx.get("dsOutOrder");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + updateEntState(record);       	
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 부품테이블 현재고 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
//    protected int updatePartsNowCnt(PosRecord record)
//    {
//        PosParameter param = new PosParameter();
//        int i = 0;
//        
//    	param.setValueParamter(i++, record.getAttribute("ENT_CNT")); 
//    	param.setValueParamter(i++, SESSION_USER_ID);
//    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
//    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
//        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
//        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
//    	    
//        return this.getDao("boadao").update("tbef_ent_uf003", param);
//    }
    protected int updatePartsNowCnt(PosRecord record)
    {
    	PosParameter param = new PosParameter();
    	int i = 0;
      
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
    	param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
    	param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
    	param.setValueParamter(i++, record.getAttribute("ENT_CNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
  	    
    	return this.getDao("boadao").update("tbef_ent_mf003", param);
    }
    
    
    protected int updateEntState(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ENT_STATE")); 
        param.setValueParamter(i++, SESSION_USER_ID);  
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  	
        return this.getDao("boadao").update("tbef_ent_uf002", param);
    }
}
