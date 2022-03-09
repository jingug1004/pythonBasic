/*================================================================================
 * �ý���			: ������ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs5010.activity.SaveEvntMana.java
 * ���ϼ���		: ������ ����ϰ� �����ϴ� ����� �����Ѵ�.
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs5010.activity;

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

public class SaveRacerInfo extends SnisActivity {
	
	public SaveRacerInfo(){}

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

        // �������� ����
        sDsName = "dsRacerList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ���� ���� ����
	            	nDeleteCount = nDeleteCount + deleteRacerInfo(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRacerInfo(record);
			        
		        }
		        
	        }	        
        }
        
        // ����������� ����
        sDsName = "dsRacerCntr";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ���� ���� ����
	            	nDeleteCount = nDeleteCount + deleteRacerCntr(record);
	            }		        

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
			        nSaveCount += saveRacerCntr(record);
			        
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
    protected int saveRacerInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //������ȣ
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	   		//�������
        param.setValueParamter(i++, record.getAttribute("RACER_NM"));		//������
        param.setValueParamter(i++, record.getAttribute("GAME_CD"));	   	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("RACER_TYPE"));  	//��������      
        param.setValueParamter(i++, record.getAttribute("RETIRE_GBN"));  	//��������      
        param.setValueParamter(i++, record.getAttribute("EMAIL"));  		//E-MAIL      
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));  		//�μ���      
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));  		//����      
        param.setValueParamter(i++, record.getAttribute("FLOC"));  			//����      
        param.setValueParamter(i++, record.getAttribute("FGRADE"));  		//����      
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));  		//��ȭ��ȣ      
        param.setValueParamter(i++, record.getAttribute("HP_NO"));  		//�ڵ�����ȣ          
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
        param.setValueParamter(i++, record.getAttribute("WORK_FRDT"));  	//�Ի�����          
        param.setValueParamter(i++, record.getAttribute("WORK_TODT"));  	//�������          
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //������ȣ
        
        int dmlcount = this.getDao("rbmdao").update("rbs5010_d01", param);

        return dmlcount;
    }

    /**
     * <p> ����������� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerCntr(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //������ȣ
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));	   	//���⵵
        param.setValueParamter(i++, record.getAttribute("CNTR_AMT"));		//���ݾ�
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_CD"));	   	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_RATE"));  	//��������      
        param.setValueParamter(i++, record.getAttribute("PAY_ADJ_AMT"));  	//�����ݾ�      
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs5010_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ����������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerCntr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RACER_ID"));	    //������ȣ
        param.setValueParamter(i++, record.getAttribute("CNTR_YEAR"));	    //���⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbs5010_d02", param);

        return dmlcount;
    }
    

}