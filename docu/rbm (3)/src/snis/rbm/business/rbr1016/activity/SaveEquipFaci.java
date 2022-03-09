package snis.rbm.business.rbr1016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveEquipFaci extends SnisActivity {
	public SaveEquipFaci(){
		
		
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
        
        
        
        sDsName = "dsEquipFaci";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == PosRecord.UPDATE
		          || record.getType() == PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveEquipFaci(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		       
	            if (record.getType() == PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipFaci(record);	            	
	            }	
	            
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    
    /**
     * <p> ���׽ü�  �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipFaci(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//�⵵
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD"));	//����ڵ�
        param.setValueParamter(i++, record.getAttribute("FLOOR"));		//����
        param.setValueParamter(i++, record.getAttribute("QTY"));		//����
      		
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
        param.setValueParamter(i++, SESSION_USER_ID);					//������
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr1016_m01", param);
        
        return dmlcount;
    }

    
    
    /**
     * <p>  ���׽ü�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipFaci(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD") );	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR") );	//�⵵
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD") );	//����ڵ�
        param.setValueParamter(i++, record.getAttribute("FLOOR") );		//����
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr1016_d01", param);

        return dmlcount;
    }
}
