package snis.rbm.system.rsy4070.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveWeekDuty extends SnisActivity {
	public SaveWeekDuty(){	
		
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

        sDsName = "dsWeekDuty";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteWeekDuty(record);	            	
	            }	
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveWeekDuty(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> Ȩ�� �湮���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveWeekDuty(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("WEEK_NO"));			// ����ȸ����
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			// ��� Ȩ������ �湮�ڼ�   
        param.setValueParamter(i++, record.getAttribute("DONE"));	// ��� �����Ȩ������ �湮�ڼ�	
        param.setValueParamter(i++, record.getAttribute("PLAN"));			// ���� Ȩ������ �湮�ڼ�
        
        int dmlcount = this.getDao("rbmdao").update("rsy4070_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> Ȩ�� �湮���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteWeekDuty(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("WEEK_NO") );        
        param.setValueParamter(i++, record.getAttribute("EMP_NO") );        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4070_d01", param);

        return dmlcount;
    }
}
