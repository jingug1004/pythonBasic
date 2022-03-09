/*================================================================================
 * �ý���			: ������ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbb5130.activity.SaveRwrdHis.java
 * ���ϼ���		: �Ի󳻿� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs5130.activity;

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

public class SaveRwrdHis extends SnisActivity {
	
	public SaveRwrdHis(){}

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

        // ����� ���ޱ���  ����
        sDsName = "dsRwrdHis";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ����� ���ޱ���  ����
	            	nDeleteCount = nDeleteCount + deleteRwrdHis(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRwrdHis(record);
			        
		        }
		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �������� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRwrdHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));		//����ID
        param.setValueParamter(i++, record.getAttribute("RWRD_SEQ"));		//���󳻿� ����
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));  	//��ȸ�з�����
        param.setValueParamter(i++, record.getAttribute("RWRD_RULE_SEQ"));  	//�������ޱ��ؿ���   
        param.setValueParamter(i++, record.getAttribute("WIN_HIS_SEQ"));  	//�Ի󳻿�����   
        param.setValueParamter(i++, record.getAttribute("PAY_DY"));  		//��������
        param.setValueParamter(i++, record.getAttribute("PAY_AMT"));  		//����ݾ�   
        param.setValueParamter(i++, record.getAttribute("RMK"));  			//���
        param.setValueParamter(i++, record.getAttribute("PRV_GRP_TYPE"));   //����/��ü ����
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5130_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRwrdHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RWRD_SEQ"));	//�Ի󳻿�����
        
        int dmlcount = this.getDao("rbmdao").update("rbs5130_d01", param);

        return dmlcount;
    }


}