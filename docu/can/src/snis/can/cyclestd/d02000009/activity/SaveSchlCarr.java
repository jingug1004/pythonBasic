/*================================================================================
 * �ý���			: �ĺ����з»��� ����
 * �ҽ����� �̸�	: snis.can.system.d02000009.activity.SaveSchlCarr.java
 * ���ϼ���		: �ĺ��� �з»��� ����
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
* �����Ͽ� �ĺ��� �з»��� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveSchlCarr  extends SnisActivity
{    
	
    public SaveSchlCarr()
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
        
        sDsName = "dsSchlCarr";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSchlCarr============>"+record);
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
        sDsName = "dsSchlCarr";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        PosRecord recHead = (PosRecord) headDs.getRecord(0);

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateSaveSchlCarr(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertSaveSchlCarr(record,recHead);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteSaveSchlCarr(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �ĺ��� �з»���  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveSchlCarr(PosRecord record,PosRecord recHead) 
    {
   	    logger.logInfo("==========================  �ĺ��� �з»���   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("SchlCarr======>");
        	
        	param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MTRIC_YY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_YY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_NM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MJR")));			
			param.setValueParamter(i++, SESSION_USER_ID);
        	param.setValueParamter(i++, Util.trim(recHead.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(recHead.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").insert("d02000009_ib003", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ��� �з»���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveSchlCarr(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("SchlCarr ������Ʈ======>");        	
        
        	param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_NM")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("MJR")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_GBN")));
        	param.setValueParamter(i++, SESSION_USER_ID); 
			param.setValueParamter(i++, Util.trim(record.getAttribute("SCHL_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MTRIC_YY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_YY")));
			
			i = 0;			
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			
			
			dmlcount += this.getDao("candao").update("d02000009_ub003", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> �ĺ��� �з»���  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveSchlCarr(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));    
        
        int dmlcount  = this.getDao("candao").update("d02000009_db003", param);
        	
        
        return dmlcount;
    }    
}

