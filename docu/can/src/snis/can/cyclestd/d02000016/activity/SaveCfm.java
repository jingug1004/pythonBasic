/*================================================================================
 * �ý���			: �����Ʒ�����
 * �ҽ����� �̸�	: snis.can.system.d02000016.activity.SaveCfm.java
 * ���ϼ���		: �����Ʒ����� ����
 * �ۼ���			: �迵��
 * ����			: 1.0.0
 * ��������		: 2008-06-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

public class SaveCfm extends SnisActivity{

	public SaveCfm() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
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
       	        
         sDsName = "dsCfm";
         
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
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	     	     	
        int nSize        = 0;
        int nTempCnt     = 0;
               
        // �������� ����
        ds    = (PosDataset)ctx.get("dsCfm");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            
        	nTempCnt = updateCfg(record);
        	nSaveCount = nSaveCount + nTempCnt;
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
    
     protected int updateCfg(PosRecord record) 
     {
    	          
         PosParameter param = new PosParameter();       					
         int i = 0;
       
         param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
         param.setValueParamter(i++, record.getAttribute("DT"));
                                   
         int inscount = this.getDao("candao").insert("tbdb_trng_diary_ub005", param);
         
         return inscount;
     }     
}
