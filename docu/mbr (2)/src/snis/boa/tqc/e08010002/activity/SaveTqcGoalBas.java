/*================================================================================
 * �ý���			: TQC �ڵ� ����
 * �ҽ����� �̸�	: snis.boa.system.tqc.activity.SaveTqcGoalBas.java
 * ���ϼ���		: ��ǥ, �򰡱��� ���
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2008-09-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.tqc.e08010002.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveTqcGoalBas extends SnisActivity{

	public SaveTqcGoalBas(){
		
	}
	 public String runActivity(PosContext ctx)
	    {   	
	    	// ����� ���� Ȯ��
	    	
		 if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	    	}
	     
	        PosDataset ds;
	        int        nSize        = 0;
	        String     sDsName = "";
	        sDsName = "dsGoalList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
	        }
	        
	        sDsName = "dsRateList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
	        }
	        
	        sDsName = "dsBasList";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
	        }
		        
	        saveState(ctx);

	        return PosBizControlConstants.SUCCESS;
	    }
	 protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount   = 0; 
	    	int nDeleteCount = 0; 
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;

	        sDsName = "dsGoalList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		
			        	nTempCnt = insertGoal(record);

				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }	        
		        }	        
	        }
	        
	        sDsName = "dsRateList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		
			        	nTempCnt = insertRate(record);

				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }	        
		        }	        
	        }
	        
	        sDsName = "dsBasList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	nTempCnt = insertBas(record);
				       
			        	nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
		        }         
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	 protected int insertGoal(PosRecord record){
		 
		 PosParameter param = new PosParameter();   
	     int i = 0;
	     
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// WHERE
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
	     param.setValueParamter(i++, record.getAttribute("GOAL"			  ));   // UPDATE             
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// INSERT
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));	
	     param.setValueParamter(i++, record.getAttribute("GOAL"			  ));
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
	     int dmlcount = this.getDao("boadao").update("tbtq_goal_ia001", param);	        
	     return dmlcount;
	 }
	 
	 protected int insertRate(PosRecord record){
		 
		 PosParameter param = new PosParameter();   
	     int i = 0;
	     
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// WHERE
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
	     param.setValueParamter(i++, record.getAttribute("SEQ"    		  ));
	     param.setValueParamter(i++, record.getAttribute("RATE"			  ));   // UPDATE              
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// INSERT
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
	     param.setValueParamter(i++, record.getAttribute("SEQ"    		  ));
	     param.setValueParamter(i++, record.getAttribute("RATE"			  ));             
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
	     int dmlcount = this.getDao("boadao").update("tbtq_rate_ia001", param);	        
	     return dmlcount;
	 }
	 
	 protected int insertBas(PosRecord record){
		 
		 PosParameter param = new PosParameter();   
		 int i = 0; 
		 
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// WHERE
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_A"		  ));   // UPDATE   
	     param.setValueParamter(i++, record.getAttribute("QTY_B"		  ));  
	     param.setValueParamter(i++, record.getAttribute("QTY_C"		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_D"		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_F"		  ));
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
	     param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));	// INSERT
	     param.setValueParamter(i++, record.getAttribute("CD"    		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_A"		  ));   
	     param.setValueParamter(i++, record.getAttribute("QTY_B"		  ));  
	     param.setValueParamter(i++, record.getAttribute("QTY_C"		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_D"		  ));
	     param.setValueParamter(i++, record.getAttribute("QTY_F"		  ));
	     param.setValueParamter(i++, SESSION_USER_ID);
	     
		 int dmlcount = this.getDao("boadao").update("tbtq_bas_ia001", param);	        
		 return dmlcount;
	 }
}
