/*================================================================================
 * �ý���		    : 
 * �ҽ����� �̸�	: snis.can.system.d07000008.activity.SaveUsage.java
 * ���ϼ���		: �������丵�Ʒó��� �Է�/����/����
 * �ۼ���		    : ���缱
 * ����			: 1.0.0
 * ��������		: 2012-12-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/

public class SaveUsage extends SnisActivity 
{
	public SaveUsage() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String	sucess ���ڿ�
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsUsage";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        */
        saveState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں� �а���
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveState(PosContext ctx)
  	{
  		int nSaveCount   = 0;
  		int nDeleteCount = 0;

  		PosDataset ds;
  		
        String sDsName      = "";
  		int nSize 	= 0;

        sDsName = "dsUsage";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteUsage(record);
	 			}

	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {      
	            	 nSaveCount += mergeUpdateUsage(record);
	             }
	        }
        }

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
     /**
      * <p> �������丵�Ʒó��� ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int deleteUsage(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
     	param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
     	param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_TPE_CD"));
     	param.setWhereClauseParameter(i++, record.getAttribute("EQUIP_NO"));
     	param.setWhereClauseParameter(i++, record.getAttribute("USE_DT"));
     	param.setWhereClauseParameter(i++, record.getAttribute("USE_TMS"));
        
     	int dmlcount = this.getDao("candao").delete("d07000008_d01", param);
        
     	return dmlcount;
     }

     /**
      * <p> �������丵�Ʒó��� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int mergeUpdateUsage(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
     	param.setValueParamter(i++, record.getAttribute("MBR_CD"));
     	param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));
     	param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));
     	param.setValueParamter(i++, record.getAttribute("USE_DT"));
     	param.setValueParamter(i++, record.getAttribute("USE_TMS"));
     	param.setValueParamter(i++, record.getAttribute("USE_TIME"));
     	param.setValueParamter(i++, record.getAttribute("USE_TIME"));
        param.setValueParamter(i++, SESSION_USER_ID);
		
     	int dmlcount = this.getDao("candao").update("d07000008_m01", param);
        
     	return dmlcount;
     }
}
