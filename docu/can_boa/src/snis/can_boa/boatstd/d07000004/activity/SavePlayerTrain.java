/*================================================================================
 * �ý���		: �����Ʒ�����
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���		: ������
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SavePlayerTrain extends SnisActivity 
{
	public SavePlayerTrain() { }
	
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
        int    nSize = 0;
        String     sDsName = "";
   
        sDsName = "dsDiaryMst";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryAcdnt";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryDetl";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        sDsName = "dsDiaryAbs";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(sDsName + " : " + record);
	        }
        }
        
        saveState(ctx); 
        
        return PosBizControlConstants.SUCCESS; 
    }
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	����� ���������Ʒ���������Ÿ 
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 
     	PosDataset ds;
     	
        int nSize        = 0;
        int nTempCnt     = 0;
        String     sDsName = "";
        
        sDsName = "dsDiaryMst";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( (nTempCnt = updateDiaryMst(record, ctx)) == 0 ) {
	            	nTempCnt = insertDiaryMst(record, ctx);
	            }
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        sDsName = "dsDiaryAcdnt";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryAcdnt(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryAcdnt(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryAcdnt(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }

        sDsName = "dsDiaryDetl";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryDetl(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryDetl(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryDetl(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }

        sDsName = "dsDiaryAbs";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteDiaryAbs(record, ctx);
	 			}
	        }
	        
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
	 			  || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            if ( (nTempCnt = updateDiaryAbs(record, ctx)) == 0 ) {
		            	nTempCnt = insertDiaryAbs(record, ctx);
		            }
	 			}
	            
	            nSaveCount = nSaveCount + nTempCnt;
	        }
	        
        }
                  
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
     
     /**
      * <p> �����Ʒ����������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
   	protected int insertDiaryMst(PosRecord record, PosContext ctx) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, ctx   .get         ("DT"));
		param.setValueParamter(i++, record.getAttribute("WEATHER1"));
		param.setValueParamter(i++, record.getAttribute("WEATHER2"));
		param.setValueParamter(i++, record.getAttribute("WIND_DIRC1"));
		param.setValueParamter(i++, record.getAttribute("WIND_DIRC2"));
		param.setValueParamter(i++, record.getAttribute("WIND_SPD1"));
		param.setValueParamter(i++, record.getAttribute("WIND_SPD2"));
		param.setValueParamter(i++, record.getAttribute("TEMPT1"));
		param.setValueParamter(i++, record.getAttribute("TEMPT2"));
		param.setValueParamter(i++, record.getAttribute("HUMID1"));
		param.setValueParamter(i++, record.getAttribute("HUMID2"));
		param.setValueParamter(i++, record.getAttribute("WAT_TEMP1"));
		param.setValueParamter(i++, record.getAttribute("WAT_TEMP2"));
		param.setValueParamter(i++, record.getAttribute("WAT_LEV"));
		param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
		param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
		param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("EXC_PERS_NO"));		
		param.setValueParamter(i++, record.getAttribute("INSTR"));
		param.setValueParamter(i++, record.getAttribute("EDU_PLC"));
		param.setValueParamter(i++, record.getAttribute("IND_DESC"));
		param.setValueParamter(i++, record.getAttribute("ETC_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_mst_ib001", param);

		return dmlcount;
	}
     
     /**
      * <p> �����Ʒ����������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateDiaryMst(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("WEATHER1"));
        param.setValueParamter(i++, record.getAttribute("WEATHER2"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC1"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC2"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD1"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD2"));
        param.setValueParamter(i++, record.getAttribute("TEMPT1"));
        param.setValueParamter(i++, record.getAttribute("TEMPT2"));
        param.setValueParamter(i++, record.getAttribute("HUMID1"));
        param.setValueParamter(i++, record.getAttribute("HUMID2"));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP1"));
        param.setValueParamter(i++, record.getAttribute("WAT_TEMP2"));
        param.setValueParamter(i++, record.getAttribute("WAT_LEV"));
        param.setValueParamter(i++, record.getAttribute("TOT_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("CURR_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("ABS_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("EXC_PERS_NO"));
        param.setValueParamter(i++, record.getAttribute("INSTR"));
        param.setValueParamter(i++, record.getAttribute("EDU_PLC"));
        param.setValueParamter(i++, record.getAttribute("IND_DESC"));
        param.setValueParamter(i++, record.getAttribute("ETC_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, ctx   .get         ("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, ctx   .get         ("DT"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_mst_ub001", param);

		return dmlcount;
     }
     
     /**
      * <p> �����Ʒ�������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
    protected int insertDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
         PosParameter param = new PosParameter();       					
         int i = 0;			
		          
         //param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
         param.setValueParamter(i++, record.getAttribute("DT"));
         param.setValueParamter(i++, record.getAttribute("SEQ"));
         param.setValueParamter(i++, record.getAttribute("GBN"));         
         param.setValueParamter(i++, record.getAttribute("RACER_NO"));
         param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
         param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
         param.setValueParamter(i++, record.getAttribute("REPAIT_DESC"));
         param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);
         
         int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_acdnt_ib001", param);
         
         return dmlcount;
    }
     

     /**
      * <p> �����Ʒ�������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
         			
		param.setValueParamter(i++, record.getAttribute("GBN"));
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
		param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
		param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
		param.setValueParamter(i++, record.getAttribute("REPAIT_DESC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_acdnt_ub001", param);

		return dmlcount;
    }
     
 
     /**
      * <p> �����Ʒ�������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
    protected int deleteDiaryAcdnt(PosRecord record, PosContext ctx) 
    {
  		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_acdnt_db001", param);
		return dmlcount;
    }
     
     
     /**
      * <p> �����Ʒ������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
 	protected int insertDiaryDetl(PosRecord record, PosContext ctx) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("DT"));
		param.setValueParamter(i++, record.getAttribute("SEQ"));         
		param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
		param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
		param.setValueParamter(i++, record.getAttribute("EDU_DETL"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_detl_ib001", param);

		return dmlcount;
	}
     

     /**
      * <p> �����Ʒ������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateDiaryDetl(PosRecord record, PosContext ctx) 
     {
        PosParameter param = new PosParameter();       					
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_DETL"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    		
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_detl_ub001", param);

		return dmlcount;
     }
     
 
     /**
      * <p> �����Ʒ������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
    protected int deleteDiaryDetl(PosRecord record, PosContext ctx) 
    {
 		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_abs_db001", param);
		return dmlcount;
    }
     
     
     /**
      * <p> �����Ʒ�������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
    protected int insertDiaryAbs(PosRecord record, PosContext ctx) 
    {
         PosParameter param = new PosParameter();       					
         int i = 0;
          
         param.setValueParamter(i++, record.getAttribute("DT")); 
         param.setValueParamter(i++, record.getAttribute("SEQ"));         
         param.setValueParamter(i++, record.getAttribute("RACER_NO"));         
         param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
         param.setValueParamter(i++, record.getAttribute("ABS_EXC_GBN"));
         param.setValueParamter(i++, record.getAttribute("RMK"));         
         param.setValueParamter(i++, SESSION_USER_ID);  
         param.setValueParamter(i++, SESSION_USER_ID);  

         int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_abs_ib001", param);
         return dmlcount;
    }
     
     /**
      * <p> �����Ʒ�������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
    protected int updateDiaryAbs(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();       					
        int i = 0;
        
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("STR_END_TIME"));
        param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
        param.setValueParamter(i++, record.getAttribute("ABS_EXC_GBN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));   
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
							
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_abs_ub001", param);
		return dmlcount;
    }
     
 
     /**
      * <p> �����Ʒ�������� ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
    protected int deleteDiaryAbs(PosRecord record, PosContext ctx) 
    {
		PosParameter param = new PosParameter();       					
		int i = 0;
        
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));		
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		
		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_abs_db001", param);
		return dmlcount;
	}         
}
