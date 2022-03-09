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
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
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
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���� ��������
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
 	
        //���񱸺� ����       
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
     * <p> ���� �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
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
     * <p> ���� �������� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
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
     * <p> ���� �������� ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
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
