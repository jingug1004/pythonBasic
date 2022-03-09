/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040060.activity.RegistHomePage.java
 * ���ϼ���		: ����ǥȨ���������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����ǥȨ�������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class RegistHomePage extends SnisActivity
{    
    public RegistHomePage()
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 

    	// ����ǥ����
    	nDeleteCount = deleteRaceDoc(ctx);
    	nSaveCount   = insertRaceDoc(ctx);

    	if ( nSaveCount == 0 ) {
    		Util.setSvcMsg(ctx, "���۾��� ���� �̷�� ���� �ʾҽ��ϴ�.");
    	}
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����ǥ Ȩ������ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceDoc(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_STAT_CD".trim()));

        int dmlcount = this.getDao("boadao").delete("tbeb_race_doc_db001", param);
        
        return dmlcount;
    }

    /**
     * <p> ����ǥ Ȩ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceDoc(PosContext ctx) 
    {
    	String sDayOrd = (String)ctx.get("DAY_ORD");
    	String sQueryId = "tbeb_race_doc_ib001";
    	
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        if ("3".equals(sDayOrd)) {
            param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
            param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD"));
        }
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        //param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        //param.setValueParamter(i++, ctx.get("STND_YEAR"));
        //param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        //param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        //param.setValueParamter(i++, ctx.get("STND_YEAR"));
        //param.setValueParamter(i++, ctx.get("TMS"      ));

        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        if ("3".equals(sDayOrd)) {
            param.setValueParamter(i++, ctx.get("STND_YEAR"));
            param.setValueParamter(i++, ctx.get("MBR_CD"   ));
            param.setValueParamter(i++, ctx.get("TMS"      ));
            param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
            param.setValueParamter(i++, ctx.get("STND_YEAR"));
            param.setValueParamter(i++, ctx.get("MBR_CD"   ));
            param.setValueParamter(i++, ctx.get("TMS"      ));
            param.setValueParamter(i++, ctx.get("DAY_ORD"  ));            
        }
        
        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"   ));
        param.setValueParamter(i++, ctx.get("TMS"      ));
        param.setValueParamter(i++, ctx.get("DAY_ORD"  ));
        if ("3".equals(sDayOrd)) {
        	sQueryId = "tbeb_race_doc_ib002";
        }
        int dmlcount = this.getDao("boadao").insert(sQueryId, param);
        
        
        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DAY_ORD"  , ctx.get("DAY_ORD"  ));
        hmProcess.put("DUTY_CD"  , "002");
        
        // Ȯ��, ��Ȯ����Ͽ���
        if ( ctx.get("ORGAN_STAT_CD").equals("001") ) {
            hmProcess.put("WORK_CD"  , "042");
        } else {
            hmProcess.put("WORK_CD"  , "045");
        }

        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
        
        return dmlcount;
    }
}
