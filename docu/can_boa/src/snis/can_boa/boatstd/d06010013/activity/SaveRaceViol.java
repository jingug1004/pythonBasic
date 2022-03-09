package snis.can_boa.boatstd.d06010013.activity;

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

public class SaveRaceViol  extends SnisActivity {
	
    public SaveRaceViol()
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
    	
    	 PosDataset ds;
         int        nSize   = 0;
         String     sDsName = "";
         
         sDsName = "dsRaceViol";
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
         }

         saveState(ctx);
         
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
        ds1   = (PosDataset) ctx.get("dsRaceViol");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updateRaceViol(record, ctx)) == 0 ) {
                  	nTempCnt = insertRaceViol(record, ctx);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
   			{
   				nDeleteCount = nDeleteCount + deleteRaceViol(record, ctx);
   			}
         }
        
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
    
    /**
     * <p> 출주 위반행위 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRaceViol(PosRecord record, PosContext ctx) 
    {
       PosParameter param = new PosParameter();       					
       int i = 0;
       Object object = record.getAttribute("VIOL_SEQ");

        param.setValueParamter(i++, ctx.get("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx.get("DAY_NO        ".trim()));
        param.setValueParamter(i++, ctx.get("ROUND         ".trim()));
        param.setValueParamter(i++, ctx.get("TEAM_NO       ".trim()));
        param.setValueParamter(i++, ctx.get("RACE_REG_NO   ".trim()));
        param.setValueParamter(i++, ctx.get("CAND_NO       ".trim()));
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_SEQ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_SCR")));	
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CNTNT")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdn_cand_race_viol_ins001", param);

		return dmlcount;
    }
    
    /**
     * <p> 출주 위반행위 수정  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRaceViol(PosRecord record, PosContext ctx) 
    {
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_SCR")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CNTNT")));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ROUND         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TEAM_NO       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACE_REG_NO   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("CAND_NO       ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VIOL_SEQ")));
		
		int dmlcount = this.getDao("candao").update("tbdn_cand_race_viol_ups001", param);

		return dmlcount;
    }
    
    /**
     * <p> 출주 위반행위 삭제</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
   protected int deleteRaceViol(PosRecord record, PosContext ctx) 
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, ctx.get("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ROUND         ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TEAM_NO       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("RACE_REG_NO   ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("CAND_NO       ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("VIOL_SEQ")));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_cand_race_viol_del001", param);
		 
		return dmlcount;
   }
}
