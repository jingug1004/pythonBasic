/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.boa.common.util.SearchCode.java
 * ���ϼ���		: �ڵ���ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchCode extends PosActivity
{    
    public SearchCode()
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
        PosDataset ds = (PosDataset)ctx.get("dsInComCd");
        int size = ds.getRecordCount();
        for (int i = 0; i < size; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);

            getCommonCode(ctx, record);
        }
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �����ڵ� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @param   record	PosRecord	�ڵ�׷�����
     * @return  none
     * @throws  none
     */
    private void getCommonCode(PosContext ctx, PosRecord record)
    {
        PosParameter param = new PosParameter();
        PosRowSet    rowset;
        
        String       sResultKey = (String) record.getAttribute("DSNAME   ".trim());
        String       sCDGrpID   = (String) record.getAttribute("CD_GRP_ID".trim());
        String       sQueryID   = (String) record.getAttribute("QUERY_ID ".trim());
        
        // �ڵ�׷찪�� �����ϸ� 
        if ( !Util.nullToStr(sCDGrpID).equals("") ) {
        	int i = 0;
        	param.setWhereClauseParameter(i++, record.getAttribute("CD_GRP_ID".trim()));
        		
        	if(sQueryID.equals("sort_common_code")) {
        		sQueryID = "sort_common_code";
        	} else {
        		sQueryID = "common_code";
        	}
            
        }

        rowset = this.getDao("boadao").find(sQueryID, param);
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
    }
}

