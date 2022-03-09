package snis.boa.fairness.e05070050.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveCnlData extends SnisActivity
{
  public String runActivity(PosContext ctx)
  {
    if (!(setUserInfo(ctx).equals("success"))) {
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

    PosDataset ds = (PosDataset)ctx.get("dsOutCnlDataList");
    nSize = ds.getRecordCount();

    for (int i = 0; i < nSize; ++i) {
      PosRecord record = ds.getRecord(i);
      switch (record.getType())
      {
      case 8:
        nDeleteCount += deleteCnlData(record);
        break;
      case 2:
        nSaveCount += insertCnlData(record);
        break;
      case 4:
        nSaveCount += updateCnlData(record);
      }

    }

    Util.setSaveCount(ctx, nSaveCount);
    Util.setDeleteCount(ctx, nDeleteCount);
  }

  protected int saveCnlData(PosRecord record)
  {
    int effectedRowCnt = 0;
    effectedRowCnt = updateCnlData(record);
    if (effectedRowCnt < 1)
      effectedRowCnt = insertCnlData(record);

    return effectedRowCnt;
  }

  protected int updateCnlData(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("CNL_MEMO"));
    param.setValueParamter(i++, record.getAttribute("FAIR_TDY"));
    param.setValueParamter(i++, record.getAttribute("RACE_TDY"));
    param.setValueParamter(i++, record.getAttribute("CNL_ID"));
    param.setValueParamter(i++, record.getAttribute("CNL_DTM"));
    param.setValueParamter(i++, this.SESSION_USER_ID);
    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
    param.setValueParamter(i++, record.getAttribute("CNL_DTM"));
    return getDao("boadao").update("tbee_counsel_data_ue001", param);
  }

  protected int insertCnlData(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
    param.setValueParamter(i++, record.getAttribute("CNL_MEMO"));
    param.setValueParamter(i++, record.getAttribute("FAIR_TDY"));
    param.setValueParamter(i++, record.getAttribute("RACE_TDY"));
    param.setValueParamter(i++, record.getAttribute("CNL_ID"));
    param.setValueParamter(i++, record.getAttribute("CNL_DTM"));
    param.setValueParamter(i++, this.SESSION_USER_ID);
    return getDao("boadao").update("tbee_counsel_data_ie001", param);
  }

  protected int deleteCnlData(PosRecord record)
  {
    PosParameter param = new PosParameter();
    int i = 0;
    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
    param.setValueParamter(i++, record.getAttribute("CNL_DTM"));
    return getDao("boadao").update("tbee_counsel_data_de001", param);
  }
}