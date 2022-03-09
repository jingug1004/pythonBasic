package snis.rbm.system.rsy4050.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveWebStat extends SnisActivity {
	public SaveWebStat(){	
		
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

        sDsName = "dsWebStat";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveWebStat(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteWebStat(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> 홈피 방문정보 입력/저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveWebStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));			// 경주일자
        param.setValueParamter(i++, record.getAttribute("MEMBER_CNT"));			// 통합회원수
        param.setValueParamter(i++, record.getAttribute("CVISTR_CNT"));			// 경륜 홈페이지 방문자수   
        param.setValueParamter(i++, record.getAttribute("CMOBILE_VISTR_CNT"));	// 경륜 모바일홈페이지 방문자수	
        param.setValueParamter(i++, record.getAttribute("MVISTR_CNT"));			// 경정 홈페이지 방문자수
        param.setValueParamter(i++, record.getAttribute("MMOBILE_VISTR_CNT"));	// 경정 모바일홈페이지 방문자수	
        param.setValueParamter(i++, record.getAttribute("RACE_VISTR_CNT"));		// 사업본부 홈페이지 방문자수
        param.setValueParamter(i++, SESSION_USER_ID);							// 작성자
        param.setValueParamter(i++, SESSION_USER_ID);							// 수정자        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4050_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> 홈피 방문정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteWebStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_DAY") );        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4050_d01", param);

        return dmlcount;
    }
}
