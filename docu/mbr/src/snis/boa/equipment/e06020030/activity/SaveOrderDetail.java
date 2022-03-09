/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020030.activity.SaveOrderDetail.java
 * 파일설명		: 주문서를 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020030.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 주문서에 대한 상세정보를 등록, 수정, 삭제 한다.
* @auther 유재은 
* @version 1.0
*/
public class SaveOrderDetail extends SnisActivity
{    
    public SaveOrderDetail()
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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	
    	int nSaveCountd   = 0; 
    	int nDeleteCountd = 0; 
    	
    	PosDataset dsd;
        int nSized        = 0;
        
        dsd    = (PosDataset) ctx.get("dsOutOrderPartsList");
        nSized = dsd.getRecordCount();
        for ( int j= 0; j < nSized; j++ ){
            PosRecord recordd = dsd.getRecord(j);
            if (recordd.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCountd = nDeleteCountd + deleteOrderParts(recordd);
            }else{
            	nSaveCountd = nSaveCountd + mergeOrderParts(recordd);
            }
        }
        Util.setSaveCount  (ctx, nSaveCountd     );
        Util.setDeleteCount(ctx, nDeleteCountd   );
    }
   

    /**
     * <p>주문서 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeOrderParts(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));  
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_CNT")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 			 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        
        return this.getDao("boadao").update("tbef_order_parts_mf001", param);
    }

    /**
     * <p>주문서 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteOrderParts(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ")); 
        param.setValueParamter(i++, record.getAttribute("PARTS_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR")); 
        
        return  this.getDao("boadao").update("tbef_order_parts_df001", param);
    }
}
