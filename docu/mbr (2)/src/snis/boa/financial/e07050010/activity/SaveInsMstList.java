package snis.boa.financial.e07050010.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveInsMstList extends SnisActivity {

	public SaveInsMstList() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see snis.cra.common.util.SnisActivity#runActivity(com.posdata.glue.context.PosContext)
	 */
	public String runActivity(PosContext ctx) {
		// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
		saveState(ctx);

		return PosBizControlConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param ctx
	 */
	protected void saveState(PosContext ctx) {
		int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	= (PosDataset) ctx.get(sDsName);
	        nSize 	= ds.getRecordCount();
	        	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
		        	nDeleteCount = nDeleteCount + delete(record, ctx);	            	
	            }	
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		            
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = update(record, ctx)) == 0 ) {
		        		nTempCnt = insert(record, ctx);
		        		insertHist(record, ctx);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        }
	        }	 
		        

        }
        
        Util.setSaveCount(ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);     
	}

	 /**
     * <p> 등록 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insert(PosRecord record, PosContext ctx) {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("ENGM_NM"));
        param.setValueParamter(i++, record.getAttribute("ENGM_RES_NO"));
        param.setValueParamter(i++, record.getAttribute("INSU_CD"));
        param.setValueParamter(i++, record.getAttribute("INSU_NO"));
        param.setValueParamter(i++, record.getAttribute("INSU_AMT"));
        param.setValueParamter(i++, record.getAttribute("REG_DT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").insert("e07050010_i01", param);
    }
    
    protected int insertHist(PosRecord record, PosContext ctx) {
        PosParameter param = new PosParameter();

        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, "최초가입");
        param.setValueParamter(i++, record.getAttribute("REG_DT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").insert("e07050030_i01", param);
    }
   
    /**
     * <p> 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int update(PosRecord record, PosContext ctx){
        PosParameter param = new PosParameter();

        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ENGM_NM"));
        param.setValueParamter(i++, record.getAttribute("ENGM_RES_NO"));
        param.setValueParamter(i++, record.getAttribute("INSU_CD"));
        param.setValueParamter(i++, record.getAttribute("INSU_NO"));
        param.setValueParamter(i++, record.getAttribute("INSU_AMT"));
        param.setValueParamter(i++, record.getAttribute("ENGM_YN"));
        param.setValueParamter(i++, record.getAttribute("REG_DT"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));
        
        return this.getDao("boadao").update("e07050010_u01", param);
    }    
    
     
    /**
     * <p> 삭제 </p>
     * @param   record	PosRecord	
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int delete(PosRecord record, PosContext ctx) {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));
        return this.getDao("boadao").delete("e07050010_d01", param);
    }
}
