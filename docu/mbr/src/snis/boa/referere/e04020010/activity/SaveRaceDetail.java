/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.referere.e04020010.activity.SaveRaceDetail.java
 * ���ϼ���		: ���� ���� 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2007-12-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.referere.e04020010.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���ְ�� ������ ���� �Ѵ�.
* @auther ������
* @version 1.0
*/
public class SaveRaceDetail extends SnisActivity
{    
	
    public SaveRaceDetail()
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
        
        //�������̺� ����	
        ds    = (PosDataset) ctx.get("dsOutRace");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveRaceDetailResult(record);
        }
    }
    /**
     * <p> �������̺� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int SaveRaceDetailResult(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRaceDetailResult(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> �������̺�  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    
     protected int updateRaceDetailResult(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_STTS_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_STTS2_CD"));
        param.setValueParamter(i++, record.getAttribute("RACE_STTS_DESC"));
        param.setValueParamter(i++, record.getAttribute("WEATH_CD"));
        param.setValueParamter(i++, record.getAttribute("TEMPT"));
        param.setValueParamter(i++, record.getAttribute("WIND_SPD"));
        param.setValueParamter(i++, record.getAttribute("WIND_DIRC_CD"));
        param.setValueParamter(i++, record.getAttribute("WATER_TEMPT"));
        param.setValueParamter(i++, record.getAttribute("WAVE"));
        param.setValueParamter(i++, record.getAttribute("WATER_LVL"));
        param.setValueParamter(i++, record.getAttribute("HUMIDITY"));
        param.setValueParamter(i++, record.getAttribute("STRT_TIME"));
        param.setValueParamter(i++, record.getAttribute("DIS_NEED_TIME"));
        param.setValueParamter(i++, record.getAttribute("MNG"));
        param.setValueParamter(i++, record.getAttribute("MNG_FILE_URL"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD"));
        param.setValueParamter(i++, record.getAttribute("REFE_HEAD_FILE_URL"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN"));
        param.setValueParamter(i++, record.getAttribute("INSP_CMAN_FILE_URL"));
        param.setValueParamter(i++, SESSION_USER_ID);
			    		   
	    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
	    param.setValueParamter(i++, record.getAttribute("TMS"));
	    param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
	    param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	    
	    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
	    param.setValueParamter(i++, record.getAttribute("TMS"));
        return this.getDao("boadao").update("tbec_appo_race_result_ud001", param);
    }
     
}