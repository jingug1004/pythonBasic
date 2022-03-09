/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.saveAlarm.java
 * ���ϼ���		: Ȯ������ ���� ����
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-31
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class saveAlarm extends SnisActivity
{    
    public saveAlarm()
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
        sDsName = "dsAlarm";
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
        int nTempCnt     = 0;

        sDsName = "dsAlarm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (i==0) {
	            	nDeleteCount = deleteAlarm(record);
	            }

		        if (((String)record.getAttribute("CHK")).equals("1")) {
		        	nTempCnt = insertAlarm(ctx, record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    
    /**
     * <p> Ȯ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAlarm(PosContext ctx, PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, (String)ctx.get("ALARM_CD"));
        param.setValueParamter(i++, record.getAttribute("RECV_ID"));
        param.setValueParamter(i++, (String)ctx.get("SUBJECT"));
        param.setValueParamter(i++, (String)ctx.get("CONTENT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
                        
        int dmlcount = this.getDao("rbmdao").insert("common_Alarm_List_Insert", param);
        
        return dmlcount;
    }
       
    
    /**
     * <p> Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAlarm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ALARM_CD" ));
        
        int dmlcount = this.getDao("rbmdao").delete("common_Alarm_List_Delete", param);
        
        return dmlcount;
    }
           
}

