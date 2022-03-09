/*================================================================================
 * �ý���			: �������  ����
 * �ҽ����� �̸�	: snis.rbm.business.rsy0010.activity.SaveRollMana.java
 * ���ϼ���		: ������� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbo3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveRollMana extends SnisActivity {
	public SaveRollMana(){
		
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

        sDsName = "dsRollMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveRollMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteRollMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


   
    /**
     * <p> �������  �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRollMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("TMS"));			//ȸ��
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));		//��������
        param.setValueParamter(i++, record.getAttribute("MRA_USGE_CNT"));	//������뷮
        
        param.setValueParamter(i++, record.getAttribute("CRA_USGE_CNT"));	//�����뷮
        param.setValueParamter(i++, record.getAttribute("MAKE_STK_CNT"));	//�����԰�
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, record.getAttribute("IP"));				//IP
        param.setValueParamter(i++, SESSION_USER_ID);						//�ۼ���
        param.setValueParamter(i++, SESSION_USER_ID);						//������

        int dmlcount = this.getDao("rbmdao").update("rbo3010_u01", param);
        
        return dmlcount;
    }
    
    
    
    
    /**
     * <p> ������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRollMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ) );
        param.setValueParamter(i++, record.getAttribute("TMS"      ) );
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"      ) );
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3010_d01", param);

        return dmlcount;
    }
}
