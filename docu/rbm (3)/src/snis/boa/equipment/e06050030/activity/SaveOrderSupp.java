/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020030.activity.SaveOrder.java
 * 파일설명		: 주문서를 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06050030.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 주문서에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 유재은 
* @version 1.0
*/
public class SaveOrderSupp extends SnisActivity
{    
    public SaveOrderSupp()
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
    	
    	//주문서 마스터 저장
        saveState(ctx);        
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutOrderSuppList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteOrder(record);  
            }else{
            	nSaveCount = nSaveCount + mergeOrder(record);            	
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    

    /**
     * <p>주문서 마스터 입력/ 수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("IO_DT")); 	
        param.setValueParamter(i++, record.getAttribute("IO_TYPE"));  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("JUMUN_NO")); 
        param.setValueParamter(i++, record.getAttribute("ORDER_CNT")); 
        param.setValueParamter(i++, record.getAttribute("AMOUNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("IO_DT")); 	
        param.setValueParamter(i++, record.getAttribute("IO_TYPE"));  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
    	
        return this.getDao("boadao").update("e06050030_m01", param);
    }

    /**
     * <p>주문내역 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("IO_DT")); 
        param.setValueParamter(i++, record.getAttribute("IO_TYPE")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_CD")); 
        param.setValueParamter(i++, record.getAttribute("SEQ"));  
        
        return  this.getDao("boadao").update("e06050030_d01", param);
    }
}
