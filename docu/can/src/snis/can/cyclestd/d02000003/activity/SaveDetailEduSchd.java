/*================================================================================
 * �ý���			: ���α�������
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000003.activity;

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

public class SaveDetailEduSchd extends SnisActivity 
{
	public SaveDetailEduSchd() { }
	
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
    
        sDsName = "dsDetlEduAssign";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("------------------->"+record);
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
     	PosDataset ds4;
     	
        int nSize        = 0;
        int nTempCnt     = 0;
        String sDsName   = "";
        
        // �ð�����ǥ ����       
        ds4   = (PosDataset) ctx.get("dsDetlEduAssign");
        nSize = ds4.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
        logger.logInfo("nTempCnt1------------------->"+nTempCnt);   
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds4.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {
            	 if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            		 nTempCnt = insertDetl_Edu_Assign(record);
            	 }else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            		 nTempCnt = updateDetl_Edu_Assign(record);
                 }              	 
/*
            	 if ( (nTempCnt = updateDetl_Edu_Assign(record)) == 0 ) {
                 	nTempCnt = insertDetl_Edu_Assign(record);
                 }            	
 */           	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteDetl_Edu_Assign(record);
             }
         }
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> ���α������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
     protected int insertDetl_Edu_Assign(PosRecord record) 
     {
     	logger.logInfo("insertEduAssign ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
 
         String sDt 	= Util.trim(record.getAttribute("DT"));
         String sYear 	= "";
         String sMm		= "";
         sDt = Util.NVL(sDt,"");
         if(!sDt.equals("")){
        	 sYear	= sDt.substring(0,4);
        	 sMm 	= "0"+sDt.substring(4,6); 
        	 logger.logInfo("insertDetl_Edu_Assign sYear===="+sYear);
        	 logger.logInfo("insertDetl_Edu_Assign sMM===="+sMm);
         }
          
         
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, sYear);
         param.setValueParamter(i++, sMm);
         param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
         param.setValueParamter(i++, record.getAttribute("EDU_TIME"));
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         
         int dmlcount = this.getDao("candao").insert("tbdb_detl_edu_assign_ib001", param);
         
         logger.logInfo("insertEduAssign ============================");
         return dmlcount;
     }
     
     /**
      * <p> ���α������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateDetl_Edu_Assign(PosRecord record) 
     {
     	logger.logInfo("updateDetl_Edu_Assign ============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
         
/*         
  		String test1 = Util.trim(record.getAttribute("MM")); 
		String test4 = Util.trim(record.getAttribute("DT"));
		String test5 = Util.trim(record.getAttribute("STR_END_TIME"));
		String test6 = Util.trim(record.getAttribute("SECTN_CD"));
		String test7 = Util.trim(record.getAttribute("CRS_CD"));
		String test8 = Util.trim(record.getAttribute("DETL_EDU_CRS"));
		String test9 = Util.trim(record.getAttribute("OBJ"));
		String test10 = Util.trim(record.getAttribute("LECTR"));
		String test11 = Util.trim(record.getAttribute("PLC"));
		String test13 = Util.trim(record.getAttribute("RACER_PERIO_NO"));
		String test14 = String.valueOf(record.getAttribute("SEQ"));

logger.logInfo("updateDetl_Edu_Assign test1 MM ============================"+test1 ); 
logger.logInfo("updateDetl_Edu_Assign test4 ============================"+test4 ); 
logger.logInfo("updateDetl_Edu_Assign test5 ============================"+test5 ); 
logger.logInfo("updateDetl_Edu_Assign test6 ============================"+test6 ); 
logger.logInfo("updateDetl_Edu_Assign test7 ============================"+test7 ); 
logger.logInfo("updateDetl_Edu_Assign test8 ============================"+test8 ); 
logger.logInfo("updateDetl_Edu_Assign test9 ============================"+test9 ); 
logger.logInfo("updateDetl_Edu_Assign test10============================"+test10); 
logger.logInfo("updateDetl_Edu_Assign test11============================"+test11); 
logger.logInfo("updateDetl_Edu_Assign test12========"+record.getAttribute("EDU_TIME")); 
logger.logInfo("updateDetl_Edu_Assign test13 ============================"+test13 ); 
logger.logInfo("updateDetl_Edu_Assign test14 ============================"+test14 );          
*/         
		String sDt 	= Util.trim(record.getAttribute("DT"));
		String sYear 	= "";
		String sMm		= "";
		sDt = Util.NVL(sDt,"");
		if(!sDt.equals("")){
			 sYear	= sDt.substring(0,4);
			 sMm 	= "0"+sDt.substring(4,6); 
			 logger.logInfo("insertDetl_Edu_Assign sYear===="+sYear);
			 logger.logInfo("insertDetl_Edu_Assign sMM===="+sMm);
		}
		
		param.setValueParamter(i++, sYear);
		param.setValueParamter(i++, sMm);  
		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("STR_END_TIME")));  
		param.setValueParamter(i++, Util.trim(record.getAttribute("SECTN_CD")));   
		param.setValueParamter(i++, Util.trim(record.getAttribute("CRS_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("DETL_EDU_CRS"))); 
		param.setValueParamter(i++, Util.trim(record.getAttribute("OBJ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("LECTR")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("PLC")));
		param.setValueParamter(i++, record.getAttribute("EDU_TIME")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

		int dmlcount = this.getDao("candao").update("tbdb_detl_edu_assign_ub001", param);

		logger.logInfo("updateDetl_Edu_Assign ============================");
		return dmlcount;
     }
     
 
     /**
      * <p> �ð�����ǥ  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteDetl_Edu_Assign(PosRecord record) 
     {
    	 logger.logInfo("deleteDetl_Edu_Assign start============================");
         PosParameter param = new PosParameter();       					
         int i = 0;
             
         param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
         int dmlcount = this.getDao("candao").delete("tbdb_detl_edu_assign_db001", param);
         
         logger.logInfo("deleteDetl_Edu_Assign end============================");
         return dmlcount;
     }
}
