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
package snis.boa.equipment.e06050050.activity;


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
public class SaveOutList extends SnisActivity
{    
    public SaveOutList()
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
    	//출고내역 저장
        saveState(ctx);        
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        String sIoDt  = (String)ctx.get("OUT_DT");
        
        ds    = (PosDataset) ctx.get("dsOutSuppList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteOutSuppList(record);  
            } else {
            	nSaveCount = nSaveCount + mergeOutSuppList(sIoDt, record);
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  

    /**
     * <p>출고내역 입력/ 수정</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeOutSuppList(String sIoDt, PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        Double dAmount;        
        dAmount = Double.valueOf(record.getAttribute("OUT_QTY").toString()) * Double.valueOf(record.getAttribute("DANGA").toString()); 
        
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "4");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("OUT_QTY")); 
        param.setValueParamter(i++, dAmount); 
        param.setValueParamter(i++, record.getAttribute("ACCEPT_NM")); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sIoDt); 	
        param.setValueParamter(i++, "4");  
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));  
    	
        return this.getDao("boadao").update("e06050050_m01", param);
    }

    /**
     * <p>출고내역 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteOutSuppList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("OUT_DT")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_CD")); 
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return  this.getDao("boadao").update("e06050050_d01", param);
    }
}
