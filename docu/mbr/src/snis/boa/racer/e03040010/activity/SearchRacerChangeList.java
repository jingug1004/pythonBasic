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

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �λ󼱼������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SearchRacerChangeList extends SnisActivity
{    
    public SearchRacerChangeList()
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
     * <p> ���ͺ�Ʈ �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected void searchState(PosContext ctx)
    {
    	StringBuffer sbSql = new StringBuffer();
    	
    	sbSql.append("\n SELECT  /* searchRacerChangeList */ ");
    	sbSql.append("\n          TA .RACER_NO               ");
    	sbSql.append("\n        , TRM.NM_KOR                 ");
    	sbSql.append("\n        , TRM.RACER_GRD_CD           ");
    	sbSql.append("\n        , TRM.RACER_PERIO_NO         ");
    	sbSql.append("\n FROM     TBEB_ARRANGE      TA       ");
    	sbSql.append("\n        , TBEC_RACER_MASTER TRM      ");
    	sbSql.append("\n WHERE  TA.RACER_NO  = TRM.RACER_NO  ");
    	sbSql.append("\n AND    TA.STND_YEAR = ?             ");
    	sbSql.append("\n AND    TA.MBR_CD    = ?             ");
    	sbSql.append("\n AND    TA.TMS       = ?             ");
    	sbSql.append("\n AND    NVL(TA.EXCL_YN,'N') = ?      ");
    	
        String sDsName = "dsOutRacerArrange";
        PosDataset ds  = (PosDataset) ctx.get(sDsName);
        int nSize = 0;
        if ( ds != null ) 
        	nSize = ds.getRecordCount();

        if ( nSize > 0 ) {
        	sbSql.append("\n AND    TA.RACER_NO NOT IN ( ");
            for ( int i = 0; i < nSize; i++ ) 
            {
                PosRecord record = ds.getRecord(i);

                if ( i == 0 ) {
                	sbSql.append(      "'" + record.getAttribute("RACER_NO") + "' \n");
                } else { 
                	sbSql.append("," + "'" + record.getAttribute("RACER_NO") + "' \n");
                }
            }
        	sbSql.append("\n                                                 )             ");
        }
        
    	int i = 0;
    	PosParameter param = new PosParameter();
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        param.setWhereClauseParameter(i++, ctx.get("TMS")      );
        param.setWhereClauseParameter(i++, ctx.get("EXCL_YN")  );
	    
        PosRowSet rowsetRacer = this.getDao("boadao").findByQueryStatement(sbSql.toString(), param);

        String sResultKey = "dsOutRacerList";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
    }
}


