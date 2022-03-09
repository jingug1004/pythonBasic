/*================================================================================
 * �ý���			: �ĺ����ڰݸ������ ����
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d06000007.activity.SaveLicList.java
 * ���ϼ���		: �ĺ����ڰݸ������ ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-03-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can_boa.boatstd.d06000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ��� �ĺ����ڰݸ������ ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveLicList  extends SnisActivity
{    
	
    public SaveLicList()
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
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsLicList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
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
        sDsName = "dsLicList";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        PosRecord recHead = (PosRecord) headDs.getRecord(0);

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	                      
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateSaveLicList(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertSaveLicList(record,recHead);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteSaveLicList(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �ĺ��� �ڰݸ���  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveLicList(PosRecord record,PosRecord recHead) 
    {
   	     PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       	
        	param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        	param.setValueParamter(i++, recHead.getAttribute("CAND_NO"));
        	param.setValueParamter(i++, recHead.getAttribute("RACER_PERIO_NO"));
        	param.setValueParamter(i++, recHead.getAttribute("CAND_NO"));
        	param.setValueParamter(i++, record.getAttribute("CERT_NM"));
        	param.setValueParamter(i++, record.getAttribute("CERT_NO"));
        	param.setValueParamter(i++, record.getAttribute("REG_DT"));
        	param.setValueParamter(i++, record.getAttribute("CERT_GRD")); 
        	param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("d06000007_ib010", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ��� �ڰݸ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveLicList(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            param.setValueParamter(i++, record.getAttribute("CERT_NM"));
        	param.setValueParamter(i++, record.getAttribute("CERT_NO"));
        	param.setValueParamter(i++, record.getAttribute("REG_DT"));
        	param.setValueParamter(i++, record.getAttribute("CERT_GRD"));
        	param.setValueParamter(i++, SESSION_USER_ID); 
        	
			i = 0;			
			param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
			param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
			param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));				
			
			dmlcount += this.getDao("candao").update("d06000007_ub010", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> �ĺ��� �ڰݸ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveLicList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
              
        int dmlcount  = this.getDao("candao").update("d06000007_db010", param);
        	
        
        return dmlcount;
    }    
}



