/*================================================================================
 * �ý���			: 1�� ���߽���  �հ�
 * �ҽ����� �̸�	: snis.can.system.d02000007.activity.SaveFstYN.java
 * ���ϼ���		: 1�� ���߽��� ����
 * �ۼ���			: 
 * ����			: 1.0.0
 * ��������		: 2008-05-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveFstYN extends SnisActivity{

	 public SaveFstYN(){}
	
	 public String runActivity(PosContext ctx)
	{
	
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	    PosDataset ds;
	    int        nSize        = 0;
	    String     sDsName      = "";
	    
	    sDsName = "dsPerioExam3";
	    if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
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
	        sDsName = "dsPerioExam3";
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		         for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);         
		                    
		                      
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
		            {
		            	nTempCnt = updateSaveFstYN(record);
		    	    }
		        
		            nSaveCount += nTempCnt;
		           		         
		        } // end for
	        }     // end if

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	 
	  protected int updateSaveFstYN(PosRecord record) 
	    {
	   	    logger.logInfo("==========================  1�� ���߽��� �հݿ���   ============================");
	                 
	   	    PosParameter param = null;
	        int i = 0;
	        int dmlcount = 0;

        	param = new PosParameter();
        	param.setValueParamter(i++, record.getAttribute("SUCCESS_YN"));
	        param.setValueParamter(i++, record.getAttribute("CAND_NO")); 
	        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO")); 
			dmlcount = this.getDao("candao").update("tbdb_perio_exam_fst_ub002", param);

	        return dmlcount;
	    }
}
