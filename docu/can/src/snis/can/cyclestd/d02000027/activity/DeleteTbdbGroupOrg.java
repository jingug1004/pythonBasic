
/*================================================================================
 * �ý���			: ������ֱ����������Ÿ ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * ���ϼ���		: ������ֱ����������Ÿ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-21 
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can.cyclestd.d02000027.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������ֱ����������Ÿ ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class DeleteTbdbGroupOrg  extends SnisActivity
{    
	
    public DeleteTbdbGroupOrg()
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

	        sDsName = "dsTbdbGroupOrg1" ;
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
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

	        sDsName = "dsTbdbGroupOrg1";
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();

	            PosRecord record = ds.getRecord(0);
            	nTempCnt = deleteTbdbRaceRecMst(record);
            	nTempCnt = deleteTbdbRaceRecDetl(record);
              
		        nSaveCount = nSaveCount + nTempCnt;
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
    protected int deleteTbdbRaceRecMst(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ����������Ÿ   ����   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_020", param);
     
        return dmlcount;
    } 
    

    /**
     * <p> ������ֱ������Detail  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbRaceRecDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ������Detail   ����   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
        	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_021", param);
     
        return dmlcount;
    }
    
    
}
