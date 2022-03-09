package snis.can_boa.boatstd.d06000026.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveBodyInsp  extends SnisActivity {
	
    public SaveBodyInsp()
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
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	 PosDataset ds1;
    	 PosDataset ds2;
         int        nSize1   = 0;
         int        nSize2   = 0;
         String     sDsName = "";
         
         sDsName = "dsBodyInsp";
         if ( ctx.get(sDsName) != null ) {
        	ds1    = (PosDataset)ctx.get(sDsName);
 	        nSize1 = ds1.getRecordCount();
         }
         if(nSize1>0)saveState(ctx);
         
         
         sDsName = "dsBodyInspDt";
         nSize2 = 0;
         if ( ctx.get(sDsName) != null ) {
 	        ds2    = (PosDataset)ctx.get(sDsName);
 	        nSize2 = ds2.getRecordCount();
         }
         if(nSize2>0)saveStateDt(ctx);
         logger.logInfo("****************============================>>>>>>"+nSize2);
         
         return PosBizControlConstants.SUCCESS;
    }

    /**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 출주 위반행위
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveState(PosContext ctx)
  	{
  		int nSaveCount   = 0; 
		int nDeleteCount = 0; 

		PosDataset ds1;

		int nSize        = 0;
		int nTempCnt     = 0;
 	
        //종목구분 저장       
        ds1   = (PosDataset) ctx.get("dsBodyInsp");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateBodyInsp(record, ctx)) == 0 ) {
                  	nTempCnt = insertBodyInsp(record, ctx);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
   			{
   				nDeleteCount = nDeleteCount + deleteBodyInsp(record, ctx);
   			}
         }
        
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
  	protected void saveStateDt(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		PosDataset ds;
         	
		int nSize        = 0;
            
		//  저장       
		ds   = (PosDataset) ctx.get("dsBodyInspDt");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                 
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
			{     
		     	PosParameter param = new PosParameter();       					
		     	int j = 0;
				
		        param.setValueParamter(j++, Util.trim(record.getAttribute("DT")));     	

				j = 0;    
				param.setWhereClauseParameter(j++, ctx.get("racerPerioNo"));
				param.setWhereClauseParameter(j++, ctx.get("round"));

				int dmlcount = this.getDao("candao").update("tbdn_mesur_body_insp_un002", param);

				nSaveCount = nSaveCount + dmlcount;
			}

		}
		Util.setSaveCount  (ctx, nSaveCount);
	}
  	
    
    /**
     * <p> 출주 위반행위 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertBodyInsp(PosRecord record, PosContext ctx) 
    {
       PosParameter param = new PosParameter();       					
       int i = 0;
       
        param.setValueParamter(i++, ctx.get("racerPerioNo".trim()));
        param.setValueParamter(i++, ctx.get("round         ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_PRES".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ECG      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT     ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdn_mesur_body_insp_in001", param);

		return dmlcount;
    }
    
    /**
     * <p> 출주 위반행위 수정  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateBodyInsp(PosRecord record, PosContext ctx) 
    {
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_PRES")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ECG")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RSLT")));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, ctx.get("racerPerioNo".trim()));
        param.setWhereClauseParameter(i++, ctx.get("round         ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		
		int dmlcount = this.getDao("candao").update("tbdn_mesur_body_insp_un001", param);

		return dmlcount;
    }
    
    /**
     * <p> 출주 위반행위 삭제</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
   protected int deleteBodyInsp(PosRecord record, PosContext ctx) 
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, ctx.get("racerPerioNo".trim()));
        param.setWhereClauseParameter(i++, ctx.get("round         ".trim()));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_mesur_body_insp_dn001", param);
		 
		return dmlcount;
   }
}
