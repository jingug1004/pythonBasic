/*================================================================================
 * �ý���			: ISO����ǥ�ذ���
 * �ҽ����� �̸�	: snis.boa.tqc.e08020010.activity.SaveIsoBord.java
 * ���ϼ���		: �Խ��� ����
 * �ۼ���			: ��ȫ��
 * ����			: 1.0.0
 * ��������		: 2008-09-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.tqc.e08020010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveIsoBord extends SnisActivity{

	protected String sStndYear = "";
    public SaveIsoBord()
    {

    }
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
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsInsertBoard";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("-------------------------------------- dsInsertBoard ----------------------");
	            logger.logInfo(record);
	            logger.logInfo("-------------------------------------- dsInsertBoard ----------------------");
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
     	String sDsName   = "";
     	
     	PosDataset ds;
         int nSize        = 0;
         int nTempCnt     = 0;

         sDsName = "dsInsertBoard";
         boolean isUpdate = false;
         if ( ctx.get(sDsName) != null ) {
 	        ds   		 = (PosDataset) ctx.get(sDsName);
 	        nSize 		 = ds.getRecordCount();
            
 	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
 	        	
 	         if( (String) ctx.get("ISTYPE") != null && ((String) ctx.get("ISTYPE")).equals("D") ) {
 	        	 nDeleteCount = deleteBoard(ctx);
 	         }

 	         for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);
 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
 		        	nTempCnt = updateBoard(record);
 		        	if(nTempCnt > 0) isUpdate = true;
 		        }
 		        
 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
 		        	if( ((String) record.getAttribute("ISTYPE")).equals("I") ) nTempCnt = insertBoard(record);
 		        	if( ((String) record.getAttribute("ISTYPE")).equals("R") ) nTempCnt = replyBoard(record, ctx);
 		        }

 		        nSaveCount = nSaveCount + nTempCnt;
 	        } // end for
         }
         
         
         sDsName = "dsUploadFile";
         if ( ctx.get(sDsName) != null ) {
 	        ds   		 = (PosDataset) ctx.get(sDsName);
 	        nSize 		 = ds.getRecordCount();
            
 	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
 	        	logger.logInfo("========== NSIZE " + (String) ctx.get("ISTYPE") + "=======================");
 	        	
 	         for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);

 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
 		        	nTempCnt = deleteFile(record,ctx);
 		        }

 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
 		        	if(isUpdate == true) nTempCnt = insertFileWithSeq(record, ctx);
 		        	else nTempCnt = insertFile(record, ctx);
 		        }

 		        nSaveCount = nSaveCount + nTempCnt;
 	        } // end for
         }        

         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     /**
      * <p> ISO ����ǥ�� Update </p>
      * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount int		update ���ڵ� ����
      * @throws  none
      */
     protected int updateBoard(PosRecord record)
     {
         PosParameter param = new PosParameter();
         int i = 0;
         param.setValueParamter(i++, record.getAttribute("SBJT"      ));
         param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
         param.setValueParamter(i++, SESSION_USER_ID);

         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));

         int dmlcount = this.getDao("boadao").update("tbtq_isobord_ua002", param);
         
         return dmlcount;
     }

     /**
      * <p> ISO ����ǥ�� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int insertBoard(PosRecord record) 
     {
         PosParameter param = new PosParameter();

         int i = 0;
         param.setValueParamter(i++, record.getAttribute("MAKE_MAN"      	  ));
         param.setValueParamter(i++, record.getAttribute("SBJT"    		  ));
         param.setValueParamter(i++, record.getAttribute("BORD_DESC"       ));
         param.setValueParamter(i++, "0");
         param.setValueParamter(i++, "001");
         param.setValueParamter(i++, "N");
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);
         int dmlcount = this.getDao("boadao").update("tbtq_isobord_ia001", param);
         
         return dmlcount;
     }


     /**
      * <p> �Խñ� �亯 </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int replyBoard(PosRecord record, PosContext ctx) 
     {
     	String grpNo 		= "";
     	String grpRankNo  	= "";
     	String grpLowNo		= ""; 
     	
         logger.logInfo("========== REPLY =======================");
         logger.logInfo("========== REPLY =======================");
  
     	grpNo 		= (String)ctx.get("GRP_NO");
     	grpRankNo   = (String)ctx.get("GRP_RANK_NO");
     	grpLowNo 	= (String)ctx.get("GRP_LOW_NO");
     	
         PosParameter param = new PosParameter();

     	int grpRankNoi 	= Integer.parseInt(grpRankNo);
     	int grpLowNoi 	= Integer.parseInt(grpLowNo);
     	
     	grpRankNoi 		= grpRankNoi 	+ 1;
     	grpLowNoi  		= grpLowNoi 	+ 1;
     	
         // �ڽź��� ���� grp_low_no update
     	updateGrpLowNo(grpNo, grpLowNoi);
     	
         int i = 0;
         param.setValueParamter(i++, grpNo);
         param.setValueParamter(i++, Integer.toString(grpRankNoi) );
         param.setValueParamter(i++, Integer.toString(grpLowNoi)  );
         param.setValueParamter(i++, record.getAttribute("MAKE_MAN"      ));
         param.setValueParamter(i++, "RE:"+record.getAttribute("SBJT"      ));
         param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
         param.setValueParamter(i++, "0");
         param.setValueParamter(i++, "002");
         param.setValueParamter(i++, "N");
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);
         int dmlcount = this.getDao("boadao").update("tbtq_isobord_ra001", param);
         
         return dmlcount;
     }

     
     
     /**
      * <p> �Խñ� �亯 ��ó���۾� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected void updateGrpLowNo(String grpNo, int grpLowNo) 
     {
     	
     	
         PosParameter param = new PosParameter();

         int i = 0;
         param.setWhereClauseParameter(i++, grpNo);
         param.setWhereClauseParameter(i++, Integer.toString(grpLowNo));

         this.getDao("boadao").update("tbtq_isobord_ia002", param);
     }
     
     
     
     /**
      * <p> �Խñ� ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int deleteBoard(PosContext ctx)
     {
     	logger.logInfo("========== DELETEBOARD =======================");
     	logger.logInfo("========== DELETEBOARD =======================");
     	logger.logInfo("========== DELETEBOARD =======================");
     	
     	PosParameter param = new PosParameter();
         int i = 0;
         param.setValueParamter(i++, "Y");
         
         i = 0; 
         param.setWhereClauseParameter(i++,ctx.get("SEQ_NO"));

         int dmlcount = this.getDao("boadao").update("tbtq_isobord_da001", param);
         return dmlcount;
     }

     
     
     
     /**
      * <p> File �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int insertFile(PosRecord record, PosContext ctx) 
     {
         PosParameter param = new PosParameter();
         logger.logDebug(" ================== Insert File Start =================== ");
          
         int i = 0;
   
         param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
         param.setValueParamter(i++, record.getAttribute("FILE_URL") );
         param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("boadao").update("tbtq_isobord_ia003", param);
         
         logger.logDebug(" ================== Insert File End =================== ");
         return dmlcount;
     }

     
     
     /**
      * <p> File �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int insertFileWithSeq(PosRecord record, PosContext ctx) 
     {
         PosParameter param = new PosParameter();
         logger.logDebug(" ================== Insert File Start =================== ");
          
         int i = 0;

         param.setValueParamter(i++, ctx.get("SEQ_NO"));
         param.setValueParamter(i++, record.getAttribute("FILE_NAME") );
         param.setValueParamter(i++, record.getAttribute("FILE_URL") );
         param.setValueParamter(i++, record.getAttribute("FILE_SIZE") );
         param.setValueParamter(i++, SESSION_USER_ID);
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("boadao").update("tbtq_isobord_ia004", param);
         
         logger.logDebug(" ================== Insert File End =================== ");
         return dmlcount;
     }
     
     
     /**
      * <p> File ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int deleteFile(PosRecord record,  PosContext ctx) 
     {
         PosParameter param = new PosParameter();
         logger.logDebug(" ================== Delete File Start =================== ");
          
         int i = 0;
         param.setWhereClauseParameter(i++, (String) ctx.get("SEQ_NO")   );
         param.setWhereClauseParameter(i++, record.getAttribute("FILE_SEQ") );
         
         int dmlcount = this.getDao("boadao").update("tbtq_isobord_da002", param);
         
         logger.logDebug(" ================== Delete File End =================== ");
         return dmlcount;
     }


}
