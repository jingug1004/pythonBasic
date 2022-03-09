package snis.rbm.common.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePrivfileInfo extends SnisActivity {
	
	public SavePrivfileInfo(){
				
	}

	public String runActivity(PosContext ctx) {
		SavePrivfileDeleteInfo(ctx);
		
		return PosBizControlConstants.SUCCESS;
	}

	private void SavePrivfileDeleteInfo(PosContext ctx){
		System.out.println("SavePrivfileDeleteInfo() called..");
		System.out.println("FILE_NAME:"+Util.getCtxStr(ctx, "FILE_NAME"));
		PosParameter param = new PosParameter();
		int iParam = 0;
				
		StringBuffer query = new StringBuffer();
		query.append("INSERT /* save_priv_info */         ");
		query.append("       INTO TBRK_TEMP_PRIVFILE (    ");
		query.append("		 USER_ID, FILE_NAME, INST_DT) ");
		query.append(" VALUES (?, ?, SYSDATE)            ");

		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "USER_ID"));
		param.setValueParamter(iParam++, Util.getCtxStr(ctx, "FILE_NAME"));
				
		try {
			this.getDao("rbmdao").insertByQueryStatement(query.toString(), param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
