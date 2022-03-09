/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.MonthPayment.e06010010.activity.SaveMonthPay.java
 * ���ϼ���		: ���� ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07020055.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.system.e01010220.activity.SaveProcess;

import com.posdata.glue.dao.vo.*;


/**
* ���� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveMonthPay extends SnisActivity
{    
	Integer inceTaxRate;
	Integer inhTaxRate;
    public SaveMonthPay()
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
    	//����Ȱ� ���� 
        saveState(ctx);
        
        //���� Update
        String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
        new SaveMonthPay().updateTax(stndYear, payMonth, SESSION_USER_ID);
        
        //���� update
        updateTmsMonthPayStat(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPay");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveMonthPay(record);
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * ��� ���� ���� Update
     * @param ctx
     */
    protected void updateTmsMonthPayStat(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
    	String sPayDt = (String)(String)ctx.get("PAY_DT");
    	
    	PosDataset ds = (PosDataset) ctx.get("dsOutMonthPayStat");
        int nSize = ds.getRecordCount();
        if(nSize>0){
        	PosRecord record = ds.getRecord(0);
        	String monthPrizFinishn = (String)record.getAttribute("MONTH_PRIZ_FINISH_YN"); 
        	new SaveMonthPayBac().saveMonthPrizFinish(stndYear, payMonth, monthPrizFinishn, sPayDt);
             
        	//���μ��� ��Ȳ �α�
//        	if(monthPrizFinishn.equals("Y")){
//        		String title = stndYear+"�� " + payMonth +"�� ������� ���� �Ϸ�";
//        		String content = stndYear+"�� " + payMonth +"�� ������� ���� �Ϸ��� �Ϸ� �Ǿ����ϴ�";
//        		new SaveProcessProg().insertProcessProg("007", stndYear, "", "", title, content); 
//        	}

            //���μ��� ���� ��Ȳ �α�(��ݰ��-����-�Ϸ�/���)
            if(Util.trim(monthPrizFinishn).equals("Y")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "070");
                hmProcess.put("PROG_STAT", "001");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
            else if(Util.trim(monthPrizFinishn).equals("N")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "070");
                hmProcess.put("PROG_STAT", "002");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
        }
    }
    
    /**
     * <p> ���� ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveMonthPay(PosRecord record)
    {
    	int effectedRowCnt = updateMonthPay(record);
        return effectedRowCnt;
    }

    /**
     * <p> ���� ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMonthPay(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_01")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_02")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_31"));
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_03")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_04")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_05")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_06")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_07")); 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_10"));  
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_12")); 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_04"));  
        param.setValueParamter(i++, record.getAttribute("AID_AMT_05")); 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_10"));  
        param.setValueParamter(i++, SESSION_USER_ID); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
		
        return this.getDao("boadao").update("tbeg_month_pay_uf001", param);
    }
    
    /**
     * ���� ���� ����
     *
     */
    protected void setTaxRate(){
    	PosRowSet prs = getTaxRateRecord();
    	if(prs.hasNext()){
    		PosRow[] pr = prs.getAllRow();
    		if(pr!= null && pr.length>0){
    			inceTaxRate = new Integer(((java.math.BigDecimal)pr[0].getAttribute("INCE_TAX_RATE")).intValue());
	    		inhTaxRate = new Integer(((java.math.BigDecimal)pr[0].getAttribute("INH_TAX_RATE")).intValue());  
	    		if(logger.isDebugEnabled())logger.logDebug("inceTaxRate = " + inceTaxRate + ",inhTaxRate = " + inhTaxRate);
    		}
    	}
    }
    
    /**
     * ¡���ǹ��� ���� ��ȸ 
     * @return
     */
    public PosRowSet getTaxRateRecord()
    {
    	PosParameter param = new PosParameter();
        return  this.getDao("boadao").find("tbeg_com_info_ff001", param);
    }
    
    public int updateTax(String stndYear, Integer payMonth, String sUserId){
    	setTaxRate();
    	PosParameter param = new PosParameter();
    	int i = 0;
         
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inhTaxRate); 
         
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inhTaxRate); 
         
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inceTaxRate); 
    	param.setValueParamter(i++, inhTaxRate); 
         
    	param.setValueParamter(i++, sUserId); 
    	param.setValueParamter(i++, stndYear); 
    	param.setValueParamter(i++, payMonth); 
         
    	updatePayMonthTaxRate(stndYear,  payMonth);
    	return this.getDao("boadao").update("tbeg_month_pay_uf999", param);
    }
    
    public int updatePayMonthTaxRate(String stndYear, Integer payMonth){
    	setTaxRate();
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, inceTaxRate); 
        param.setValueParamter(i++, inhTaxRate); 
        param.setValueParamter(i++, stndYear); 
        param.setValueParamter(i++, payMonth); 
        return this.getDao("boadao").update("tbeg_month_pay_bac_uf040", param);
    }
}
