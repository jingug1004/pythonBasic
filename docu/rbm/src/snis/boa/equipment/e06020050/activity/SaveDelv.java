/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020050.activity.SaveDelv.java
 * 파일설명		: 출고목록을 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020050.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 출고목록에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 유재은 
* @version 1.0
*/
public class SaveDelv extends SnisActivity
{    
	private SaveDelvDetail saveDelvDtail = new SaveDelvDetail();
    public SaveDelv()
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
        saveDelvDtail.runActivity(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	int nDeleteCountd = 0; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutOrderList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteDelv(record);  
            	nDeleteCountd = nDeleteCountd + updatePartsNowUnitCntWithDelvSeq(record);
            	deleteDelvPartsNowWithDelvSeq(record);
            }else{
            	nSaveCount = nSaveCount + mergeDelv(record);
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * <p>출고 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeDelv(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("DELV_SEQ" ));
        param.setValueParamter(i++, record.getAttribute("DELV_DT"));
        param.setValueParamter(i++, record.getAttribute("RECEPT_ID"));
        param.setValueParamter(i++, record.getAttribute("OUT_ID"));
        param.setValueParamter(i++, record.getAttribute("DELV_DESC"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        //param.setValueParamter(i++, null);        
        
        return this.getDao("boadao").update("tbef_delv_mf001", param);
    }
    
    /**
     * <p>출고 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteDelv(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("DELV_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_delv_df001", param);
    }
    
    

    /**
     * <p> 출고 마스터 삭제시 부품테이블 현재고 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePartsNowUnitCntWithDelvSeq(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("DELV_SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("DELV_SEQ"));
	        
        return this.getDao("boadao").update("tbef_delv_parts_uf701", param);
    }
    
    //마스터 삭제시 Detail삭제 
    protected int deleteDelvPartsNowWithDelvSeq(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("DELV_SEQ"));
	        
        return this.getDao("boadao").update("tbef_delv_parts_df101", param);
    }
    
    
}
