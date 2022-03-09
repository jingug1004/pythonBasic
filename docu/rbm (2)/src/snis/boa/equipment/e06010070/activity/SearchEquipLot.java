/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010060.activity.SearchEquipLot.java
 * ���ϼ���		: ����/��Ʈ ��÷ ����� ��ȸ�Ѵ�. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���س⵵, ȸ�� ������ �޾� �ش� ȸ���� ���� 
* ���� /��Ʈ ��� Ȯ�� ���θ� ��ȸ �Ѵ�. 
* @auther �輺�� 
* @version 1.0
*/
public class SearchEquipLot extends SnisActivity
{    
    public SearchEquipLot()
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
    	getMotLot(ctx);
    	getBoatLot(ctx);
    	getEquipDrwlt(ctx);
    	getPropellerLot(ctx);
    	getMaxRaceRegNo(ctx);
    	
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���� ��÷ ���  ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getMotLot(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;
        
        String	sResultKey = "dsOutMotLot";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        String	equipTpe   = "M";
        String	mbrCd   = (String) ctx.get("MBR_CD");
        
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, equipTpe);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, equipTpe);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        
        rowset = this.getDao("boadao").find("tbef_equip_lot_ff001", param);
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
    }
    /**
     * <p> ��Ʈ  ��÷ ���  ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void getBoatLot(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;
        
        String	sResultKey = "dsOutBoatLot";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        String	equipTpe   = "B";
        String	mbrCd   = (String) ctx.get("MBR_CD");
        
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, equipTpe);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, equipTpe);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, tms);
        
        rowset = this.getDao("boadao").find("tbef_equip_lot_ff002", param);
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
    }
    
    /**
     * 
     * @param ctx
     */
    private void getPropellerLot(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;
        
        String	sResultKey = "dsOutPropellerLot";
        String	stndYear   = (String) ctx.get("STND_YEAR");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        String	equipTpe   = "P";
        String	mbrCd   = (String) ctx.get("MBR_CD");
        
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);
        param.setWhereClauseParameter(i++, mbrCd);
        param.setWhereClauseParameter(i++, tms);
        param.setWhereClauseParameter(i++, equipTpe);
        
        rowset = this.getDao("boadao").find("tbef_equip_lot_ff003", param);
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
    }
    
    
    /**
     * <p> ��� ��÷ Ȯ�� ���� ��ȸ </p>
     * @param ctx
     * @return  none
     * @throws  none
     */
    private void getEquipDrwlt(PosContext ctx)
    {
        PosRowSet    rowset;
        String sResultKey = "dsOutEquipDrwlt";
        String stndYear   = (String) ctx.get("STND_YEAR");
        String mbrCd = (String) ctx.get("MBR_CD");
        Integer	tms =  new Integer((String) ctx.get("TMS"));
        rowset = (new SearchEquipDrwlt()).getEquipDrwltRwoSet(stndYear, mbrCd, tms);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
    
    private void getMaxRaceRegNo(PosContext ctx){
    	StringBuffer sbQuery = new StringBuffer();
    	sbQuery.append(" 	SELECT  MAX(A.RACE_REG_NO) MAX_RACE_REG_NO	"); 
    	sbQuery.append(" 	FROM    TBEB_ORGAN A                            ");                    
    	sbQuery.append(" 		 WHERE A.STND_YEAR = ?                      "); 
    	
    	PosParameter param = new PosParameter();
    	int i = 0;
    	param.setWhereClauseParameter(i++, ctx.get("STND_YEAR".trim()));
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String sResultKey = "MAX_RACE_REG_NO";
    	String sDefault = "0";
        ctx.put(sResultKey, Util.nullToStr((String)rowset.getAllRow()[0].getAttribute(sResultKey), sDefault));
        Util.addResultKey(ctx, sResultKey);
    }
}

