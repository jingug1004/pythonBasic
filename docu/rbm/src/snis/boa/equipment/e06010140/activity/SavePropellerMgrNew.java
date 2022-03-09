package snis.boa.equipment.e06010140.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SavePropellerMgrNew extends SnisActivity
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

        PosDataset ds = (PosDataset)ctx.get("dsOutPropellaList");
        nSize = ds.getRecordCount();
        for (int i = 0; i < nSize; ++i) {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == 8) {
                nDeleteCount += deletePropellerMgr(record);
                deletePropellerMaintenance(record);
            } else {
                nSaveCount += savePropellerMgr(record);
                processRegPropeller(record);
            }
        }
        
        Util.setSaveCount(ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /*
     * 지급 프로펠러 업데이트.
     */
    protected void processRegPropeller(PosRecord record){
	    
	    if(!Util.isNull(record.getAttribute("PROVIDE_PROPELLER_NO"))){
		    PosParameter param = new PosParameter();
		        int i = 0;
		        
		        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
		        param.setValueParamter(i++, record.getAttribute("PROVIDE_PROPELLER_NO"));

		        getDao("boadao").update("tbef_propeller_mgr_new_uf001", param);
	    }
    }

    protected int savePropellerMgr(PosRecord record)
    {
        int effectedRowCnt = 0;
        effectedRowCnt = mergePropellerMgr(record);

        return effectedRowCnt;
    }

    protected int mergePropellerMgr(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("PROPELLER_SEQ"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("PROPELLER_NO"));
        param.setValueParamter(i++, record.getAttribute("MAKER"));
        param.setValueParamter(i++, record.getAttribute("DIAMETER"));
        param.setValueParamter(i++, record.getAttribute("WEIGHT"));
        param.setValueParamter(i++, record.getAttribute("WING_WIDTH"));
        param.setValueParamter(i++, record.getAttribute("END_LENGTH"));
        param.setValueParamter(i++, record.getAttribute("WING_THICKNESS"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, record.getAttribute("REG_PRO_PHO_NM1"));
        param.setValueParamter(i++, record.getAttribute("REG_PRO_PHO_PATH1"));
        param.setValueParamter(i++, record.getAttribute("REG_PRO_PHO_NM2"));
        param.setValueParamter(i++, record.getAttribute("REG_PRO_PHO_PATH2"));
        param.setValueParamter(i++, record.getAttribute("REG_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("REG_DT"));
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_PROPELLER_NO"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_TYPE_CD"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_DT"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_RS"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_PRO_PHO_NM1"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_PRO_PHO_PATH1"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_PRO_PHO_NM2"));
        param.setValueParamter(i++, record.getAttribute("DISUSE_PRO_PHO_PATH2"));
        param.setValueParamter(i++, record.getAttribute("PROVIDE_PROPELLER_NO"));        
        param.setValueParamter(i++, this.SESSION_USER_ID);
        param.setValueParamter(i++, this.SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("DAMAGE_PART"));

        return getDao("boadao").update("tbef_propeller_mgr_new_mf001", param);
    }

    protected int deletePropellerMgr(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PROPELLER_SEQ"));
        return getDao("boadao").update("tbef_propeller_mgr_new_df001", param);
    }

    //삭제된 프로펠러 목록의 점기 점검 내역 삭제.
    protected int deletePropellerMaintenance(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PROPELLER_SEQ"));
        return getDao("boadao").update("tbef_propeller_maintenance_new_df002", param);
    }
}