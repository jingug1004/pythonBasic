/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.MonthPayBacment.e06010010.activity.SaveMonthPayBac.java
 * ���ϼ���		: �������ޱ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �������ޱ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveMonthPayBac extends SnisActivity
{    
    public SaveMonthPayBac()
    {
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayBac");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveMonthPayBac(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �������ޱ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveMonthPayBac(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthPayBac(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthPayBac(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �������ޱ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMonthPayBac(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PMT_DT"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_PRIZ_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_ACT_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("PRIZ_RMK"));  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf001", param);
    }
    
    /**
     * ��� ���� ó�� 
     * @param stndYear	���س⵵
     * @param month	������޿�
     * @param monthPrizFinishYn	��ݸ������� 
     * @return
     */
    protected int updateMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthPrizFinishYn);  
        param.setValueParamter(i++, sPayDt);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
		   
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf010", param);
    }
    
    
    /**
     * ȸ�� ���� ó�� 
     * @param stndYear	���س⵵
     * @param month	������޿�
     * @param monthPrizFinishYn	��ݸ������� 
     * @return
     */
    protected int updateMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthActFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
	    return this.getDao("boadao").update("tbeg_month_pay_bac_uf011", param);
    }
    
    
    /**
     * ��Ÿ ���� ����  ó�� 
     * @param stndYear	���س⵵
     * @param month	������޿�
     * @param monthEtcFinishYn	��Ÿ ���� ���� ���� ���� 
     * @return
     */
    protected int updateMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, monthEtcFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);
	    return this.getDao("boadao").update("tbeg_month_pay_bac_uf021", param);
    }

    /**
     * <p>�������ޱ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertMonthPayBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 

        param.setValueParamter(i++, record.getAttribute("PMT_DT"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("PMT_DT"));
        
        param.setValueParamter(i++, record.getAttribute("MONTH_PRIZ_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("MONTH_ACT_FINISH_YN"));  
        param.setValueParamter(i++, record.getAttribute("PRIZ_RMK"));  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if001", param);
    }
    
    /**
     * �� ��� ���� ��� 
     * @param stndYear
     * @param month
     * @param monthPrizFinishYn
     * @return
     */
    public int saveMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthPrizFinish(stndYear, month, monthPrizFinishYn, sPayDt);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthPrizFinish(stndYear, month, monthPrizFinishYn, sPayDt);
    	}
        return effectedRowCnt;
    }
    
    
    /**
     * �� ȸ�� ���� ��� 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    public int saveMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthActFinish(stndYear, month, monthActFinishYn);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthActFinish(stndYear, month, monthActFinishYn);
    	}
        return effectedRowCnt;
    }
    
    
    
    /**
     * �� ȸ�� ���� ��� 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    public int saveMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateMonthEtcFinish(stndYear, month, monthEtcFinishYn);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertMonthEtcFinish(stndYear, month, monthEtcFinishYn);
    	}
        return effectedRowCnt;
    }
    
    /**
     * �� ��� ���� ��� 
     * @param stndYear
     * @param month
     * @param monthPrizFinishYn
     * @return
     */
    protected int insertMonthPrizFinish(String stndYear, Integer month, String monthPrizFinishYn, String sPayDt)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month);         
        param.setValueParamter(i++, sPayDt);  
        param.setValueParamter(i++, monthPrizFinishYn); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if010", param);
    }
    
    /**
     * �� ȸ�� ���� ��� 
     * @param stndYear
     * @param month
     * @param monthActFinishYn
     * @return
     */
    protected int insertMonthActFinish(String stndYear, Integer month, String monthActFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, monthActFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if011", param);
    }
    
    
    /**
     * ��Ÿ ���� ����  ��� ���� 
     * @param stndYear
     * @param month
     * @param monthEtcFinishYn
     * @return
     */
    protected int insertMonthEtcFinish(String stndYear, Integer month, String monthEtcFinishYn)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 
        param.setValueParamter(i++, month); 

        param.setValueParamter(i++, monthEtcFinishYn);  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_month_pay_bac_if021", param);
    }
}
