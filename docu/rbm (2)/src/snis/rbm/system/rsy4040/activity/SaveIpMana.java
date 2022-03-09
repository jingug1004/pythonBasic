/*================================================================================
 * �ý���			: IP ��
 * �ҽ����� �̸�	: snis.rbm.system.rsy0010.activity.SaveIpMana.java
 * ���ϼ���		: IP ��
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ������		: 2011-08-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.system.rsy4040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveIpMana extends SnisActivity {
	
	public SaveIpMana(){
		
		
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

        sDsName = "dsIpMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	/*
		        	if ( (nTempCnt = updateIpMana(record)) == 0 ) {
		        		nTempCnt = insertIpMana(record);
		        	}*/
		        	
		        	nTempCnt = saveIpMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteIpMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> IP �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertIpMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("IP"));			//IP
        param.setValueParamter(i++, record.getAttribute("USE_MAN"));	//�����
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD"));	//��񱸺�
        param.setValueParamter(i++, record.getAttribute("VLAN"));		//VLAN
        param.setValueParamter(i++, record.getAttribute("MANA_DEPT_NM"));	//��μ�
        param.setValueParamter(i++, record.getAttribute("LOC_CD"));		//��ġ�ڵ�
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, record.getAttribute("IP_GBN"));		//IP����
        param.setValueParamter(i++, record.getAttribute("USE_SELT"));	//��뱸��
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
        
     
        
        int dmlcount = this.getDao("rbmdao").update("rsy4040_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> IP ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateIpMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USE_MAN"      ));	//�����
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD"      ));	//��񱸺�
        param.setValueParamter(i++, record.getAttribute("VLAN"      ));		//VLAN
        param.setValueParamter(i++, record.getAttribute("MANA_DEPT_NM"      ));//��μ�
        param.setValueParamter(i++, record.getAttribute("LOC_CD"      ));	//��ġ�ڵ�
        param.setValueParamter(i++, record.getAttribute("RMK"      ));		//���
        param.setValueParamter(i++, record.getAttribute("IP_GBN"      ));	//IP����
        param.setValueParamter(i++, record.getAttribute("USE_SECT"      ));	//��뱸��
        param.setValueParamter(i++, SESSION_USER_ID);						//�ۼ���
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("IP" ));		//IP

        int dmlcount = this.getDao("rbmdao").update("rsy4040_u02", param);
        return dmlcount;
    }

    
    
    /**
     * <p> IP �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveIpMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("IP"));			//IP
        param.setValueParamter(i++, record.getAttribute("IP"));			//IP
        param.setValueParamter(i++, record.getAttribute("IP"));			//IP
        param.setValueParamter(i++, record.getAttribute("USE_MAN"));	//�����
        param.setValueParamter(i++, record.getAttribute("EQUIP_CD"));	//��񱸺�
        param.setValueParamter(i++, record.getAttribute("VLAN"));		//VLAN
        param.setValueParamter(i++, record.getAttribute("MANA_DEPT_NM"));	//��μ�
        
        
        param.setValueParamter(i++, record.getAttribute("LOC_CD"));		//��ġ�ڵ�
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, record.getAttribute("IP_GBN"));		//IP����
        param.setValueParamter(i++, record.getAttribute("USE_SECT"));	//��뱸��
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
        param.setValueParamter(i++, SESSION_USER_ID);					//������
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4040_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> IP  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteIpMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("IP"      ) );	

        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4040_d01", param);

        return dmlcount;
    }
}
