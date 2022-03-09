/*================================================================================
 * �ý���			: �޴��� ��ȣ �ٽ� ��������
 * �ҽ����� �̸�	: snis.rbm.business.rev4010.activity.UpdateHp.java
 * ���ϼ���		: �޴��� ��ȣ �ٽ� ��������
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2012-01-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateHp extends SnisActivity {
	
	public UpdateHp(){}

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
        
        nSaveCount = updateHp(sEvalYear, sEvalNum);	
        
        if( nSaveCount > 0 ) {
        	Util.setSvcMsg(ctx, "[ " + nSaveCount + " ]�� ���� �޴��� ��ȣ�� ����Ǿ����ϴ�.");
        } else {
        	Util.setSvcMsg(ctx, "�޴��� ��ȣ�� �ٲ� ���ڰ� �����ϴ�.");
        }

        Util.setSaveCount(ctx, nSaveCount  );
    }
    
    /**
     * <p> �޴��� ��ȣ �ٽ� �������� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateHp(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int nUpdateCnt = 0;
        String sEmpNo, sHp;
        
        param.setWhereClauseParameter(i++, sEvalYear);
        param.setWhereClauseParameter(i++, sEvalNum);
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev4010_s03", param);        
        
        PosRow pr[] = keyRecord.getAllRow();

        for( int k=0; k<pr.length; k++) {
        	sEmpNo = String.valueOf(pr[k].getAttribute("EMP_NO"));
        	sHp    = String.valueOf(pr[k].getAttribute("HP_NO"));
        	
        	nUpdateCnt += updateHphone(sEvalYear, sEvalNum, sEmpNo, sHp);
        }
        
        return nUpdateCnt;
    }
    
    /**
     * <p> �޴��� ��ȣ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateHphone(String sEvalYear, String sEvalNum, String sEmpNo, String sHpNo)
    {			
    	PosParameter param = new PosParameter();
        
    	int i = 0;
        
        param.setValueParamter(i++, sHpNo);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, sEvalYear);
        param.setWhereClauseParameter(i++, sEvalNum);
        param.setWhereClauseParameter(i++, sEmpNo);
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u04", param);
        	
        return dmlcount;
    }
}
