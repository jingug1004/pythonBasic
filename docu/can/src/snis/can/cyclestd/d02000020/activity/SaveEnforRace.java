/*================================================================================
 * �ý���		: �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000023.activity.SaveEnforRace.java
 * ���ϼ���		: ������ֵ��
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2007-03-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000020.activity;

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

public class SaveEnforRace extends SnisActivity {

	public SaveEnforRace() { }
	
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
       	        
         sDsName = "dsRaceListValue";
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
        ds    = (PosDataset)ctx.get("dsRaceListValue");
        nSize = ds.getRecordCount();
          
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) 
            {
            	//Util.setSvcMsg(ctx, "�����Ҽ� �����ϴ� ���� �� ����ϼ���!");
                 nTempCnt = updateEnforRace(record);
               	 nSaveCount = nSaveCount + nTempCnt;
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	//try 
            	//{
            		nTempCnt = insertEnforRace(record);
            		nSaveCount = nSaveCount + nTempCnt;
            
            	//}
            	//catch(Exception e)
            	//{
            		//Util.setSvcMsg(ctx, "�̹� ��ϵ� �ڷᰡ �����մϴ�.");
            		//e.printStackTrace();
            	//}
            }
            else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
                // delete
            	nDeleteCount = nDeleteCount + deleteEnforRace(record);
            }
              
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     /**
      * <p> ������ֵ�� �Է�  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertEnforRace(PosRecord record) 
     {
    	 logger.logInfo("==========================  ������ֵ�� �Է�   ============================");
    	 
    	 logger.logInfo(record.getAttribute("MAT_CD"));
         logger.logInfo(record.getAttribute("RACE_GRD_CD"));
         logger.logInfo(record.getAttribute("RACE_CNT"));
//       logger.logInfo(SESSION_USER_ID);
    	 
    	 logger.logInfo("==========================  ������ֵ�� �Է�   ============================");
                 
         PosParameter param = new PosParameter();       					
         int i = 0;
         
         param.setValueParamter(i++, record.getAttribute("MAT_CD"));
         param.setValueParamter(i++, record.getAttribute("RACE_GRD_CD"));
         param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
         param.setValueParamter(i++, "admin");
                 
         int dmlcount = this.getDao("candao").insert("tbdb_enfor_race_ib001", param);
         
         return dmlcount;
     }
     /**
      * <p> ������ֵ�� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateEnforRace(PosRecord record) 
     {
     	
         PosParameter param = new PosParameter();       					
         int i = 0;
         
    	 logger.logInfo("==========================  ������ֵ�� ����   ============================");
    	 
         logger.logInfo(record.getAttribute("RACE_CNT"));
        // logger.logInfo(SESSION_USER_ID);
    	 logger.logInfo(record.getAttribute("MAT_CD"));
         logger.logInfo(record.getAttribute("RACE_GRD_CD"));
    	 
    	 
    	 
    	 logger.logInfo("==========================  ������ֵ�� ����   ============================");
                  
         param.setValueParamter(i++, record.getAttribute("RACE_CNT"));
         param.setValueParamter(i++, "admin");
                  
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("MAT_CD"));
         param.setWhereClauseParameter(i++, record.getAttribute("RACE_GRD_CD"));
               
         int dmlcount = this.getDao("candao").update("tbdb_enfor_race_ub001", param);
               
         return dmlcount;
     }
     
     /**
      * <p> �ִ�������  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteEnforRace(PosRecord record) 
     {
    	 logger.logInfo("====================== ������ֵ��  ���� ===========================");
         
    	 logger.logInfo(record.getAttribute("MAT_CD"));
    	 logger.logInfo(record.getAttribute("RACE_GRD_CD"));
    	 
    	 logger.logInfo("===================== ������ֵ��  ���� ============================");
    	 
    	 PosParameter param = new PosParameter();       					
         int i = 0;
                             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MAT_CD")));
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_GRD_CD")));
                 
         int dmlcount = this.getDao("candao").delete("tbdb_enfor_race_db001", param);
         
         
         return dmlcount;
     }
}
