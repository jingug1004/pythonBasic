/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.ComInfoment.e06010010.activity.SaveComInfo.java
 * ���ϼ���		: ¡���ǹ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ¡���ǹ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveComInfo extends SnisActivity
{    
    public SaveComInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutComInfo");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveComInfo(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ¡���ǹ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveComInfo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateComInfo(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertComInfo(record);
    	}
    	if(record.getAttribute("TAX_RATE_CHANGE_YN").equals("Y")){
    		saveTaxRateHis(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ¡���ǹ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateComInfo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_NM"));  
        param.setValueParamter(i++, record.getAttribute("COM_OWNER_NM")); 
        param.setValueParamter(i++, record.getAttribute("CORP_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("COM_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("JOB_TPE"));  
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS"));  
        param.setValueParamter(i++, record.getAttribute("INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("APP_END_YEAR_MONTH"));  
        param.setValueParamter(i++, SESSION_USER_ID);
		   
        return this.getDao("boadao").update("tbeg_com_info_uf001", param);
    }

    /**
     * <p>¡���ǹ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_NM"));  
        param.setValueParamter(i++, record.getAttribute("COM_OWNER_NM")); 
        param.setValueParamter(i++, record.getAttribute("CORP_REG_NO"));  
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("COM_ADDR"));  
        param.setValueParamter(i++, record.getAttribute("JOB_TPE"));  
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS"));  
        param.setValueParamter(i++, record.getAttribute("INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("STR_YEAR_MONTH"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_com_info_if001", param);
    }
    
    /**
     * ���� �����̷� ���� 
     * @param record
     * @return
     */
    protected int saveTaxRateHis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BEFORE_INCE_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_INH_TAX_RATE"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("APP_STR_YEAR_MONTH"));  
        param.setValueParamter(i++, record.getAttribute("BEFORE_APP_STR_YEAR_MONTH")); 
        param.setValueParamter(i++, SESSION_USER_ID);
		   
        return this.getDao("boadao").update("tbeg_tax_rate_his_if001", param);
    }


   
   
}
