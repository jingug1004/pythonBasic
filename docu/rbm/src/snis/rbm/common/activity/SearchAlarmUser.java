package snis.rbm.common.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SearchAlarmUser extends SnisActivity {
	
	public SearchAlarmUser(){
		
		
	}

	public String runActivity(PosContext ctx) {
		getAlarmUser(ctx);
		
		return PosBizControlConstants.SUCCESS;
	}

	private void getAlarmUser(PosContext ctx){
		PosParameter param = new PosParameter();
		PosRowSet rowset = null;

		String sResultKey = "dsRecvUser";
		String sUserIds= (String) ctx.get("USER_IDS");
	

		String sWhere = "";

		//if (!Util.nullToStr(sUserIds).equals("")) {
		
			//if (!Util.nullToStr(sUserIds).equals("")) {
	
				sWhere += "\n AND A.USER_ID IN ('"+sUserIds+"') \n  ";
				
	             
				sWhere +=  " ORDER BY  A.USER_ID  \n  ";
			//} 


		//} 
		
		try {
			rowset = Util.getRowSet(ctx, this.getDao("rbmdao"), "common_alram_user", sWhere, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.put(sResultKey, rowset);

		Util.addResultKey(ctx, sResultKey);

	}
}
