/*
 * ================================================================================
 * �ý��� : ���� 
 * �ҽ����� �̸� : snis.rbm.business.rem4040.activity.SavePeriod.java 
 * ���ϼ��� : ���������ֱ� ���� 
 * �ۼ��� : ���缱
 * ���� : 1.0.0 
 * �������� : 2012.12.29
 * ������������ :
 * ���������� : 
 * ������������ :
 * =================================================================================
 */

package snis.rbm.business.rsm1140.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveEventConfirm extends SnisActivity {

	public SaveEventConfirm(){		
		
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
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsListDetl";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	        
	            nTempCnt = selectGrnCdPswd(record);
	            
	            if (nTempCnt == 0) {
	            	// ������ �����ϴ� �ڷᰡ ����(��������� �ٽ� �ϰų� ������ �����)
	    			throw new RuntimeException("�Է��� �׸�ī�� ��ȣ�� ��ġ���� �ʽ��ϴ�.\nȮ���� �ٽ� �Է��ϼ���!");
	            }
		        nSaveCount = nSaveCount + updateConfirm(record);
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }
    

    protected int selectGrnCdPswd(PosRecord record)
    {
        PosRowSet    rowset;
        int          nCnt = 0;
        
		int i = 0;
		PosParameter paramId = new PosParameter();
		paramId.setWhereClauseParameter(i++, record.getAttribute("EVENT_DT"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("MIN_SLIP_SEQ"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_NO"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_SEQ"));
		paramId.setWhereClauseParameter(i++, record.getAttribute("CARD_PWD"));
		
		rowset = this.getDao("rbmdao").find("rsm1140_s03", paramId);
		if (rowset.hasNext()) {	// ���������� �����ϴ� ��� ����� ���� ������ ������� ����
			PosRow rowUserId = rowset.next();
			nCnt = Integer.parseInt(rowUserId.getAttribute("CNT").toString());
		}
           	
    	return nCnt;
    }
    
    /**
     * <p> ��÷Ȯ�� ó�� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateConfirm(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        
    	int i = 0;
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���(�޸�)
        param.setValueParamter(i++, SESSION_USER_ID);						//������
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("EVENT_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("MIN_SLIP_SEQ"));
        param.setWhereClauseParameter(i++, record.getAttribute("CARD_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("CARD_SEQ"));

        int dmlcount = this.getDao("rbmdao").update("rsm1140_u01", param);
        return dmlcount;
    }    
}
