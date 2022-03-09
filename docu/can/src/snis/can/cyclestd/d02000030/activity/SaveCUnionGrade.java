/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000030.activity.SaveCUnionGrade.java
 * ���ϼ���		: ������޺� ��������
 * �ۼ���			: �ѿ��� 
 * ����			: 1.0.0
 * ��������		: 2008-03-13
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000030.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* ������޺� �������� ���� ���� ���, ����, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveCUnionGrade extends SnisActivity
{    
    public SaveCUnionGrade()
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
        
        ds    = (PosDataset) ctx.get("dsOutCUnionGrade");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteCUnionGrade(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            try{
	            		nSaveCount = nSaveCount + insertCUnionGrade(record);
		        	}catch(Exception e)
	            	{
		        		Util.setSvcMsg(ctx,"���������� �����ϴ� ȸ���� �ֽ��ϴ�");
	            	}
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            		nSaveCount = nSaveCount + updateCUnionGrade(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������޺� �������� ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCUnionGrade(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateCUnionGrade(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertCUnionGrade(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ������޺� �������� ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCUnionGrade(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GRD_RANK"));
        param.setValueParamter(i++, record.getAttribute("PROP_SCR")); 
        param.setValueParamter(i++, record.getAttribute("NF_SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        return this.getDao("candao").update("tbdb_racer_grd_scr_ub001", param);
    }

    /**
     * <p>������޺� �������� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertCUnionGrade(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY")); 
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("GRD_RANK")); 
        param.setValueParamter(i++, record.getAttribute("PROP_SCR")); 
        param.setValueParamter(i++, record.getAttribute("NF_SCR")); 
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("candao").update("tbdb_racer_grd_scr_ib001", param);
    }

    /**
     * <p>������޺� �������� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteCUnionGrade(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        return  this.getDao("candao").update("tbdb_racer_grd_scr_db001", param);
    }
}
