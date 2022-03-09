
/*================================================================================
 * �ý���			: ��� ���ݻ���  ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * ���ϼ���		: ��� ���ݻ��� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-25 
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can.cyclestd.d02000032.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���ݻ��� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbViolAct  extends SnisActivity
{    
	
    public SaveTbdbViolAct()
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
 	   	
        sDsName = "dsTbdbViolAct";
        if ( ctx.get(sDsName) != null ) {

	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsTbdbViolAct============>"+record);
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        //int nTempCnt     = 0;
        sDsName = "dsTbdbViolAct";

        ds   		 = (PosDataset) ctx.get(sDsName);
        nSize 		 = ds.getRecordCount();
	/*
	        for ( int i = 0; i < nSize; i++ ) {

	            PosRecord record = ds.getRecord(i);  
	            if (i == 0){   
		            deleteTbdbViolAct_All(record);        	            	
	            }
	           try {
	                	nTempCnt = insertTbdbViolAct(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            nSaveCount += nTempCnt;
	           		      
        }     // end if
*/  
        for ( int i = 0; i < nSize; i++ ){
	    PosRecord record = ds.getRecord(i);
		    switch (record.getType())
		    {
		        case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
		        	nDeleteCount = nDeleteCount + deleteTbdbViolAct(record);
		        	break;
		        case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
		        	nSaveCount = nSaveCount + insertTbdbViolAct(record);
		        	break;	
		        case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
		        	nSaveCount = nSaveCount + updateTbdbViolAct(record);
		        	break;	
		    }
        }

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
}
    
    
    /**
     * <p> ������ֱ����������Ÿ  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbViolAct(PosRecord record) 
    {
   	    logger.logInfo("==========================  ���ݻ���   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("insertTbdbViolAct======>");
        	
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("SEQ"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("WHEEL_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_PLC")));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO2"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REPORT_DT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_DESC")));
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_viol_act_ib001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> ���ݻ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
*/
    protected int updateTbdbViolAct(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("updateTbdbViolAct ������Ʈ======>");        	
            param.setValueParamter(i++, Util.trim(record.getAttribute("WHEEL_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_PLC")));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO1"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_JO2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HANG2"));
	        param.setValueParamter(i++, record.getAttribute("VIOL_HO2"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GBN")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REFE")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("REPORT_DT")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("VIOL_DESC")));
			param.setValueParamter(i++, SESSION_USER_ID);
			
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, record.getAttribute("SEQ"));
			dmlcount += this.getDao("candao").update("tbdb_viol_act_ib002", param);    
       
        return dmlcount;
    }
   
    
    
    /**
     * <p> ���ݻ��� �� ���ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbViolAct(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        int dmlcount  = this.getDao("candao").update("tbdb_viol_act_ib003", param);
        	
        
        return dmlcount;
    }    
    /**
     * <p> ���ݻ��� ��ü ���ڵ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbViolAct_All(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
        
        int dmlcount  = this.getDao("candao").update("tbdb_viol_act_ib004", param);
        	
        
        return dmlcount;
    }    
    

    
}
