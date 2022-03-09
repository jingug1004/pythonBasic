/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020030.activity.SaveOrder.java
 * ���ϼ���		: �ֹ����� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06050060.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* �ֹ����� ���� ������ ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class ProcBringForward extends SnisActivity
{    
    public ProcBringForward()
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
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	//����� ����
        saveState(ctx);        
        
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	PosDataset ds;
        int nSize        = 0;
        String sBfYear   = (String)ctx.get("BF_YEAR");
        String sJobFlag  = (String)ctx.get("JOB_FLAG");
        
    	if ("PROC".equals(sJobFlag)) {
    		nSaveCount = ProcBF(sBfYear);
    	} else {
    		nSaveCount = CancelProcBF(sBfYear);
    	}
    	
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  

    /**
     * <p>�̿� ó��</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int ProcBF(String sBfYear) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sBfYear); 	
    	param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sBfYear);  
    	
        return this.getDao("boadao").update("e06050060_i01", param);
    }


    /**
     * <p>�̿� ��� ó��</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int CancelProcBF(String sBfYear) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sBfYear); 	
    	
        return this.getDao("boadao").update("e06050060_d01", param);
    }

}
