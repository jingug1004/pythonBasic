package snis.rbm.system.rsy4080.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveDutyList extends SnisActivity {
	public SaveDutyList(){	
		
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
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsDutyList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteDutyList(record);	            	
	            }
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveDutyList(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> �������� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveDutyList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// �����
        param.setValueParamter(i++, record.getAttribute("DUTY_DT"));		// �������
        param.setValueParamter(i++, record.getAttribute("SEQ"));			// ����
        param.setValueParamter(i++, record.getAttribute("JOB_TYPE"));		// ��������   
        param.setValueParamter(i++, record.getAttribute("JOB_TYPE_DTL"));	// �������α���	
        param.setValueParamter(i++, SESSION_USER_ID);						// �۾��� ���
        param.setValueParamter(i++, record.getAttribute("IMPTC"));			// �߿䵵	
        param.setValueParamter(i++, record.getAttribute("STATUS"));			// ����
        param.setValueParamter(i++, record.getAttribute("DUTY_TITLE"));		// �۾� ����
        param.setValueParamter(i++, record.getAttribute("DUTY_PLAN"));		// �۾� ��ȹ
        param.setValueParamter(i++, record.getAttribute("DUTY_DONE"));		// ó������
        param.setValueParamter(i++, record.getAttribute("DUE_DT"));			// ó������
        param.setValueParamter(i++, record.getAttribute("END_DT"));			// �Ϸ�����
        param.setValueParamter(i++, record.getAttribute("DUTY_DT"));		// �������
        
        int dmlcount = this.getDao("rbmdao").update("rsy4080_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteDutyList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );        
        param.setValueParamter(i++, record.getAttribute("DUTY_DT") );        
        param.setValueParamter(i++, record.getAttribute("SEQ") );        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4080_d01", param);

        return dmlcount;
    }
}
