package snis.rbm.business.rbr1030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveGreenZone extends SnisActivity {
	public SaveGreenZone(){
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
        
        // 창구번호와의 매핑 정보 수정        
        sDsName = "dsWinNoMapping";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if(i==0) {
	            	deleteWinNoMapp(record);	
	            }
	            if (record.getType() != PosRecord.DELETE) {
	            	nSaveCount = nSaveCount + insertWinNoMapp(record);
	            }	
	        }	        
        }
        
     // 그린카드 단말기번호와의 매핑 정보 수정        
        sDsName = "dsMycatNoMapping";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if(i==0) {
	            	deleteMycatNoMapp(record);	            
	            }
	            if (record.getType() != PosRecord.DELETE) {
	            	nSaveCount = nSaveCount + insertMycatNoMapp(record);
	            }
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> 발매기 매핑정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteWinNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));		//지점코드
        
        int dmlcount = this.getDao("rbmdao").update("rbr1030_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> 그린카드 단말기번호 매핑정보 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteMycatNoMapp(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));		//지점코드
        
        int dmlcount = this.getDao("rbmdao").update("rbr1030_d03", param);

        return dmlcount;
    }

    /**
     * <p> 발매기 매핑정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertWinNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("WIN_NO"));		//창구번호
        param.setValueParamter(i++, record.getAttribute("GRN_ZN_CD"));	//장소구분
        param.setValueParamter(i++, SESSION_USER_ID);					//입력자 사번        
         
        int dmlcount = this.getDao("rbmdao").update("rbr1030_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 그린카드 단말기번호 매핑정보 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertMycatNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("CHNL_CD"));	//단말/앱구분
        param.setValueParamter(i++, record.getAttribute("DEV_NM"));		//단말번호
        param.setValueParamter(i++, record.getAttribute("GRN_ZN_CD"));	//장소구분
        param.setValueParamter(i++, SESSION_USER_ID);					//입력자 사번        

         
        int dmlcount = this.getDao("rbmdao").update("rbr1030_i03", param);
        
        return dmlcount;
    }
    
}
