/*================================================================================
 * �ý���			: �������
 * �ҽ����� �̸�	: snis.rbm.business.rev4010.activity.UpdateResult.java
 * ���ϼ���		: �������
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-24
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateResult extends SnisActivity {
	
	public UpdateResult(){}

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
    	int nSaveCount = 0;

    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
        String sEvalNum  = (String)ctx.get("ESTM_NUM");
        String sOpenYn   = (String)ctx.get("OPEN_YN");
        
    	updateResult(sEvalYear, sEvalNum, sOpenYn);	

        Util.setSaveCount(ctx, nSaveCount  );
    }
    
    /**
     * <p> ������� </p>
     * @param   sEvalYear	�򰡳⵵
     * 			sEvalNum	��ȸ��
     * @return  dmlcount	int		update ����
     * @throws  none
     */
    protected int updateResult(String sEvalYear, String sEvalNum, String sOpenYn) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sOpenYn);  			// �򰡰��Ȯ������
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sEvalYear);         // �򰡳⵵
        param.setValueParamter(i++, sEvalNum);          // ��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u03", param);
        
        return dmlcount;
    }
}
