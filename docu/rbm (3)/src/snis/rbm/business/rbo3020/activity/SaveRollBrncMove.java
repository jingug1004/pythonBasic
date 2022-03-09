package snis.rbm.business.rbo3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRollBrncMove extends SnisActivity {
	public SaveRollBrncMove(){
		
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

        sDsName = "dsRollBrncMove";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveRollBrncMove(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRollBrncMove(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


   
    /**
     * <p> 용지점간이동  입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRollBrncMove(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("OUT_BRNC_CD"));	//출고지점코드
        param.setValueParamter(i++, record.getAttribute("IN_BRNC_CD"));		//입고지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//기준년도
        param.setValueParamter(i++, record.getAttribute("TMS"));			//회차
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));		//용지구분

        param.setValueParamter(i++, record.getAttribute("MOVE_CNT"));		//이동수량
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//작성자
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자

        
        int dmlcount = this.getDao("rbmdao").update("rbo3020_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> 용지점간이동  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRollBrncMove(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("OUT_BRNC_CD"      ) );
        param.setValueParamter(i++, record.getAttribute("IN_BRNC_CD"      ) );
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ) );
        param.setValueParamter(i++, record.getAttribute("TMS"      ) );
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3020_d01", param);

        return dmlcount;
    }
}
