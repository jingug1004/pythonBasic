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
package snis.boa.financial.e07030010.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.financial.e07020055.activity.SaveMonthPay;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* ���� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveMonthPayEtc extends SnisActivity
{    
    public SaveMonthPayEtc()
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
        
        //���� update
        String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
        new SaveMonthPay().updateTax(stndYear, payMonth, SESSION_USER_ID);
        
        //���� update
        updateMonthPayStat(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayEtc");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveMonthPayEtc(record);
        }

       
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * ��� ���� ���� Update
     * @param ctx
     */
    protected void updateMonthPayStat(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	Integer payMonth = new Integer((String)ctx.get("PAY_MONTH"));
    	
    	PosDataset ds = (PosDataset) ctx.get("dsOutMonthPayStat");
        int nSize = ds.getRecordCount();
        if(nSize>0){
        	PosRecord record = ds.getRecord(0);
        	String monthActFinishn = (String)record.getAttribute("MONTH_ACT_FINISH_YN"); 
        	new SaveMonthPayBac().saveMonthActFinish(stndYear, payMonth, monthActFinishn);
             
//        	//���μ��� ��Ȳ �α�
//        	if(monthActFinishn.equals("Y")){
//        		String title = stndYear+"�� " + payMonth +"�� ȸ�� ���� �Ϸ�";
//        		String content = stndYear+"�� " + payMonth +"�� ȸ��  �Ϸ��� �Ϸ� �Ǿ����ϴ�";
//        		new SaveProcessProg().insertProcessProg("007", stndYear, "", "", title, content); 
//        	}

            //���μ��� ���� ��Ȳ �α�(���ȸ��-����-�Ϸ�/���)
            if(Util.trim(monthActFinishn).equals("Y")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "075");
                hmProcess.put("PROG_STAT", "001");
                hmProcess.put("USER_ID",   SESSION_USER_ID);

                SaveProcess sp = new SaveProcess();
                sp.saveProcess(hmProcess);
            }
            else if(Util.trim(monthActFinishn).equals("N")){
                HashMap hmProcess = new HashMap();
                hmProcess.put("STND_YEAR", stndYear);
                hmProcess.put("MBR_CD"   , "001");
                hmProcess.put("MONTH"    , payMonth);
                hmProcess.put("DUTY_CD"  , "007");
                hmProcess.put("WORK_CD"  , "075");
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
    protected int saveMonthPayEtc(PosRecord record)
    {
    	int effectedRowCnt = mergeMonthPayEtc(record);
        return effectedRowCnt;
    }

    /**
     * <p> ���� ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeMonthPayEtc(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH"));
        param.setValueParamter(i++, record.getAttribute("PAY_TPE"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_08")); //    -- �����׸� - ���� 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_09")); //		-- �����׸� - ��Ÿ�׸� 
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_12")); //    -- ���ް����׸� - ��������(����ںд�)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_14")); // 	-- ���ް����׸� - ���غ����(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_15")); // 	-- ���ް����׸� - ��������(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_16")); //		-- ���ް����׸� - ��Ÿ�׸�(����)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_05")); //     -- �����׸� - ��������(���κδ��)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_06")); //     -- �����׸� - �̼��� 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_07")); //     -- �����׸� - ������ �뿩�� 
        param.setValueParamter(i++, record.getAttribute("AID_AMT_08")); //     -- �����׸� - ��Ÿ�׸�
        param.setValueParamter(i++, record.getAttribute("AID_AMT_11")); //     -- ���ް����׸� - ���غ����(����)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_12")); //     -- ���ް����׸� - ��������(����ںд�)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_13")); //     -- ���ް����׸� - ��������(����)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_14")); //     -- ���ް����׸� - ��Ÿ�׸�(����)
        param.setValueParamter(i++, SESSION_USER_ID); 
        
        return this.getDao("boadao").update("tbeg_month_pay_etc_mf001", param);
    }
}
