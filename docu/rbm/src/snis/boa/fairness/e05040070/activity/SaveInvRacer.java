/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.fairness.e05040070.activity.SaveInvRacer.java
 * ���ϼ���		: �������� ���� ���
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-01-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.fairness.e05040070.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �������� ���� ��� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveInvRacer extends SnisActivity
{    
    public SaveInvRacer()
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
        
        ds    = (PosDataset) ctx.get("dsOutInvRacer");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteInvRacer(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertInvRacer(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateInvRacer(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �������� ���� ��� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveInvRacer(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateInvRacer(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertInvRacer(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �������� ���� ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateInvRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_YN"));
        param.setValueParamter(i++, record.getAttribute("HOMPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        
        return this.getDao("boadao").update("tbee_investigate_racer_ue001", param);
    }

    /**
     * <p>�������� ���� ��� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertInvRacer(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN_CD"));
        param.setValueParamter(i++, record.getAttribute("SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_SANC_RSN"));
        param.setValueParamter(i++, record.getAttribute("ACT_DT"));
        param.setValueParamter(i++, record.getAttribute("HOMEPAGE_YN"));
        param.setValueParamter(i++, record.getAttribute("HOMPAGE_NOTI_SDT"));
        param.setValueParamter(i++, record.getAttribute("HOMPAGE_NOTI_EDT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbee_investigate_racer_ie001", param);
    }

    /**
     * <p>�������� ���� ��� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteInvRacer(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        return  this.getDao("boadao").update("tbee_investigate_racer_de001", param);
    }
}
