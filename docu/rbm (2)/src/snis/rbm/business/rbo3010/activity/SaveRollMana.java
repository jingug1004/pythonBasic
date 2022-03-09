/*================================================================================
 * 시스템			: 용지재고  관리
 * 소스파일 이름	: snis.rbm.business.rsy0010.activity.SaveRollMana.java
 * 파일설명		: 용지재고 관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbo3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveRollMana extends SnisActivity {
	public SaveRollMana(){
		
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

        sDsName = "dsRollMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveRollMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRollMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


   
    /**
     * <p> 용지재고  입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRollMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//년도
        param.setValueParamter(i++, record.getAttribute("TMS"));			//회차
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));		//용지구분
        param.setValueParamter(i++, record.getAttribute("MRA_USGE_CNT"));	//경정사용량
        
        param.setValueParamter(i++, record.getAttribute("CRA_USGE_CNT"));	//경륜사용량
        param.setValueParamter(i++, record.getAttribute("MAKE_STK_CNT"));	//제작입고
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, record.getAttribute("IP"));				//IP
        param.setValueParamter(i++, SESSION_USER_ID);						//작성자
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자

        int dmlcount = this.getDao("rbmdao").update("rbo3010_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> 용지재고 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRollMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ) );
        param.setValueParamter(i++, record.getAttribute("TMS"      ) );
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"      ) );
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3010_d01", param);

        return dmlcount;
    }
}
