/*================================================================================
 * �ý���			: ���� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * ���ϼ���		: ���⵵��  ���� ������ ���� �⵵�� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class CopyRptPrepSitu extends SnisActivity {
	
	public CopyRptPrepSitu(){}

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
    	
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, ctx.get("RACE_DAY"));           // 1. ������ 
        param.setValueParamter(i++, SESSION_USER_ID);               // 2. ���/������ ���  
        param.setValueParamter(i++, ctx.get("RACE_DAY"));           // 3. ������ 
        param.setValueParamter(i++, ctx.get("BRNC_CD"));            // 4. �����ڵ�
        param.setValueParamter(i++, ctx.get("MEET_CD"));            // 5. ���/���� ����(����ó�ڵ�)
        param.setValueParamter(i++, ctx.get("BRNC_CD"));            // 4. �����ڵ�
        
        nSaveCount = this.getDao("rbmdao").update("rbr1050_m02", param);
  
        Util.setSaveCount  (ctx, nSaveCount );
    }
}
