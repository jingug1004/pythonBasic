/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05040010.activity.SaveRacerSanc.java
 * ���ϼ���		: ����ó�е��
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05040010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ����ó�е�� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveRacerSanc extends SnisActivity
{    
    public SaveRacerSanc()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerSanc");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteRacerSanc(record);
	            	break;
            }
        }

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertRacerSanc(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateRacerSanc(record, ctx);
	            	break;	
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����ó�е�� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerSanc(PosRecord record, PosContext ctx)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerSanc(record, ctx);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerSanc(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ����ó�е�� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerSanc(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD"));
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD")); 
        param.setValueParamter(i++, record.getAttribute("SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, ctx.get("CFM"));
        param.setValueParamter(i++, ctx.get("CFM"));
        param.setValueParamter(i++, ctx.get("CFM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("SANC_DEPT"));			// ����������� �߰�
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_GRD_CD"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));

        return this.getDao("boadao").update("tbee_racer_sanc_ue001", param);
        
        
    }

    /**
     * <p>����ó�е�� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerSanc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD"));
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD")); 
        param.setValueParamter(i++, record.getAttribute("SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("SANC_DEPT"));			// ����������� �߰�
        param.setValueParamter(i++, record.getAttribute("RACER_GRD_CD"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, record.getAttribute("UNIT"));
        param.setValueParamter(i++, record.getAttribute("SANC_TERM"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        
        return this.getDao("boadao").update("tbee_racer_sanc_ie001", param);
    }

    /**
     * <p>����ó�е�� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerSanc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO"));
        return  this.getDao("boadao").update("tbee_racer_sanc_de001", param);
    }
}
