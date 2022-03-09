// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchAwardList.java

package snis.boa.financial.e07040020.activity;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SearchAwardList extends SnisActivity
{

    public SearchAwardList()
    {
    }

    public String runActivity(PosContext ctx)
    {
        getAwardCom(ctx);
        return "success";
    }

    private void getAwardCom(PosContext ctx)
    {
        String sResultKey = "dsOutPartsCom";
        String award_ym = (String)ctx.get("AWARD_YM");
        String racer_nm = (String)ctx.get("RACER_NM");
        PosRowSet rowset = getPartsComRowSet(award_ym, racer_nm);
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }

    public PosRowSet getPartsComRowSet(String award_ym, String racer_nm)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, award_ym);
        param.setWhereClauseParameter(i++, racer_nm);
        return getDao("boadao").find("tbeg_racer_award_his_ff001", param);
    }
}
