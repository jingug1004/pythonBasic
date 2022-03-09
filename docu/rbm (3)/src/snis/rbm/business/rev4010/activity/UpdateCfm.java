/*================================================================================
 * �ý���			: �򰡰��Ȯ��
 * �ҽ����� �̸�	: snis.rbm.business.rev4010.activity.UpdateGrade.java
 * ���ϼ���		: �򰡰��Ȯ��
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

public class UpdateCfm extends SnisActivity {
	
	public UpdateCfm(){}

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
        String sCfmYn    = (String)ctx.get("RSLT_CFM_YN");
        
    	updateCfm(sEvalYear, sEvalNum, sCfmYn);	

        Util.setSaveCount(ctx, nSaveCount  );
    }
    
    /**
     * <p> ������Ȯ�� </p>
     * @param   sEvalYear	�򰡳⵵
     * 			sEvalNum	��ȸ��
     * @return  dmlcount	int		update ����
     * @throws  none
     */
    protected int updateCfm(String sEvalYear, String sEvalNum, String sCfmYn) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sCfmYn);  			// �򰡰��Ȯ������
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sEvalYear);         // �򰡳⵵
        param.setValueParamter(i++, sEvalNum);          // ��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u02", param);
        
        return dmlcount;
    }
}
