/*================================================================================
 * �ý���			: ��� ����
 * �ҽ����� �̸�	: snis.rbm.system.rsy0010.activity.SaveEquipMana.java
 * ���ϼ���		: ��� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-08-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.rbm.business.rbr2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEquipMana extends SnisActivity {
	
	public SaveEquipMana(){
		
		
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

        sDsName = "dsEquipMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateEquipMana(record)) == 0 ) {
		        		nTempCnt = insertEquipMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ���  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertEquipMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));	//�ڷᱸ���ڵ�
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));	//�ڷᱸ���ڵ�
        param.setValueParamter(i++, record.getAttribute("FLOOR_CD"));	//������

        param.setValueParamter(i++, record.getAttribute("DATA_A"));	//�ڷ� A
        param.setValueParamter(i++, record.getAttribute("DATA_B"));	//�ڷ� B
        param.setValueParamter(i++, record.getAttribute("DATA_C"));	//�ڷ� C
        param.setValueParamter(i++, record.getAttribute("DATA_D"));	//�ڷ� D
        param.setValueParamter(i++, record.getAttribute("DATA_E"));	//�ڷ� E
        param.setValueParamter(i++, record.getAttribute("DATA_F"));	//�ڷ� F
        param.setValueParamter(i++, record.getAttribute("DATA_G"));	//�ڷ� G
        param.setValueParamter(i++, record.getAttribute("DATA_H"));	//�ڷ� H
        param.setValueParamter(i++, record.getAttribute("DATA_I"));	//�ڷ� I
        param.setValueParamter(i++, record.getAttribute("DATA_J"));	//�ڷ� J
        param.setValueParamter(i++, record.getAttribute("DATA_K"));	//�ڷ� K

        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ�

        
        int dmlcount = this.getDao("rbmdao").update("rbr2010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ���  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("FLOOR_CD"));//������
        param.setValueParamter(i++, record.getAttribute("DATA_A"));	//�ڷ� A
        param.setValueParamter(i++, record.getAttribute("DATA_B"));	//�ڷ� B
        param.setValueParamter(i++, record.getAttribute("DATA_C"));	//�ڷ� C
        param.setValueParamter(i++, record.getAttribute("DATA_D"));	//�ڷ� D
        param.setValueParamter(i++, record.getAttribute("DATA_E"));	//�ڷ� E
        param.setValueParamter(i++, record.getAttribute("DATA_F"));	//�ڷ� F
        param.setValueParamter(i++, record.getAttribute("DATA_G"));	//�ڷ� G
        param.setValueParamter(i++, record.getAttribute("DATA_H"));	//�ڷ� H
        param.setValueParamter(i++, record.getAttribute("DATA_I"));	//�ڷ� I
        param.setValueParamter(i++, record.getAttribute("DATA_J"));	//�ڷ� J
        param.setValueParamter(i++, record.getAttribute("DATA_K"));	//�ڷ� K
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);      				//������
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("TYPE_CD"));	//�ڷ�з��ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		//����

        int dmlcount = this.getDao("rbmdao").update("rbr2010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ���  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));		//�ڷ�з��ڵ�
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//����
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr2010_d01", param);

        return dmlcount;
    }
}
