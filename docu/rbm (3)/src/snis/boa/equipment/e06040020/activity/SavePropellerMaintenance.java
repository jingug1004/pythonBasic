package snis.boa.equipment.e06040020.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SavePropellerMaintenance extends SnisActivity
{
  public String runActivity(PosContext ctx)
  {
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

    PosDataset ds = (PosDataset)ctx.get("dsOutMaintenance");
    nSize = ds.getRecordCount();
    for (int i = 0; i < nSize; ++i) {
      PosRecord record = ds.getRecord(i);
      if (record.getType() == 8) {
        nDeleteCount += deletePropellerMaintenance(record);
      } else {
        nSaveCount += savePropellerMaintenance(record);
      }
    }
    
    Util.setSaveCount(ctx, nSaveCount);
    Util.setDeleteCount(ctx, nDeleteCount);
  }
  
  protected int savePropellerMaintenance(PosRecord record)
  {
    int effectedRowCnt = 0;
    effectedRowCnt = mergePropellerMaintenance(record);

    return effectedRowCnt;
  }

  protected int mergePropellerMaintenance(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    
    param.setValueParamter(i++, record.getAttribute("MAINTENANCE_SEQ"));
    param.setValueParamter(i++, record.getAttribute("PROPELLER_SEQ"));
    param.setValueParamter(i++, record.getAttribute("PROPELLER_NO"));
    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
    param.setValueParamter(i++, record.getAttribute("WEIGHT"));
    param.setValueParamter(i++, record.getAttribute("END_THICKNESS"));
    param.setValueParamter(i++, record.getAttribute("SHAPE_CHECK"));
    param.setValueParamter(i++, record.getAttribute("PASS_YN"));
    param.setValueParamter(i++, record.getAttribute("RMK"));
    param.setValueParamter(i++, record.getAttribute("MAINTENANCE_DT"));
    param.setValueParamter(i++, this.SESSION_USER_ID);
    param.setValueParamter(i++, this.SESSION_USER_ID);

    return getDao("boadao").update("tbef_propeller_maintenance_mf001", param);
  }

  protected int deletePropellerMaintenance(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("MAINTENANCE_SEQ"));
    return getDao("boadao").update("tbef_propeller_maintenance_df001", param);
  }

}