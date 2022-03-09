package snis.rbm.system.rsy1110.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.dao.vo.PosRowSet;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class RunSql extends SnisActivity {    
	
	
    public RunSql(){
    }

    /**
     * <p> RunSql Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx) {
    	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	/*
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("-------------------------------------- dsDetailList ----------------------");
	            logger.logInfo(record);
	            logger.logInfo("-------------------------------------- dsDetailList ----------------------");
	        }
        }
		*/
    	
    	String sDsName   = "";
    	
    	PosDataset ds;

        sDsName = "dsInput";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        int nSize =0;
	        nSize = ds.getRecordCount();
	        
	        if(nSize > 0) {
	        	PosRecord pr =  ds.getRecord(0);
	            PosRowSet prs;
	        	prs =  this.getDao("rbmdao").findByQueryStatement(pr.getAttribute("CONTENT").toString());
	    		String sOutDs = "dsOutput";
	        	ctx.put(sOutDs, prs);
	        	Util.addResultKey(ctx, sOutDs);    
	        }
	        
        }
        
        return PosBizControlConstants.SUCCESS;
    }    
    
 }