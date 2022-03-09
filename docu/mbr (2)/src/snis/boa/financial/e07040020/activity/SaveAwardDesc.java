// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveAwardDesc.java

package snis.boa.financial.e07040020.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveAwardDesc extends SnisActivity
{

    public SaveAwardDesc()
    {
    }

    public String runActivity(PosContext ctx)
    {
        if(setUserInfo(ctx) == null || !setUserInfo(ctx).equals("success"))
        {
            Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return "success";
        } else
        {
            saveState(ctx);
            return "success";
        }
    }

    protected void saveState(PosContext ctx)
    {
        int nSaveCount = 0;
        int nDeleteCount = 0;
        int nSize = 0;
        PosDataset ds = (PosDataset)ctx.get("dsOutPartsCom");
        nSize = ds.getRecordCount();
        for(int i = 0; i < nSize; i++)
        {
            PosRecord record = ds.getRecord(i);
            if(record.getType() == 8)
                nDeleteCount += deletePartsCom(record);
            else
                nSaveCount += savePartsCom(record);
        }

        Util.setSaveCount(ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    protected int savePartsCom(PosRecord record)
    {
        int effectedRowCnt = 0;
        effectedRowCnt = updatePartsCom(record);
        if(effectedRowCnt < 1)
            effectedRowCnt = insertPartsCom(record);
        return effectedRowCnt;
    }

    protected int updatePartsCom(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NM"));
        param.setValueParamter(i++, record.getAttribute("AWARD_DT"));
        param.setValueParamter(i++, record.getAttribute("AWARD_DESC"));
        param.setValueParamter(i++, record.getAttribute("AWARD_AMT"));
        param.setValueParamter(i++, record.getAttribute("AWARD_RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("AWARD_SEQ"));
        return getDao("boadao").update("tbeg_racer_award_his_uf001", param);
    }

    protected int insertPartsCom(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("RACER_NM"));
        param.setValueParamter(i++, record.getAttribute("AWARD_DT"));
        param.setValueParamter(i++, record.getAttribute("AWARD_DESC"));
        param.setValueParamter(i++, record.getAttribute("AWARD_AMT"));
        param.setValueParamter(i++, record.getAttribute("AWARD_RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return getDao("boadao").update("tbeg_racer_award_his_if001", param);
    }

    protected int deletePartsCom(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("AWARD_SEQ"));
        return getDao("boadao").update("tbeg_racer_award_his_df001", param);
    }
}
