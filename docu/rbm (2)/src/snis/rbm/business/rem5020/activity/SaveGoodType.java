/*================================================================================
 * 시스템			: 지정좌석실 관리
 * 소스파일 이름	: snis.rbm.business.rem5020.activity.SaveGoodType.java
 * 파일설명		: 지정좌석실 상품 출고 관리
 * 작성자			: 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-08-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveGoodType extends SnisActivity {
	
	public SaveGoodType(){
		
		
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

        sDsName = "dsGoodType";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveGoodType(record);
		        }else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount += deleteGoodType(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> IP 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveGoodType(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("GOOD_TYPE_CD"));	//상품분류코드
        param.setValueParamter(i++, record.getAttribute("GOOD_TYPE_NM"));	//상품분류명
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, record.getAttribute("USE_YN"));			//사용유무(사용:Y)
        
        int dmlcount = this.getDao("rbmdao").update("rem5020_m01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> IP  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGoodType(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"      ) );	
        param.setValueParamter(i++, record.getAttribute("GOOD_TYPE_CD"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rem5020_d01", param);

        return dmlcount;
    }
}
