/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrange.java
 * ���ϼ���		: ȸ���� ����� �м����
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2010-01-24
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveOrganAnal extends SnisActivity
{   	
    public SaveOrganAnal()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutOrganAnal";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ����� �м� ����
        for ( int j = 1; j <= 15; j++ ) {
	        sDsName = "dsOutOrganAnal";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	 nTempCnt = updateOrganAnal(record, ctx);
			        }
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    
    /**
     * <p> ����� �м� ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    protected int updateOrganAnal(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, ctx.get("STND_YEAR"));
        param.setValueParamter(i++, ctx.get("MBR_CD"));
        param.setValueParamter(i++, ctx.get("TMS"));
        param.setValueParamter(i++, record.getAttribute("ORGAN_WAY"));
        param.setValueParamter(i++, record.getAttribute("RESULT_ANAL"));
        param.setValueParamter(i++, record.getAttribute("GENERAL_ANAL"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY1_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY2_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY3_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY4_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY5_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY6_T"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_E"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_Q"));
        param.setValueParamter(i++, record.getAttribute("HIT_DAY7_T"));
        param.setValueParamter(i++, record.getAttribute("ANAL_INST_NM"));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_organ_analysis_ub001", param);
        
        return dmlcount;
    }
}
