/*================================================================================
 * �ý���			: �ý��� ���� 
 * �ҽ����� �̸�	: snis.boa.system.e01010150.activity.SaveCloseRaceInfo.java
 * ���ϼ���		: ���ָ������� ���
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-04-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010150.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���ָ������� ��� ���� ���� ���, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCloseRaceInfo extends SnisActivity
{    
    public SaveCloseRaceInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutCloseRaceInfo");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCloseRaceInfo(record);
	            	break;
            }
        }
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertCloseRaceInfo(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateCloseRaceInfo(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���ָ������� ��� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCloseRaceInfo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCloseRaceInfo(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCloseRaceInfo(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���ָ������� ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCloseRaceInfo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("POST_DT"));
        param.setValueParamter(i++, record.getAttribute("CURR_DT"));
        param.setValueParamter(i++, record.getAttribute("REST_MM"));
        param.setValueParamter(i++, record.getAttribute("RACE_END_YN"));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));
        param.setValueParamter(i++, record.getAttribute("PERF_NO"));
        param.setValueParamter(i++, record.getAttribute("PERF_TYPE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        
        return this.getDao("boadao").update("tbes_sdl_tm_ue001", param);
    }

    /**
     * <p>���ָ������� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCloseRaceInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD")); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POST_DT"));
        param.setValueParamter(i++, record.getAttribute("CURR_DT"));
        param.setValueParamter(i++, record.getAttribute("REST_MM"));
        param.setValueParamter(i++, record.getAttribute("RACE_END_YN"));
        param.setValueParamter(i++, record.getAttribute("RACE_DAY"));
        param.setValueParamter(i++, record.getAttribute("PERF_NO"));
        param.setValueParamter(i++, record.getAttribute("PERF_TYPE"));
        
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbes_sdl_tm_ie001", param);
    }

    /**
     * <p>���ָ������� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCloseRaceInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        
        return  this.getDao("boadao").update("tbes_sdl_tm_de001", param);
    }
}
