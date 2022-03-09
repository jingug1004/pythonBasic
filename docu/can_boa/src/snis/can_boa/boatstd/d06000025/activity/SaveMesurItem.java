package snis.can_boa.boatstd.d06000025.activity;

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

public class SaveMesurItem  extends SnisActivity {
	
    public SaveMesurItem()
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
         
         sDsName = "dsMesurItem";
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
        ds1   = (PosDataset) ctx.get("dsMesurItem");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if ( (nTempCnt = updatedsMesurItem(record, ctx)) == 0 ) {
                  	nTempCnt = insertdsMesurItem(record, ctx);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
   			{
   				nDeleteCount = nDeleteCount + deletedsMesurItem(record, ctx);
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
    protected int insertdsMesurItem(PosRecord record, PosContext ctx) 
    {
       PosParameter param = new PosParameter();       					
       int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ROUND         ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM          ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ASSGN_SCR_RATE".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK           ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdn_mesur_item_in001", param);

		return dmlcount;
    }
    
    /**
     * <p> ���� �������� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatedsMesurItem(PosRecord record, PosContext ctx) 
    {
		PosParameter param = new PosParameter();
		
		int i = 0;
		param.setValueParamter(i++, Util.trim(record.getAttribute("ASSGN_SCR_RATE")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ROUND         ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM".trim())));
		
		int dmlcount = this.getDao("candao").update("tbdn_mesur_item_un001", param);

		return dmlcount;
    }
    
    /**
     * <p> ���� �������� ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
   protected int deletedsMesurItem(PosRecord record, PosContext ctx) 
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ROUND         ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM          ".trim())));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_mesur_item_dn001", param);
		 
		return dmlcount;
   }
}
