package snis.rbm.business.rem3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SearchStayStatByRace extends SnisActivity {
	public SearchStayStatByRace(){
		
		
	}
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {
			getStayStatByRace(ctx);
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}
	
	
	private void getStayStatByRace(PosContext ctx) throws Exception {
	

	}
}

