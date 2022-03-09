/*================================================================================
 * �ý���		: �л����
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.UserManager.java
 * ���ϼ���		: �ִ��� ���ص��
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-09
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000001.activity;

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
* @auther �ֹ���
* @version 1.0
*/

public class SaveWkStandard extends SnisActivity
{
	
	public SaveWkStandard() { }
	
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
       	        
         sDsName = "dsOutWkStandard";
         
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
        ds    = (PosDataset)ctx.get("dsOutWkStandard");
        nSize = ds.getRecordCount();
               
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
            {
                nTempCnt = updateWkStandard(record);
               	nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nTempCnt = insertWkStandard(record);
            	nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nTempCnt = deleteWkStandard(record);
            	nDeleteCount = nDeleteCount + nTempCnt; 
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> �ִ������� �Է�  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertWkStandard(PosRecord record) 
     {
    	          
         PosParameter param = new PosParameter();       					
         int i = 0;
                 
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("YY"))); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("MM"))); 
         param.setValueParamter(i++, record.getAttribute("WK"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                 
         int inscount = this.getDao("candao").insert("tbdb_cmpt_wk_std_ib001", param);
         
         return inscount;
     }
     
     
     /**
      * <p> �ִ������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateWkStandard(PosRecord record) 
     {
         PosParameter param = new PosParameter();       					
         int i = 0;
                           
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("YY"))); 
         param.setValueParamter(i++, Util.trim(record.getAttribute("MM")));          
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                  
         i = 0;
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("WK"));
               
         int updcount = this.getDao("candao").update("tbdb_cmpt_wk_std_ub001", param);
               
         return updcount;
     }
     
     /**
      * <p> �ִ�������  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteWkStandard(PosRecord record) 
     {
    	  	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("YY")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MM")));
         param.setWhereClauseParameter(i++, record.getAttribute("WK"));
         
         int delcount = this.getDao("candao").delete("tbdb_cmpt_wk_std_db001", param);
         
         return delcount;
     }
}
