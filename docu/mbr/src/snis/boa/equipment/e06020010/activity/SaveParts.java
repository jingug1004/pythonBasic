/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020010.activity.SaveParts.java
 * ���ϼ���		: ��ǰ�� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020010.activity;


import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ��ǰ�� ���� ������ ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SaveParts extends SnisActivity
{    
    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//����� ���� Ȯ��
    	if ((setUserInfo(ctx) == null) || (!(setUserInfo(ctx).equals("success")))) {
    	      Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
    	      return "success";
    	    }
    	    saveState(ctx);
    	    return "success";
    }
   
    
    protected void saveState(PosContext ctx)
    {
      int nSaveCount = 0;
      int nDeleteCount = 0;

      int nSize = 0;

      PosDataset ds = (PosDataset)ctx.get("dsOutParts");
      nSize = ds.getRecordCount();
      for (int i = 0; i < nSize; ++i) {
        PosRecord record = ds.getRecord(i);
        if (record.getType() == 8)
          nDeleteCount += deletePartsMaster(record);
        else
          nSaveCount += mergePartsMaster(record);

      }

      Util.setSaveCount(ctx, nSaveCount);
      Util.setDeleteCount(ctx, nDeleteCount);
    }
  

    /**
     * <p>��ǰ �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergePartsMaster(PosRecord record)
    {
      PosParameter param = new PosParameter();
      int i = 0;

      param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
      param.setValueParamter(i++, record.getAttribute("PARTS_TPE"));
      param.setValueParamter(i++, record.getAttribute("PARTS_COM_CD"));
      param.setValueParamter(i++, record.getAttribute("MODULE_CODE"));
      param.setValueParamter(i++, record.getAttribute("PARTS_ITEM_CD_NM"));
      param.setValueParamter(i++, record.getAttribute("SPEC"));
      param.setValueParamter(i++, record.getAttribute("SAFT_UNIT_CNT"));
      param.setValueParamter(i++, record.getAttribute("LOC"));
      param.setValueParamter(i++, record.getAttribute("USE_YN"));
      param.setValueParamter(i++, this.SESSION_USER_ID);

      return getDao("boadao").update("tbef_parts_master_mf001", param);
    }

    
    protected int deletePartsMaster(PosRecord record)
    {
      PosParameter param = new PosParameter();
      int i = 0;
      param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
      return getDao("boadao").update("tbef_parts_master_df001", param);
    }
}
