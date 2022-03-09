/*================================================================================
 * �ý���			: ������� ���� ����  
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquip.java
 * ���ϼ���		: ��� ���� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� ��Ʈ�� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveAcdntReport extends SnisActivity
{    
    public SaveAcdntReport()
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
        
        ds    = (PosDataset) ctx.get("dsOutAcdntReport");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteAcdntReport(record);
            }else{
            	nSaveCount = nSaveCount + saveAcdntReport(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������纸�� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveAcdntReport(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateAcdntReport(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertAcdntReport(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������纸��  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAcdntReport(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NOS"));  
        param.setValueParamter(i++, record.getAttribute("RACER_NOS"));   
        param.setValueParamter(i++, record.getAttribute("RACER_NMS")); 
        param.setValueParamter(i++, record.getAttribute("MOT_NOS"));  
        param.setValueParamter(i++, record.getAttribute("BOAT_NOS"));
        param.setValueParamter(i++, record.getAttribute("CNF_DT")); 
        param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRS_STAT")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_SUMMARY")); 
        param.setValueParamter(i++, record.getAttribute("ACDENT_DESC")); 
        param.setValueParamter(i++, record.getAttribute("PRS_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
	        
        return this.getDao("boadao").update("tbef_acdnt_report_uf001", param);
    }

    /**
     * <p>������纸�� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertAcdntReport(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));   
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NOS")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NOS"));  
        param.setValueParamter(i++, record.getAttribute("RACER_NMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NOS")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_NOS")); 
        param.setValueParamter(i++, record.getAttribute("CNF_DT")); 
        param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRS_STAT")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_SUMMARY")); 
        param.setValueParamter(i++, record.getAttribute("ACDENT_DESC")); 
        param.setValueParamter(i++, record.getAttribute("PRS_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbef_acdnt_report_if001", param);
    }

    /**
     * <p>������纸�� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteAcdntReport(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        
        return  this.getDao("boadao").update("tbef_acdnt_report_df001", param);
    }
   
}
