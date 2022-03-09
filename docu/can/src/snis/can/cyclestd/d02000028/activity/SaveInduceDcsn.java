/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000024.activity.SaveInduceDcsn.java
 * ���ϼ���		: ������ ���� (����)
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000028.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ��ȫ��
* @version 1.0
*/

public class SaveInduceDcsn extends SnisActivity {
	
	public SaveInduceDcsn(){ }
	
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
         sDsName = "dsInduceListValue";

         logger.logInfo("codemanager start ==============");
         if ( ctx.get(sDsName) != null ) {
 	        ds    = (PosDataset)ctx.get(sDsName);
 	        nSize = ds.getRecordCount();
 	        for ( int i = 0; i < nSize; i++ ) 
 	        {
 	            PosRecord record = ds.getRecord(i);
 	            logger.logInfo(record);
 	        }
 	       logger.logInfo("---------------------------------------");
         }   
 	     saveState(ctx);
 	    logger.logInfo("codemanager end =================");
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
        ds    = (PosDataset)ctx.get("dsInduceListValue");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i); 
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
            	nDeleteCount = nDeleteCount + deleteInduceDcsn(record);
            }else{
        		nTempCnt = insertInduceDcsn(record);
        		nSaveCount = nSaveCount + nTempCnt;      
            }	
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
    /**
     * <p> �ĺ�������   </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertInduceDcsn(PosRecord record) 
    {  
   	 logger.logInfo("==========================  ������ �Է�   ============================");

   	 	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("RACE_NO"));
		 logger.logInfo(record.getAttribute("CAND_NO"));
         logger.logInfo(SESSION_USER_ID);
   	 
   	 logger.logInfo("==========================  ������ �Է�   ============================");
   	 
   	 PosParameter param = new PosParameter();   
   	 int i = 0;
	 
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));  
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));	//WHERE
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //UPDATE
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));  
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));	
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //INSERT
        
   	 int dmlcount = this.getDao("candao").insert("tbdb_induce_dcsn_ib001", param);
        
        return dmlcount;
    }
    
    protected int deleteInduceDcsn(PosRecord record){
   	 
   	 logger.logInfo("====================== ������  ���� ===========================");
        
	 logger.logInfo(record.getAttribute("RACE_YY"));
	 logger.logInfo(record.getAttribute("ROUND"));
	 logger.logInfo(record.getAttribute("RACE_NO"));

   	 logger.logInfo("===================== ������  ���� ============================");
   	 
   	 PosParameter param = new PosParameter();       					
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_YY"));
        param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO"));
        
   	
   	 int dmlcount =  this.getDao("candao").delete("tbdb_induce_dcsn_db001", param);
   	 return dmlcount;
    }
}
