
/*================================================================================
 * �ý���			: ������� ������ ��������   ����
 * �ҽ����� �̸�	: snis.can.system.d02000021.activity.SavePerioExam.java
 * ���ϼ���		: ������� ������ �������� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-02-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000017.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������� ������ �������� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveMakeCycle  extends SnisActivity
{    
	
    public SaveMakeCycle()
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
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsCycAssem";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsCycAssem============>"+record);
	        }
        }
	        
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
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
        ds1   = (PosDataset) ctx.get("dsCycleAssem");
        nSize = ds1.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds1.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
             	 if ( (nTempCnt = updateCycAssem(record)) == 0 ) {
                  	nTempCnt = insertCycAssem(record);
                 }                	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteCycAssem(record);
             }
        }
		Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> ������ ��������  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCycAssem(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ ��������  �Է�   ============================");
                
        //PosParameter param = new PosParameter();  
   	    PosParameter param = null;
        int i = 0;
        int dmlcount = 0;
       
        String sRecAssem      = Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME")));
        String sRecAssemDelay = Util.trim(String.valueOf(record.getAttribute("ADD_TIME")));
       
       
        if(!sRecAssem.equals("")){
        	param = new PosParameter();
	        i=0;
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "201");
			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));			
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_cyc_assem_ib001", param);
        }
        
        if(!sRecAssemDelay.equals("")) {      
	    	param = new PosParameter();
	    	i = 0;
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setValueParamter(i++, "202");
			param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));			
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ADD_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_cyc_assem_ib001", param);
        }
        
        
        return dmlcount;
    }
    
    
    /**
     * <p> ������ ��������  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCycAssem(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        int dmlcount = 0;
 		
        String sRecAssem      = Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME")));
        String sRecAssemDelay = Util.trim(String.valueOf(record.getAttribute("ADD_TIME")));
             
        if(!sRecAssem.equals("")){
        	param = new PosParameter();   
        	i = 0; 
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ASSEM_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));        
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0; 
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("EXAM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").update("tbdb_cyc_assem_ub001", param);
        }
        
        if(!sRecAssemDelay.equals("")) {
			
        	param = new PosParameter();
        	i = 0; 
			param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("ADD_TIME"))));	
			param.setValueParamter(i++, record.getAttribute("TOT_SCR"));  
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0; 
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, "202");
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("DT")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").update("tbdb_cyc_assem_ub001", param);
        }     
          
        return dmlcount;
    }

    
    
    /**
     * <p> ������ ��������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCycAssem(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("DT" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        
        int dmlcount  = this.getDao("candao").update("tbdb_cyc_assem_db001", param);
        	
        
        return dmlcount;
    }    
}
