/*================================================================================
 * �ý���			: �Խ��� ����
 * �ҽ����� �̸�	: snis.boa.system.e01010060.activity.BoardManager.java
 * ���ϼ���		: �Խ��� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-11-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010061.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �迵ö
* @version 1.0
*/
public class SaveProcessProg extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveProcessProg()
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

        sDsName = "dsInsertBoard";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        	logger.logInfo("========== NSIZE " + nSize + "=======================");
	        	
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			         if( (String) ctx.get("ISTYPE") != null && ((String) ctx.get("ISTYPE")).equals("D") ) {
			        	 nDeleteCount = deleteBoard(ctx);
			         } else {
			        	 nTempCnt = updateProcessProg(record);
			         }
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if( ((String) record.getAttribute("ISTYPE")).equals("I") ) nTempCnt = insertProcessProg(record);
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        } // end for
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> �Խñ� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateProcessProg(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SBJT"      ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"      ));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"      ));
        param.setValueParamter(i++, record.getAttribute("TMS"      ));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"      ));
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));

        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ua001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Խñ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertProcessProg(PosRecord record) 
    {
        PosParameter param = new PosParameter();

        String clsCd		= (String) record.getAttribute("CLS_CD");
       
        logger.logInfo("========== clscd " + clsCd + "=======================");
        
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, record.getAttribute("SBJT"    		  ));
        param.setValueParamter(i++, record.getAttribute("BORD_DESC"       ));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"       ));
        param.setValueParamter(i++, record.getAttribute("TMS"       	  ));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"         ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ia001", param);
        
        return dmlcount;
    }
    
    
    /*
     * ���μ��� ������Ȳ insert Method
     */
    public int insertProcessProg(String clsCd, String stndYear, String tms, String day_ord, String sbjt, String rmk) 
    {
    	/*
    	 * 001 : �ý��۰���
    	 * 002 : ������
    	 * 003 : ��������
    	 * 004 : ���ǰ���
    	 * 005 : ��������
    	 * 006 : ������
    	 * 007 : ��ݰ���
    	 */
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, clsCd);
        param.setValueParamter(i++, sbjt);
        param.setValueParamter(i++, rmk);
        param.setValueParamter(i++, stndYear);
        param.setValueParamter(i++, tms);
        param.setValueParamter(i++, day_ord);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ia001", param);
        
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
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0; 
        param.setWhereClauseParameter(i++,ctx.get("SEQ_NO"));

        int dmlcount = this.getDao("boadao").update("tbea_proc_prog_ua002", param);
        return dmlcount;
    }
    
 }
