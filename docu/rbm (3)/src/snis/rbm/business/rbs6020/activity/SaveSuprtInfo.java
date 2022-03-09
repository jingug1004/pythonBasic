/*================================================================================
 * �ý���			: ������û���� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs6020.activity.SaveEvntMana.java
 * ���ϼ���		: �⵿����� ����ϰ� �����ϴ� ����� �����Ѵ�.
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs6020.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveSuprtInfo extends SnisActivity {
	
	public SaveSuprtInfo(){}

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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0; 

        // ������û ����
        sDsName = "dsSuprtList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ������û ����  ����
	            	nDeleteCount = nDeleteCount + deleteSuptr_Info(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
	            	// 1�� 3ȸ �̻� ��û���� ���� ���� üũ
	            	if (CheckSchedule(ctx, record)) {
	            		return ;	            		
	            	}
	            	
	            	// �������� ��û�Ⱓ�� �� ��û������ �����ϴ� ��� üũ
	            	if (CheckDupBrnc(ctx, record)) {
	            		return ;
	            	}
	            		            	
	            	// �ڷ� ����
			        nSaveCount += saveSuptr_Info(record);			        
		        }		        
	        }	        
        }        
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
        
        return ;
    }

    /**
     * <p> ������û���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//������û ����
        param.setValueParamter(i++, record.getAttribute("STR_DT"));			//����������
        param.setValueParamter(i++, record.getAttribute("END_DT"));			//����������
        param.setValueParamter(i++, record.getAttribute("REQ_AREA"));		//�����о�
        param.setValueParamter(i++, record.getAttribute("REQ_RSN"));		//��������
        param.setValueParamter(i++, record.getAttribute("SUPTR_ID"));		//�⵿������
        param.setValueParamter(i++, record.getAttribute("STAT_CD"));		//����
        param.setValueParamter(i++, record.getAttribute("REQ_TEAM_CD"));	//��û�μ�
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));			//��û�� ���
        param.setValueParamter(i++, record.getAttribute("BRNC_APRV_ID"));	//���������� ���(������)
        param.setValueParamter(i++, record.getAttribute("BRNC_ARPV_DT"));	//���������Ͻ�
        param.setValueParamter(i++, record.getAttribute("MNGR_ARPV_ID"));	//�����μ� �����ڻ��
        param.setValueParamter(i++, record.getAttribute("MNGR_APRV_DT"));	//�����μ� ���������Ͻ�
        param.setValueParamter(i++, record.getAttribute("CNCL_RSN"));	    //��һ���
        param.setValueParamter(i++, record.getAttribute("CONDUCT"));	   	//���»���
        param.setValueParamter(i++, record.getAttribute("SUPRT_CONT"));	   	//��������
        param.setValueParamter(i++, record.getAttribute("BRNC_OPINN"));	   	//�μ��ǰ�
        param.setValueParamter(i++, record.getAttribute("RSLT_REPTR_ID"));	//������� �ۼ��� ���
        param.setValueParamter(i++, record.getAttribute("RSLT_APRV_ID"));	//���� ���������� ���
        param.setValueParamter(i++, record.getAttribute("RSLT_REPTR_DT"));	//���� �ۼ�����
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ������û���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSuptr_Info(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SEQ_NO"));	    // ������û���� ����
        
        int dmlcount = this.getDao("rbmdao").update("rbs6020_d01", param);

        return dmlcount;
    }

    private boolean CheckSchedule(PosContext ctx, PosRecord record) 
    {
    	PosParameter param = new PosParameter();
    	PosRowSet rowset;
		int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));
        
		rowset = this.getDao("rbmdao").find("rbs6020_s02", param);
		if (rowset.hasNext()) {		// ��û�Ⱓ�� ��¥���� 3���̻� �����ϴ� ��� ��û�Ұ�
			String sResultKey = "dsDupList";
			ctx.put(sResultKey, rowset);
			Util.addResultKey(ctx, sResultKey);	
			Util.setSvcMsgCode(ctx, "SNIS_RBM_E026");
			return true;
		}
		return false;
    }
    

    private boolean CheckDupBrnc(PosContext ctx, PosRecord record) 
    {
    	PosParameter param = new PosParameter();
    	PosRowSet rowset;
		int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));			//��û ������
        param.setWhereClauseParameter(i++, record.getAttribute("STR_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("END_DT"));
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_TEAM_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));
        
		rowset = this.getDao("rbmdao").find("rbs6020_s05", param);
		if (rowset.hasNext()) {		// ��û�Ⱓ�� ���� �μ����� ��û�� ������ �����ϴ� �� ����
			String sResultKey = "dsDupList";
			ctx.put(sResultKey, rowset);
			Util.addResultKey(ctx, sResultKey);	
			Util.setSvcMsgCode(ctx, "SNIS_RBM_E028");
			return true;
		}
		return false;
    }
}