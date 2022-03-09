/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02050030.activity.SaveRecordEnd.java
 * ���ϼ���		: ȸ���������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050030.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ȸ����������ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRecordEnd extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	
    public SaveRecordEnd()
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
    	int nSaveCount = updateRaceTms(ctx);

        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> ȸ��������� �� ȸ���� �۾� �̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceTms(PosContext ctx) 
    {
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
    	
        // ������� üũ
        String sStatCd = "";
        SaveProcess sp = new SaveProcess();        
        sStatCd = sp.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

        /*
		if(!"055".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "�����ݿ� ���°� �ƴմϴ�!!!\n���� ������ �Է��ϼ���.");
        		return 0;        		
        	}			
		}*/
		        

        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, null);
        param.setValueParamter(i++, "060");
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub002", param);

        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "060");
        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        //SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return dmlcount;
    }
}
