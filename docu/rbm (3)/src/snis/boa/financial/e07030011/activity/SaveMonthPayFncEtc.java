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
package snis.boa.financial.e07030011.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.financial.e07010030.activity.SaveMonthPayBac;
import snis.boa.system.e01010061.activity.SaveProcessProg;

/**
* ���� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveMonthPayFncEtc extends SnisActivity
{    
    public SaveMonthPayFncEtc()
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
        
        ds    = (PosDataset) ctx.get("dsOutMonthPayFncEtc");
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
             String monthEtcFinishn = (String)record.getAttribute("MONTH_ETC_FINISH_YN"); 
             new SaveMonthPayBac().saveMonthEtcFinish(stndYear, payMonth, monthEtcFinishn);
             
             //���μ��� ��Ȳ �α�
             if(monthEtcFinishn.equals("Y")){
	             String title = stndYear+"�� " + payMonth +"�� ��Ÿ ���� ���� ���  ���� �Ϸ�";
	             String content = stndYear+"�� " + payMonth +"�� ��Ÿ ���� ���� �����   �Ϸ� �Ǿ����ϴ�";
	             new SaveProcessProg().insertProcessProg("007", stndYear, "", "", title, content); 
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
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_07")); //    -- �����׸� - ��Ÿ����(��Ÿ�׸�)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_10")); //    -- ���ް����׸� - �Ĵ�(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_12")); //    -- ���ް����׸� - ��������(����ںδ�)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_14")); //    -- ���ް����׸� - ���ຸ���(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_15")); //    -- ���ް����׸� - ��������(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_16")); //    -- ���ް����׸� - ��Ÿ�׸�(����)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_19")); //    -- �����׸� - ��Ÿ����(�Ʒ�������������)
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT_20")); //    -- �����׸� - ��Ÿ����(���࿬����������)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_01")); // 	-- �����׸� - �ҵ漼
        param.setValueParamter(i++, record.getAttribute("AID_AMT_02")); // 	-- �����׸� - �ֹμ�
        param.setValueParamter(i++, record.getAttribute("AID_AMT_03")); // 	-- �����׸� - ��Ÿ�׸�
        param.setValueParamter(i++, record.getAttribute("AID_AMT_10")); //    	-- ���ް����׸� - �Ĵ�(���� )
        param.setValueParamter(i++, record.getAttribute("AID_AMT_11")); //    	-- ���ް����׸� - ���ຸ���(����)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_12")); //    	-- ���ް����׸� - ��������(�����δ��)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_13")); //    	-- ���ް����׸� - ��������(����)
        param.setValueParamter(i++, record.getAttribute("AID_AMT_14")); //    	-- ���ް����׸� - ��Ÿ�׸�(����)
        param.setValueParamter(i++, SESSION_USER_ID); 
			    		
        return this.getDao("boadao").update("tbeg_month_pay_fnc_etc_mf001", param);
    }
    
    
}
