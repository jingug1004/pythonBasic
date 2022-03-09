/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03040010.activity.SaveEquipmentUnConfirm.java
 * ���ϼ���		: ����/��Ʈ���� Ȯ�����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-05-14
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �λ󼱼������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveEquipmentUnConfirm extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	
    public SaveEquipmentUnConfirm()
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

    	updateRaceTms(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���ͺ�Ʈ �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        // ���ͺ�Ʈ ���� Ȯ��  ����
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
    	
        // ������� üũ
        String sStatCd = "";
        SaveProcess sp = new SaveProcess();        
        sStatCd = sp.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

		if(!"020".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "�����÷ �Ϸ���°� �ƴմϴ�!!!");
        		return 0;        		
        	}			
		}
		        

    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, null);
        param.setValueParamter(i++, "010");
        param.setValueParamter(i++, SESSION_USER_ID );
        
        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
	        
        int effectedRow = 0;
        effectedRow = this.getDao("boadao").update("tbeb_race_tms_ub002", param);
        
        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DUTY_CD"  , "003");
        hmProcess.put("WORK_CD"  , "020");
        hmProcess.put("PROG_STAT", "002");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        //SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return effectedRow;
    }
}


