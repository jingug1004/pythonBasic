/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000024.activity.SaveCandAssign.java
 * ���ϼ���		: �ĺ��� ���� (����)
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-20
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.can.cyclestd.d02000024.activity;

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

public class SaveCandAssign extends SnisActivity {
	
	public SaveCandAssign(){ }

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
         sDsName = "dsCandAssign";

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
        ds    = (PosDataset)ctx.get("dsCandAssign");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i); 
        	//try 
        	//{
        		nTempCnt = insertCandAssign(record);
        		nSaveCount = nSaveCount + nTempCnt;      
        	//}
        //	catch(Exception e)
        //	{
        //		Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�1");
        //	}
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
    protected int insertCandAssign(PosRecord record) 
    {  
   	 logger.logInfo("==========================  �ĺ������� �Է�   ============================");

   	 	 logger.logInfo(record.getAttribute("RACE_YY"));
		 logger.logInfo(record.getAttribute("ROUND"));
		 logger.logInfo(record.getAttribute("CAND_NO"));
		 logger.logInfo(record.getAttribute("MAT_CD"));
		 logger.logInfo(record.getAttribute("GBN"));
		 logger.logInfo(record.getAttribute("DCSN_GBN"));
		 logger.logInfo(record.getAttribute("RACE_CNT"));
		 logger.logInfo(record.getAttribute("TOT_AVG"));
		 logger.logInfo(record.getAttribute("TOT_RANK"));
         logger.logInfo(SESSION_USER_ID);
   	 
   	 logger.logInfo("==========================  �ĺ������� �Է�   ============================");
   	 
   	 PosParameter param = new PosParameter();   
   	 int i = 0;
	 
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND")); 
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));//WHERE
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //UPDATE
   	    param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("MAT_CD"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("DCSN_GBN"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));
        param.setValueParamter(i++, record.getAttribute("GRD"));
        param.setValueParamter(i++, record.getAttribute("SESSION_USER_ID"));  //INSERT
        
   	 int dmlcount = this.getDao("candao").insert("tbdb_cand_assign_ib001", param);
        
        return dmlcount;
    }
}
