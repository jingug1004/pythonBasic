/*================================================================================
 * �ý���			: �Խ��� ����
 * �ҽ����� �̸�	: snis.can.system.d01010060.activity.BoardManager.java
 * ���ϼ���		: �Խ��� ����
 * �ۼ���			: �ڿ���
 * ����			: 1.0.0
 * ��������		: 2007-11-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.system.d05000005.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ڿ���
* @version 1.0
*/
public class SaveBoard extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveBoard()
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

        sDsName = "dsBoardInsert";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	logger.logInfo("========== NSIZE " + (String) ctx.get("ISTYPE") + "=======================");
	        	
	         if( (String) ctx.get("ISTYPE") != null && ((String) ctx.get("ISTYPE")).equals("D") ) nTempCnt = deleteBoard(ctx);
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	 nTempCnt = updateBoard(record);
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if( ((String) record.getAttribute("ISTYPE")).equals("I") ) nTempCnt = insertBoard(record);
		        	if( ((String) record.getAttribute("ISTYPE")).equals("R") ) nTempCnt = replyBoard(record);
		        	//if( ((String) record.getAttribute("ISTYPE")).equals("M") ) nTempCnt = updateBoard(record);
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �Խñ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBoard(PosRecord record) 
    {
        PosParameter param = new PosParameter();

        String clsCd		= (String) record.getAttribute("CLS_CD");
        String nextSeqNo 	= (String) record.getAttribute("NEXTSEQNO");
        String grpNo 		= nextSeqNo;
        String grpLowNo 	= "0";
       
        logger.logInfo("========== clscd " + clsCd + "=======================");
        logger.logInfo("========== nextSeqNo " + nextSeqNo + "=======================");
        logger.logInfo("========== grpNo " + grpNo + "=======================");
        logger.logInfo("========== grpLowNo " + grpLowNo + "=======================");        
         
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, nextSeqNo);
        param.setValueParamter(i++, grpNo);
        param.setValueParamter(i++, "0");
        param.setValueParamter(i++, grpLowNo);
        param.setValueParamter(i++, record.getAttribute("MAKE_MAN"      	  ));
        param.setValueParamter(i++, record.getAttribute("SBJT"    		  ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"       ));
        param.setValueParamter(i++, "0");
        param.setValueParamter(i++, "001");
        param.setValueParamter(i++, "N");
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("candao").update("tbdm_bord_im001", param);
        
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

        this.getDao("candao").update("tbdm_bord_im002", param);
    }   
    
    
    /**
     * <p> �Խñ� �亯 </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int replyBoard(PosRecord record) 
    {
    	String grpNo 		= "";
    	String grpRankNo  	= "";
    	String grpLowNo		= ""; 
    	
        logger.logInfo("========== REPLY =======================");
        logger.logInfo("========== REPLY =======================");
 
    	grpNo 		= (String)record.getAttribute("GRP_NO");
    	grpRankNo   = (String)record.getAttribute("GRP_RANK_NO");
    	grpLowNo 	= (String)record.getAttribute("GRP_LOW_NO");
    	
        PosParameter param = new PosParameter();

    	int grpRankNoi 	= Integer.parseInt(grpRankNo);
    	int grpLowNoi 	= Integer.parseInt(grpLowNo);
    	
    	grpRankNoi 		= grpRankNoi 	+ 1;
    	grpLowNoi  		= grpLowNoi 	+ 1;
    	
        // �ڽź��� ���� grp_low_no update
    	updateGrpLowNo(grpNo, grpLowNoi);
    	
        int i = 0;
        param.setValueParamter(i++, (String) record.getAttribute("CLS_CD"));
        param.setValueParamter(i++, (String) record.getAttribute("NEXTSEQNO"));
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
        int dmlcount = this.getDao("candao").update("tbdm_bord_im003", param);
        
        return dmlcount;
    }


    /**
     * <p> �Խñ� Update </p>
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
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"      ));

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));

        int dmlcount = this.getDao("candao").update("tbdm_bord_um001", param);
        
        return dmlcount;
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
        // 
        i = 0; 
        param.setWhereClauseParameter(i++,ctx.get("SEQ_NO"));

        int dmlcount = this.getDao("candao").update("tbdm_bord_dm001", param);
        return dmlcount;
    }

}
