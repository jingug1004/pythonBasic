/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.RacerComInfoment.e06010010.activity.SaveRacerComInfo.java
 * ���ϼ���		: �������������/���� ���� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �������������/���� ���� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveRacerComInfo extends SnisActivity
{    
    public SaveRacerComInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerComInfo");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteRacerComInfo(record);
            }else{
            	nSaveCount = nSaveCount + saveRacerComInfo(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �������������/���� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerComInfo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerComInfo(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerComInfo(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �������������/���� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerComInfo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_NM"));
        param.setValueParamter(i++, record.getAttribute("JOB_TPE")); 
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS")); 
        param.setValueParamter(i++, record.getAttribute("BK_CD")); 
        param.setValueParamter(i++, record.getAttribute("BK_ACCUNT"));
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));
        param.setValueParamter(i++, record.getAttribute("MBS_FEE_YN"));
        param.setValueParamter(i++, record.getAttribute("FNC_CHK_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        return this.getDao("boadao").update("tbeg_racer_com_info_uf001", param);
    }

    /**
     * <p>�������������/���� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_NM"));
        param.setValueParamter(i++, record.getAttribute("JOB_TPE")); 
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS")); 
        param.setValueParamter(i++, record.getAttribute("BK_CD")); 
        param.setValueParamter(i++, record.getAttribute("BK_ACCUNT"));
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));
        param.setValueParamter(i++, record.getAttribute("MBS_FEE_YN"));
        param.setValueParamter(i++, record.getAttribute("FNC_CHK_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_racer_com_info_if001", param);
    }

    /**
     * <p>�������������/���� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        return  this.getDao("boadao").update("tbeg_racer_com_info_df001", param);
    }
   
}
