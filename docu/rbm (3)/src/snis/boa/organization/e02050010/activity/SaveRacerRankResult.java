/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrange.java
 * ���ϼ���		: ����������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02050010.activity;

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
public class SaveRacerRankResult extends SnisActivity
{   	
    public SaveRacerRankResult()
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
        
        sDsName = "dsOutArrange";
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

        // ������� �� �� ����
        for ( int j = 1; j <= 15; j++ ) {
	        sDsName = "dsOutRace";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	 nTempCnt = updateRacerRankResult(record);
			        }
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    
    /**
     * <p> ������� �� �� ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    protected int updateRacerRankResult(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ANTC_RANK"));
        param.setValueParamter(i++, record.getAttribute("ANTC_DESC"));
        param.setValueParamter(i++, record.getAttribute("RSLT_DESC"));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_rank_result_ub001", param);
        
        return dmlcount;
    }
}
