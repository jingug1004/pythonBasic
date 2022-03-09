/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrangeEnd.java
 * ���ϼ���		: �ּ����µ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ּ����¸� ����(�Է�/����)�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveArrangeEnd extends SnisActivity
{    

    public SaveArrangeEnd()
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
        updateRaceTms(ctx);
    }

    /**
     * <p> �ּ����� �� ȸ���� �۾� �̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("ARRANGE_CMPL_YN" ));
        param.setValueParamter(i++, ctx.get("RACE_TMS_STAT_CD"));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub002", param);
        
        if ( ctx.get("ARRANGE_CMPL_YN") != null ) {
            // �۾��α� �ۼ�
            //==============================================
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
            hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
            hmProcess.put("TMS"      , ctx.get("TMS"      ));
            hmProcess.put("DUTY_CD"  , "002");
            hmProcess.put("WORK_CD"  , "010");
            if ( ctx.get("ARRANGE_CMPL_YN").equals("Y") ) {
                hmProcess.put("PROG_STAT", "001");
	        } else if ( ctx.get("ARRANGE_CMPL_YN").equals("N") ) {
                hmProcess.put("PROG_STAT", "002");
	        }
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
            //==============================================
        }
        
        return dmlcount;
    }
}
