
/*================================================================================
 * �ý���			: �ĺ��� �������� ����
 * �ҽ����� �̸�	: snis.can.system.d02000009.activity.SaveFmly.java
 * ���ϼ���		: �ĺ��� �������� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-02-27
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can.cyclestd.d02000009.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ��� �������� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveFmly  extends SnisActivity
{    
	
    public SaveFmly()
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
   	
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}  
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsFmly";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsFmly============>"+record);
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
    	
    	PosDataset headDs = (PosDataset)ctx.get("dsCandIdent");
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsFmly";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        PosRecord recHead = (PosRecord) headDs.getRecord(0);

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateSaveFmly(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertSaveFmly(record,recHead);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteSaveFmly(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �ĺ��� ��������  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveFmly(PosRecord record,PosRecord recHead) 
    {
   	    logger.logInfo("==========================  �ĺ��� ��������   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("Fmly======>");
        	
	        param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RELN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RES_AREA")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_JOB")));			
			param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").insert("d02000009_ib002", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ��� ��������  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveFmly(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("Fmly ������Ʈ======>");        	
        
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RELN")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("NM")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RES_AREA")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_JOB")));
        	param.setValueParamter(i++, SESSION_USER_ID); 
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
        	
			i = 0;			
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
			
			dmlcount += this.getDao("candao").update("d02000009_ub002", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> �ĺ�����������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveFmly(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));
        
        int dmlcount  = this.getDao("candao").update("d02000009_db002", param);
        	
        
        return dmlcount;
    }    
}
