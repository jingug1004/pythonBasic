package snis.rbm.business.rbr1016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveEquipFaci extends SnisActivity {
	public SaveEquipFaci(){
		
		
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
        
        
        
        sDsName = "dsEquipFaci";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == PosRecord.UPDATE
		          || record.getType() == PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveEquipFaci(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		       
	            if (record.getType() == PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipFaci(record);	            	
	            }	
	            
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    
    /**
     * <p> 장비및시설  입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipFaci(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//년도
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD"));	//장비코드
        param.setValueParamter(i++, record.getAttribute("FLOOR"));		//층수
        param.setValueParamter(i++, record.getAttribute("QTY"));		//층수
      		
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
        param.setValueParamter(i++, SESSION_USER_ID);					//수정자
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr1016_m01", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p>  장비및시설  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEquipFaci(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD") );	//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR") );	//년도
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD") );	//장비코드
        param.setValueParamter(i++, record.getAttribute("FLOOR") );		//층수
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr1016_d01", param);

        return dmlcount;
    }
}
