/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030035.activity.SaveArrangeNoti.java
 * ���ϼ���		: �����ּ��뺸���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030035.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �����ּ��뺸�� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @version 1.0
*/
public class SaveArrangeNotiList extends SnisActivity
{
    public SaveArrangeNotiList()
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
        int        nSize   = 0;
        String     sDsName = "";
	        
        sDsName = "dsOutArrangeNotiList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int nSaveCount   = 0; 
    	int nTempCnt     = 0; 

    	PosDataset ds;
        int nSize        = 0;
        String sDsName   = "";

        sDsName = "dsOutArrangeNotiList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
    	    // �����ּ��뺸���  ���
    	    for ( int i = 0; i < nSize; i++ ) 
    	    {
    	    	PosRecord record = ds.getRecord(i);
    	        if ( (record.getAttribute("STND_YEAR").equals(record.getAttribute("MAPPING_YEAR")))
    	          && (record.getAttribute("TMS").equals(record.getAttribute("MAPPING_TMS")))
    	          && (record.getAttribute("SEQ").equals(record.getAttribute("MAPPING_SEQ"))) ) {    
		            nTempCnt = updateArrangeNotiList(record, ctx);
		            nSaveCount = nSaveCount + nTempCnt;
    	        }
    	    }
        }

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> �����ּ��뺸��� ������Է»��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateArrangeNotiList(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMMENTARY") );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_noti_ub002", param);
        return dmlcount;
    }
    
}
