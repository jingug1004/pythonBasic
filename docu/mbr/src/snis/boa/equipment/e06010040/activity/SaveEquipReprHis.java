package snis.boa.equipment.e06010040.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveEquipReprHis extends SnisActivity
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

    PosDataset ds = (PosDataset)ctx.get("dsOutEquipReprHis");
    nSize = ds.getRecordCount();
    for (int i = 0; i < nSize; ++i) {
      PosRecord record = ds.getRecord(i);
      if (record.getType() == 8) {
        nDeleteCount += deleteEquipReprHis(record);

        deleteEquipReprTpeHis(record);
      } else {
        nSaveCount += saveEquipReprHis(record);

        deleteEquipReprTpeHis(record);
        insertEquipReprTpeHis(record);
      }

    }

    Util.setSaveCount(ctx, nSaveCount);
    Util.setDeleteCount(ctx, nDeleteCount);
  }

  protected int saveEquipReprHis(PosRecord record)
  {
    int effectedRowCnt = 0;
    effectedRowCnt = mergeEquipReprHis(record);

    return effectedRowCnt;
  }

  protected int mergeEquipReprHis(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    String homepageYN = (String)record.getAttribute("HOMEPAGE_YN");
    homepageYN = ((homepageYN != null) && (homepageYN.equals("1"))) ? "Y" : "N";

    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
    param.setValueParamter(i++, record.getAttribute("MBR_CD"));
    param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));
    param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));
    param.setValueParamter(i++, record.getAttribute("REPR_DT"));
    param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));
    param.setValueParamter(i++, record.getAttribute("REPR_TPE_CDS"));
    param.setValueParamter(i++, record.getAttribute("REPR_TPE_NMS"));
    param.setValueParamter(i++, record.getAttribute("MJR_PARTS"));
    param.setValueParamter(i++, record.getAttribute("REPR_DESC"));
    param.setValueParamter(i++, record.getAttribute("REPR_NM"));
    param.setValueParamter(i++, homepageYN);
    param.setValueParamter(i++, record.getAttribute("HOMEPAGE_DESC"));
    param.setValueParamter(i++, record.getAttribute("REL_STND_YEAR"));
    param.setValueParamter(i++, record.getAttribute("REL_MBR_CD"));
    param.setValueParamter(i++, record.getAttribute("REL_TMS"));
    param.setValueParamter(i++, record.getAttribute("REL_DAY_ORD"));
    param.setValueParamter(i++, record.getAttribute("REL_RACE_NO"));
    param.setValueParamter(i++, record.getAttribute("REL_RACE_REG_NO"));
    param.setValueParamter(i++, this.SESSION_USER_ID);
    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));

    return getDao("boadao").update("tbef_equip_repr_his_mf001", param);
  }

  protected int insertEquipReprTpeHis(PosRecord record)
  {
    int effectedRowCnt = 0;

    String[] reprTpeCds = ((String)record.getAttribute("REPR_TPE_CDS")).split(",");
    for (int j = 0; j < reprTpeCds.length; ++j) {
      PosParameter param = new PosParameter();
      int i = 0;
      param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
      param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));
      param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
      param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));
      param.setValueParamter(i++, reprTpeCds[j]);
      effectedRowCnt += getDao("boadao").update("tbef_equip_repr_tpe_his_if001", param);
    }

    return effectedRowCnt;
  }

  protected int deleteEquipReprHis(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
    param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));

    return getDao("boadao").update("tbef_equip_repr_his_df001", param);
  }

  protected int deleteEquipReprTpeHis(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
    param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));
    param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
    param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));

    return getDao("boadao").update("tbef_equip_repr_tpe_his_df001", param);
  }
}