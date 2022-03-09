/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000022.activity.SaveCStartTimeLength.java
 * ���ϼ���		: ������߽ð� �� �Ÿ�
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000022.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* ������߽ð� �� �Ÿ� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCStartTimeLength extends SnisActivity
{    
    public SaveCStartTimeLength()
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
        
        ds    = (PosDataset) ctx.get("dsOutCStartTimeLength");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCStartTimeLength(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCStartTimeLength(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCStartTimeLength(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������߽ð� �� �Ÿ� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCStartTimeLength(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCStartTimeLength(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCStartTimeLength(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������߽ð� �� �Ÿ� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCStartTimeLength(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STR_TIME")); 
        param.setValueParamter(i++, record.getAttribute("CCIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("RACELEN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACE_YY")); 
        param.setValueParamter(i++, record.getAttribute("RACE_MM"));
        param.setValueParamter(i++, record.getAttribute("AM_PM_GBN")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        return this.getDao("candao").update("tbdb_strt_time_dist_ub001", param);
    }

    /**
     * <p>������߽ð� �� �Ÿ� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCStartTimeLength(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY")); 
        param.setValueParamter(i++, record.getAttribute("RACE_MM"));
        param.setValueParamter(i++, record.getAttribute("AM_PM_GBN")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("STR_TIME")); 
        param.setValueParamter(i++, record.getAttribute("CCIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("RACELEN"));
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("candao").update("tbdb_strt_time_dist_ib001", param);
    }

    /**
     * <p>������߽ð� �� �Ÿ� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCStartTimeLength(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY")); 
        param.setValueParamter(i++, record.getAttribute("RACE_MM"));
        param.setValueParamter(i++, record.getAttribute("AM_PM_GBN")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        return  this.getDao("candao").update("tbdb_strt_time_dist_db001", param);
    }
}
