/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040060.activity.SearchRaceSchedule.java
 * ���ϼ���		: ����ǥ��ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����ǥ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRaceSchedule extends SnisActivity
{    
	
    public SearchRaceSchedule()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	PosParameter param = new PosParameter();
    	int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);

        // ���� �� ��ŭ ���ֳ����� ��ȸ�Ѵ�.
        while ( rowset.hasNext() )
        {
        	PosRow rowTms = rowset.next();
        	ctx.put("RACE_NO", rowTms.getAttribute("RACE_NO"));
        	if ( ctx.get("DB_SERVER") != null && ctx.get("DB_SERVER").equals("1") ) {
        		searchRaceHomepage(ctx);
        	} else {
        		searchRace(ctx);
        	}
        }
	}
    
    /**
     * <p> ����ǥ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected void searchRace(PosContext ctx) 
    {
        PosParameter paramRace = new PosParameter();
        PosRowSet rowsetRace = null; 
        int i = 0;
        
        String sQueryId = "tbeb_organ_fb007";
        String sDayOrd = (String)ctx.get("DAY_ORD");
        if ("3".equals(sDayOrd)) sQueryId = "tbeb_organ_fb007_3";
        
        // ����ǥ ��ȸ
        // ����ǥ ��ȸ
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        //paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        //paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        //paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        //paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        if ("3".equals(sDayOrd)) {
        	paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        }
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"  ));
        
        rowsetRace = this.getDao("boadao").find(sQueryId, paramRace);

        String sResultKey = "dsOutRace" + Integer.parseInt((String) ctx.get("RACE_NO"  ));
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);
    }

    /**
     * <p> Ȩ�������� ��ϵ� ����ǥ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected void searchRaceHomepage(PosContext ctx) 
    {
        PosParameter paramRace = new PosParameter();
        PosRowSet rowsetRace = null; 
        int i = 0;
        
        // ����Ƚ�� ��ȸ
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"    ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"       ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"          ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_STAT_CD"));
        paramRace.setWhereClauseParameter(i++, ctx.get("RACE_NO"      ));
        
        rowsetRace = this.getDao("boadao").find("tbeb_race_doc_fb001", paramRace);

        String sResultKey = "dsOutRace" + Integer.parseInt((String) ctx.get("RACE_NO"  ));
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);    
    }
}
