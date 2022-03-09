package snis.rbm.business.rem3070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveStayEnd extends SnisActivity {

	
	public SaveStayEnd(){
		
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

        String sChk		 = "";
        sDsName = "dsStayEndMana";
        
        //01 : 수정, 02 :  마감취소 수정 
        String sJobGbn 		= (String) ctx.get("JOB_GBN");
        if(sJobGbn == null )sJobGbn = "";
        
        String sEndStat  = (String) ctx.get("STAY_END_STAT_CD");
        if(sEndStat == null )sEndStat = "";
        
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {

		        	if(sJobGbn.equals("01")){
		        		
		        		updateStayEnd(record);				//비고만 수정
		        	}else if(sJobGbn.equals("02")){
		        		
		        		sChk = (String) record.getAttribute("CHK");
		        		
		        		if(sChk.equals("1")){
		        			
		        			record.setAttribute("STAY_END_STAT_CD", sEndStat);
				        	nTempCnt = updateStayEndMana(record);
				        	
				        	if(nTempCnt>0){
					        	updateStayMana(record);		//수정가능한 일자를 세팅
				        	}
		        		}
		        	}else if(sJobGbn.equals("03")){
		        		
		        		sChk = (String) record.getAttribute("CHK");
		        		
		        		if(sChk.equals("1")){
		        			
		        			record.setAttribute("STAY_END_STAT_CD", sEndStat);
				        	nTempCnt = updateStayEndMana(record);
		        		}
		        	}


			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    
    /**
     * <p> 마감취소  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateStayEndMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        
        param.setValueParamter(i++, record.getAttribute("RMK"));						//비고
        param.setValueParamter(i++, record.getAttribute("STAY_END_STAT_CD" ));			//마감상태
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//경주일자
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//구분
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//요청일자 
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u01", param);
        return dmlcount;
    }

    
    /**
     * <p> 마감취소  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateStayMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);								//수정자
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//경주일자
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//구분
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//지점코드
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u02", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 마감정보 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateStayEnd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//경주일자
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//구분
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//요청일자 
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u03", param);
        return dmlcount;
    }

}
