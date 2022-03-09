/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040048.activity.SaveRaceGap.java
 * ���ϼ���		: ������/��������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040048.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���ְ��ݸ� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRaceGap extends SnisActivity
{    
	protected String sStndYear      = "";
	
    public SaveRaceGap()
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
    	
        PosDataset ds;
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutRaceGap");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
        }
        
        saveState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // �� ���ֺ� �������� ������ �����Ѵ�.
        ds    = (PosDataset) ctx.get("dsOutRaceGap");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ )
        {
            PosRecord record = ds.getRecord(i);
            if ( (nTempCnt = updateRaceGap(record)) == 0 ) {
            	nTempCnt = insertRaceGap(record);
            }

        	nSaveCount = nSaveCount + nTempCnt;
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���ְ��� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceGap(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STR_RACE_NO".trim()));
        param.setValueParamter(i++, record.getAttribute("END_RACE_NO".trim()));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO".trim()));
        
        int dmlcount = this.getDao("boadao").update("tbeb_race_gap_ub001", param);
        return dmlcount;
    }

    /**
     * <p> ���ְ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceGap(PosRecord record) 
    {
        // insert
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("STR_RACE_NO".trim()));
        param.setValueParamter(i++, record.getAttribute("END_RACE_NO".trim()));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbeb_race_gap_ib001", param);
        return dmlcount;
    }
}
