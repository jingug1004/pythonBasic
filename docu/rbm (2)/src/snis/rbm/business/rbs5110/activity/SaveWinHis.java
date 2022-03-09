/*================================================================================
 * �ý���			: ������ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbb5110.activity.SaveWinHis.java
 * ���ϼ���		: �Ի󳻿� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs5110.activity;

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

public class SaveWinHis extends SnisActivity {
	
	public SaveWinHis(){}

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
        sDsName = "dsWinHis";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ����� ���ޱ���  ����
	            	nDeleteCount = nDeleteCount + deleteWinHis(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveWinHis(record);
			        
		        }
		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �Ի󳻿� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveWinHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        String maxWinHisSeq="";
        
        if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) { 
        	maxWinHisSeq = getMaxWinHisSeq(record);
        } else {
        	maxWinHisSeq = record.getAttribute("WIN_HIS_SEQ").toString();
        }
        
        
        param.setValueParamter(i++, maxWinHisSeq);	//�Ի󳻿�����
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));		//����ID
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CONTEST_SEQ"));	//��ȸ����
        param.setValueParamter(i++, record.getAttribute("RANK"));			//����
        param.setValueParamter(i++, record.getAttribute("ENTY_NM"));  		//��������   
        param.setValueParamter(i++, record.getAttribute("WIN_DY"));  		//�Ի�����   
        param.setValueParamter(i++, record.getAttribute("MRTN_RACE_TYPE")); //������ �ڽ� ����
        param.setValueParamter(i++, record.getAttribute("RMK"));  			//���   
        param.setValueParamter(i++, record.getAttribute("PRV_GRP_TYPE"));  	//���� ��ü ����   
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5110_m01", param);
        
        if (record.getAttribute("PAY_DY") != null &&
        	record.getAttribute("PAY_AMT") != null ) {
        	saveRwrdHis(record, maxWinHisSeq);
        }
        return dmlcount;
    }
    /**
     * <p> ���󳻿� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRwrdHis(PosRecord record, String maxWinHisSeq) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));		//����ID
        param.setValueParamter(i++, record.getAttribute("RWRD_SEQ"));		//���󳻿� ����
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CONTEST_TYPE_SEQ"));		//��ȸ�з�����
        param.setValueParamter(i++, null);									//��������ޱ���
        param.setValueParamter(i++, maxWinHisSeq);  						//�Ի󳻿�����   
        param.setValueParamter(i++, record.getAttribute("PAY_DY"));  		//��������
        param.setValueParamter(i++, record.getAttribute("PAY_AMT"));  		//����ݾ�   
        param.setValueParamter(i++, record.getAttribute("RMK"));  			//���   
        param.setValueParamter(i++, record.getAttribute("PRV_GRP_TYPE")); 	//���δ�ü ����
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5130_m01", param);
        
        return dmlcount;
    }
    
    protected String getMaxWinHisSeq(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;

        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs5110_s02",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
       
        rtnKey = String.valueOf(pr[0].getAttribute("MAX_WIN_HIS_SEQ"));

        return rtnKey;
    }
    
    
    /**
     * <p> �Ի󳻿� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteWinHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("WIN_HIS_SEQ"));	//�Ի󳻿�����
        
        int dmlcount = this.getDao("rbmdao").update("rbs5110_d01", param);

        return dmlcount;
    }


}