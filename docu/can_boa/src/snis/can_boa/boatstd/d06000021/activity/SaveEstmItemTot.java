
/*================================================================================
 * �ý���			: �ĺ��� 
 * �ҽ����� �̸�	: snis.can.system.d06000021.activity.SaveViolCd.java
 * ���ϼ���		: �ĺ��� �Ѱ���ǥ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-05-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can_boa.boatstd.d06000021.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ��� �����ڵ� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveEstmItemTot  extends SnisActivity
{    
	
    public SaveEstmItemTot()
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
    	
    	PosDataset ds;
        
    	nDeleteCount = deleteEstmItemTo(ctx);
    	nSaveCount = insertEstmItemTo(ctx);
        
        Util.setDeleteCount(ctx, nDeleteCount   );
        Util.setSaveCount  (ctx, nSaveCount     );

    }
    
    
    /**
     * <p> �Ѱ���ǥ  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertEstmItemTo(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
		param.setValueParamter(i++, SESSION_USER_ID);       
//    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
//    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
			 	
		dmlcount = this.getDao("candao").insert("tbdn_total_d06000021_in001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p>�Ѱ���ǥ  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteEstmItemTo(PosContext ctx) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("racerPerioNo"));    
        param.setWhereClauseParameter(i++, ctx.get("round"));
        int dmlcount  = this.getDao("candao").update("tbdn_total_d06000021_dn001", param);
        	
        
        return dmlcount;
    }    
}



