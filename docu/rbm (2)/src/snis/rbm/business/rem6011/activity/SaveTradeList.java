/*================================================================================
 * �ý���			: ���������ο� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem6011.activity.SaveTradeList.java
 * ���ϼ���		: ���������ο� ����
 * �ۼ���			: ���ֿ�
 * ����			: 1.0.0
 * ��������		: 2017-07-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem6011.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveTradeList extends SnisActivity {
	
	public SaveTradeList(){}

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
    	
        String sDsName  = "dsTradeList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {	
	            	
	            	nDeleteCount = nDeleteCount + deleteList(record);
		        	nSaveCount += saveList(record);
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);
	            }
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> ���������ο� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        //TBRC_T_TRADE_SUM
    	PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("TRADE_DATE"));		// ��������
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		// ����
        param.setValueParamter(i++, record.getAttribute("CNT"));			// �湮�ڼ�
        param.setValueParamter(i++, record.getAttribute("USER_ID"));		// �����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_i01", param);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();   
        int j = 0;

        raceparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));		// ��������
        raceparam.setValueParamter(j++, record.getAttribute("TRADE_DATE"));		// ��������
        raceparam.setValueParamter(j++, record.getAttribute("MEET_CD"));		// ���ֱ���
        raceparam.setValueParamter(j++, record.getAttribute("COMM_NO"));		// ����
        raceparam.setValueParamter(j++, record.getAttribute("MEET_CD"));		// ���ֱ���
        raceparam.setValueParamter(j++, record.getAttribute("CNT"));			// �湮�ڼ�
        
        int racecount = this.getDao("rbmdao").update("rem6011_i02", raceparam);
        
        //TBRC_T_TRADE
    	
        PosParameter tradeparam = new PosParameter();   
        int z = 0;

        tradeparam.setValueParamter(z++, record.getAttribute("TRADE_DATE"));	// ��������
        tradeparam.setValueParamter(z++, record.getAttribute("COMM_NO"));		// ����
        tradeparam.setValueParamter(z++, record.getAttribute("USER_ID"));		// �����ID(������)
        
        int tradecount = this.getDao("rbmdao").update("rem6011_i03", tradeparam);
        
        return dmlcount;
    }
        
    /**
     * <p> ���������ο�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
    	//TBRC_T_TRADE_SUM
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("TRADE_DATE" ));
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO" ));
        
        int dmlcount = this.getDao("rbmdao").update("rem6011_d01", param);
        
        //TBRC_T_TRADE_RACE_SUM
        PosParameter raceparam = new PosParameter();
        int j = 0;
        
        raceparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("COMM_NO" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("MEET_CD" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("TRADE_DATE" ));
        raceparam.setWhereClauseParameter(j++, record.getAttribute("MEET_CD" ));
        
        int racecount = this.getDao("rbmdao").update("rem6011_d02", raceparam);
        
        //TBRC_T_TRADE
        PosParameter tradeparam = new PosParameter();
        int z = 0;
        
        tradeparam.setWhereClauseParameter(z++, record.getAttribute("TRADE_DATE" ));
        tradeparam.setWhereClauseParameter(z++, record.getAttribute("COMM_NO" ));
        
        int tradecount = this.getDao("rbmdao").update("rem6011_d03", tradeparam);
        
		return dmlcount;
        
    }
    
}
