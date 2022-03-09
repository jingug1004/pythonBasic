package snis.rbm.system.rsy4020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveInfraEquip extends SnisActivity {
	
	public SaveInfraEquip(){
		
		
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

        
        //������ ���
        sDsName = "dsInfraEquip";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveInfraEquip(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraEquip(record);	            	
	            }		        
	        }	        
        }

        
        
        //������ ��� SW ����        
        sDsName = "dsInfraMapp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveInfraEquipSw(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraEquipSw(record);	            	
	            }		        
	        }	        
        }
        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


  
    /**
     * <p> ������ ���  �Է�/���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveInfraEquip(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        
        param.setValueParamter(i++, record.getAttribute("DIST_NO"));	    //������ȣ
        param.setValueParamter(i++, record.getAttribute("TPE_1"));	     	//��з�
        param.setValueParamter(i++, record.getAttribute("TPE_2"));	     	//�ߺз�
        param.setValueParamter(i++, record.getAttribute("EQUIP_NM"));	    //����
        param.setValueParamter(i++, record.getAttribute("EQUIP_ID"));	    //���ID

        param.setValueParamter(i++, record.getAttribute("INSTALL_LOC"));    //��ġ��ġ
        param.setValueParamter(i++, record.getAttribute("DTL_LOC"));	    //������ġ
        param.setValueParamter(i++, record.getAttribute("ADOPT_DT"));	    //��������
        param.setValueParamter(i++, record.getAttribute("PRHS_AMT"));	    //���Աݾ�
        param.setValueParamter(i++, record.getAttribute("MODEL_NM"));	    //�𵨸�

        param.setValueParamter(i++, record.getAttribute("DTL_SPEC"));	    //���λ��
        param.setValueParamter(i++, record.getAttribute("CPU"));	     	//CPU
        param.setValueParamter(i++, record.getAttribute("Memory"));	     	//memory
        param.setValueParamter(i++, record.getAttribute("HDD"));	     	//HDD
        param.setValueParamter(i++, record.getAttribute("OS_TYP"));	     	//OSType

        param.setValueParamter(i++, record.getAttribute("OS"));		     	//OS
        param.setValueParamter(i++, record.getAttribute("IN_IP"));	     	//����IP
        param.setValueParamter(i++, record.getAttribute("OUT_IP"));	    	//�ܺ�IP
        param.setValueParamter(i++, record.getAttribute("ETC_IP"));	     	//��ŸIP
        param.setValueParamter(i++, record.getAttribute("CONN_TYP"));	    //��������

        param.setValueParamter(i++, record.getAttribute("CONSOLE_ID"));	    //�ܼ�����
        param.setValueParamter(i++, record.getAttribute("CONSOLE_PSWD"));   //�ܼ־�ȣ
        param.setValueParamter(i++, record.getAttribute("WEB_ID"));	     	//����������
        param.setValueParamter(i++, record.getAttribute("WEB_PSWD"));	    //�����پ�ȣ
        param.setValueParamter(i++, record.getAttribute("EQUIP_STR_WAY"));  //�����۹��

        param.setValueParamter(i++, record.getAttribute("EQUIP_END_WAY"));   //���������
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));	     //�������������
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));  //������������ڿ���ó
        param.setValueParamter(i++, record.getAttribute("ETC"));	     	//��Ÿ
        param.setValueParamter(i++, record.getAttribute("INST_ID"));	    //�ۼ���
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));	    //������




        int dmlcount = this.getDao("rbmdao").update("rsy4020_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ���������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteInfraEquip(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DIST_NO"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ���������SW���� �Է�/ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveInfraEquipSw(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          

        param.setValueParamter(i++, record.getAttribute("DIST_NO"));		//������ȣ
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"));		//SW������ȣ
        param.setValueParamter(i++, record.getAttribute("RMK"));			//��Ÿ
        param.setValueParamter(i++, SESSION_USER_ID);						//�ۼ�
        param.setValueParamter(i++, SESSION_USER_ID);						//������
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_u02", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ���������SW����  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteInfraEquipSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DIST_NO"      ) );			//������ȣ
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"      ) );		//SW������ȣ
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_d02", param);

        return dmlcount;
    }
}
