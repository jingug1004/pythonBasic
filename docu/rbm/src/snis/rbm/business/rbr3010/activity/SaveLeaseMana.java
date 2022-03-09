/*================================================================================
 * �ý���			: �Ӵ� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr3010.activity.SaveLeaseMana.java
 * ���ϼ���		: �Ӵ� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-08-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveLeaseMana extends SnisActivity {
	
	public SaveLeaseMana(){
		
		
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

        sDsName = "dsLeaseMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateLeaseMana(record)) == 0 ) {
		        		nTempCnt = insertLeaseMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteLeaseMana(record);	
		        	
		        	deleteLeaseBondAll(record);
		        	
	            }		        
	        }	        
        }


        //ä��         
        sDsName = "dsLeaseBond";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateLeaseBond(record)) == 0 ) {
		        		nTempCnt = insertLeaseBond(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteLeaseBond(record);	            	
	            }		        
	        }	        
        }

        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> �Ӵ�  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertLeaseMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));	//����������
        param.setValueParamter(i++, record.getAttribute("CNTR_END_DT"));	//�����������
        param.setValueParamter(i++, record.getAttribute("POST_CD"));		//�����ȣ
        param.setValueParamter(i++, record.getAttribute("ADDR"));			//�ּ�
        
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));		//���ּ�
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));	//�������
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));	//�������(��)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));	//�������
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));	//�������(��)

        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));	//��ġ�㰡����
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));			//��������
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));		//��������
        param.setValueParamter(i++, record.getAttribute("LEASE_RATE"));			//����������
        param.setValueParamter(i++, record.getAttribute("SQMT_AMT"));			//����ݾ�

        param.setValueParamter(i++, record.getAttribute("CNTR_TOT_AMT"));		//����Ѿ�
        param.setValueParamter(i++, record.getAttribute("LEASE"));				//����
        param.setValueParamter(i++, record.getAttribute("DEPOSIT"));			//������
        param.setValueParamter(i++, record.getAttribute("INTEREST_RATE"));		//������
        param.setValueParamter(i++, record.getAttribute("LEASE_COMP"));			//�Ӵ��ü

        param.setValueParamter(i++, record.getAttribute("LEASE_FIX_AMT"));		//�ӷ�����ݾ�
        param.setValueParamter(i++, record.getAttribute("CORP_AMT"));			//���ܱݾ�
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_AMT"));		//�ǹ��ֱݾ�
        param.setValueParamter(i++, record.getAttribute("CHAG_RATE"));			//�δ����
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_RANK"));		//ä��Ȯ������
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_OBJ"));		//ä��Ȯ������
        
        
        
        param.setValueParamter(i++, record.getAttribute("RMK"));				//���
        param.setValueParamter(i++, record.getAttribute("BOND_OBJ"));			//����������
        param.setValueParamter(i++, record.getAttribute("CORP_ESTMR"));			//���� �򰡾�ü 
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_ESTMR"));		//�ǹ��� �򰡾�ü

        
        param.setValueParamter(i++, record.getAttribute("M2_MANA_COST"));		//�������ʹ� ������
        param.setValueParamter(i++, record.getAttribute("MM_AVG_MANA_COST"));	//����հ�����
        param.setValueParamter(i++, record.getAttribute("WTR_HEAT_COST"));		//����������
        param.setValueParamter(i++, record.getAttribute("YEAR_AVG_MANA_COST"));	//����������
        param.setValueParamter(i++, record.getAttribute("MANA_CORP"));			//������ü��
        
      
        param.setValueParamter(i++, SESSION_USER_ID);
        
        

        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> �Ӵ�  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateLeaseMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("CNTR_END_DT"));	//�����������
        param.setValueParamter(i++, record.getAttribute("POST_CD"));		//�����ȣ
        param.setValueParamter(i++, record.getAttribute("ADDR"));			//�ּ�
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));		//���ּ�
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));	//�������(��)
        
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));	//�������(��)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));	//�������
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));	//�������(��)
        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));//��ġ�㰡����
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));		//��������
        
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));	//��������
        param.setValueParamter(i++, record.getAttribute("LEASE_RATE"));		//����������
        param.setValueParamter(i++, record.getAttribute("SQMT_AMT"));		//����ݾ�
        param.setValueParamter(i++, record.getAttribute("CNTR_TOT_AMT"));	//����Ѿ�
        param.setValueParamter(i++, record.getAttribute("LEASE"));			//����
        
        param.setValueParamter(i++, record.getAttribute("DEPOSIT"));		//������
        param.setValueParamter(i++, record.getAttribute("INTEREST_RATE"));	//������
        param.setValueParamter(i++, record.getAttribute("LEASE_COMP"));		//�Ӵ��ü
        
        param.setValueParamter(i++, record.getAttribute("LEASE_FIX_AMT"));	//�ӷ�����ݾ�
        param.setValueParamter(i++, record.getAttribute("CORP_AMT"));		//���ܱݾ�
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_AMT"));	//�ǹ��ֱݾ�
        param.setValueParamter(i++, record.getAttribute("CHAG_RATE"));		//�δ����
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_RANK"));	//ä��Ȯ������
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_OBJ"));	//ä��Ȯ������
        
        
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���        
        param.setValueParamter(i++, record.getAttribute("BOND_OBJ"));			//����������
        param.setValueParamter(i++, record.getAttribute("CORP_ESTMR"));			//���� �򰡾�ü 
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_ESTMR"));		//�ǹ��� �򰡾�ü

        
        param.setValueParamter(i++, record.getAttribute("M2_MANA_COST"));		//�������ʹ� ������
        param.setValueParamter(i++, record.getAttribute("MM_AVG_MANA_COST"));	//����հ�����
        param.setValueParamter(i++, record.getAttribute("WTR_HEAT_COST"));		//����������
        param.setValueParamter(i++, record.getAttribute("YEAR_AVG_MANA_COST"));	//����������
        param.setValueParamter(i++, record.getAttribute("MANA_CORP"));			//������ü��        
        
        param.setValueParamter(i++, SESSION_USER_ID);						//������
        
        
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));	//���������� 

        int dmlcount = this.getDao("rbmdao").update("rbr3010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> �Ӵ�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteLeaseMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));//����������
    
        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> �Ӵ� ä��  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertLeaseBond(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));		//����������
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));		//����������
        
        param.setValueParamter(i++, record.getAttribute("BANK_NM"));			//�����
        
        param.setValueParamter(i++, record.getAttribute("SECU_AMT"));			//�㺸�ݾ�
        param.setValueParamter(i++, record.getAttribute("ACPT_DT"));			//��������
      
        
      
        param.setValueParamter(i++, SESSION_USER_ID);
        

        int dmlcount = this.getDao("rbmdao").update("rbr3010_i02", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> �Ӵ� ä��  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateLeaseBond(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BANK_NM"));			//�����
        
        param.setValueParamter(i++, record.getAttribute("SECU_AMT"));			//�㺸�ݾ�
        param.setValueParamter(i++, record.getAttribute("ACPT_DT"));			//��������
        
        param.setValueParamter(i++, SESSION_USER_ID);						//������
        
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));	//������� 
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));	//�Ϸù�ȣ

        int dmlcount = this.getDao("rbmdao").update("rbr3010_u02", param);
        return dmlcount;
    }
    
    /**
     * <p> �Ӵ� ä�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteLeaseBond(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));//����������
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));//����������
    
        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> �Ӵ� ä�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteLeaseBondAll(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));//����������
            
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d03", param);

        return dmlcount;
    }

}
