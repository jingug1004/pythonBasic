/*================================================================================
 * �ý���			: ���� ���� �����ο� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem6011.activity.SaveTradeList.java
 * ���ϼ���		: ���� ���� �����ο� ����
 * �ۼ���			: ���ֿ�
 * ����			: 1.0.0
 * ��������		: 2017-07-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem6011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveTradeListKm extends SnisActivity {
	
	public SaveTradeListKm(){}

	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	int nDeleteCount = 0;
    	
    	
    	String sFlag = "N";	//�޼����� ����ڿ��� ����� ������ ���ϴ� Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
        String sDsName  = "dsTradeKm";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
	            	nDeleteCount = nDeleteCount + deleteList(record);
	            	nSaveCount += saveList(record);
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);
		        	nSaveCount += saveRaceList(record);
	            }
	            	
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ���� ���� �����ο� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        //TBRC_T_TRADE_KM_SUM
    	PosParameter param = new PosParameter();  
    	PosParameter perparam = new PosParameter();
    	PosParameter raceparam = new PosParameter();
    	
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("TRADE_DATE"));		// ��������
        param.setValueParamter(i++, record.getAttribute("CNT"));			// �湮�ڼ�
        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// �����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_i04", param);
        
        //TBRC_T_TRADE_KM
        int j = 0;
        perparam.setValueParamter(j++, record.getAttribute("USER_ID"));		// �����ID(������)
        perparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));	// ��������
        
        int dPercount = this.getDao("rbmdao").update("rem6011_i05", perparam);
        
        //TBRC_T_TRADE_RACE_SUM
        int z = 0;
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        
        int dracecount = this.getDao("rbmdao").update("rem6011_i08", raceparam);
        
        return dmlcount;
    }
        
    /**
     * <p> TBRC_T_TRADE_RACE_SUM ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRaceList(PosRecord record) 
    {
    	PosParameter raceparam = new PosParameter();

        //TBRC_T_TRADE_RACE_SUM
        int z = 0;
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        raceparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        
        int dracecount = this.getDao("rbmdao").update("rem6011_i08", raceparam);
        
        return dracecount;
    }
    
    /**
     * <p> ���� ���� �����ο�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
    	//TBRC_T_TRADE_KM_SUM
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("TRADE_DATE" ));
        int dmlcount = this.getDao("rbmdao").update("rem6011_d04", param);
        
        //TBRC_T_TRADE_KM
        PosParameter perparam = new PosParameter();
        int j = 0;
        
        perparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        int dPercount = this.getDao("rbmdao").update("rem6011_d05", perparam);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();
        int z = 0;
        
        raceparam.setWhereClauseParameter(z++, record.getAttribute("TRADE_DATE" ));
        int dracecount = this.getDao("rbmdao").update("rem6011_d08", perparam);
        
		return dmlcount;
    }
    
}
