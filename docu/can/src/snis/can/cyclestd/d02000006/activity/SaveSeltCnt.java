/*================================================================================
 * �ý���			: �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000006.activity.SaveSeltCnt.java
 * ���ϼ���		: �����ο����
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2008-05-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000006.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveSeltCnt extends SnisActivity{
	
	public SaveSeltCnt(){}
	
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
	       	        
	         sDsName = "dsSeltCnt";
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

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // �������� ����
        ds    = (PosDataset)ctx.get("dsSeltCnt");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
        	try 
        	{
        		nTempCnt = insertSeltCnt(record);
        		nSaveCount = nSaveCount + nTempCnt;
        
        	}
        	catch(Exception e)
        	{
        		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�1");
        	}
    
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
	 
	 protected int insertSeltCnt(PosRecord record) 
     {  

    	 PosParameter param = new PosParameter();   
    	 int i = 0;
         param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
         param.setValueParamter(i++, record.getAttribute("DT_FST_SELT"));
         param.setValueParamter(i++, record.getAttribute("DT_FST_SELT_END"));
         param.setValueParamter(i++, record.getAttribute("DT_SND_SELT"));
         param.setValueParamter(i++, record.getAttribute("DT_SND_SELT_END"));
         param.setValueParamter(i++, record.getAttribute("CNT_FST_SELT"));
         param.setValueParamter(i++, record.getAttribute("CNT_SND_SELT"));
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
         param.setValueParamter(i++, record.getAttribute("DT_FST_SELT"));
         param.setValueParamter(i++, record.getAttribute("DT_FST_SELT_END"));
         param.setValueParamter(i++, record.getAttribute("DT_SND_SELT"));
         param.setValueParamter(i++, record.getAttribute("DT_SND_SELT_END"));
         param.setValueParamter(i++, record.getAttribute("CNT_FST_SELT"));
         param.setValueParamter(i++, record.getAttribute("CNT_SND_SELT"));
         param.setValueParamter(i++, SESSION_USER_ID);

         
    	 int dmlcount = this.getDao("candao").insert("tbdb_selt_bas_ib001", param);
         
         return dmlcount;
     }
}
