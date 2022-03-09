/*================================================================================
 * �ý���			: �μ�������ڼ���
 * �ҽ����� �̸�	: snis.rbm.business.rev1070.activity.SaveEmpEstm.java
 * ���ϼ���		: �μ������� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

public class SaveEmpEstm extends SnisActivity {
		public SaveEmpEstm(){}
		
		
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
	    	String sDsNameExp   = "";
	    	String sDsNameMnr   = "";
	    	String sDsNameSvr   = "";
	    	String sDsNameEstm1 = "";
	    	
	    	PosDataset ds;
	        int nSize          = 0;
	        int nEmpEstmCnt    = 0;
	        int nEmpEstmExpCnt = 0;
	        int nEmpEstmMnrCnt = 0;
	        int nEmpEstmSvrCnt = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        String sDeptCd   = (String)ctx.get("DEPT_CD");
	        String sReGrant  = (String)ctx.get("REGRANT");
	        
	    	//������ �Ǿ��ٸ� �Ұ���

    		if( getAprvYn(sEvalYear, sEvalNum, sDeptCd).equals("N") ) {
    			try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "���ο�û�� �Ǿ��� ������ �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
    		}
	        
	        
	        if( sReGrant.equals("N")) {
		        //�򰡰��õǾ��ٸ� ���� �Ұ���
		        if( getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
		        	try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "�� ���ð� ���۵Ǿ��� ������ ���� �Ұ����մϴ�.");
	            		
	            		return;
	            	}
		        }
	        }
	        
	        //�򰡸����� �Ǿ��ٸ� ���� �Ұ���
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        sDsName      = "dsEmpEstm";
	        sDsNameEstm1 = "dsEmpEstm1";
	        sDsNameExp   = "dsEmpEstmExp";
	        sDsNameMnr   = "dsEmpEstmMnr";
	        sDsNameSvr   = "dsEmpEstmSvr";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		        	PosRecord record = ds.getRecord(i);
		        	
		        	if(i==0)	refreshEmpEstm(record);	//�ʱ�ȭ
		        	
		        	if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {        	
		        		nEmpEstmCnt = updateEmpEstm(record);	        	
				        nSaveCount  = nSaveCount + nEmpEstmCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameMnr) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameMnr);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordMnr = ds.getRecord(i);
	            	refreshEmpEstmMnr(recordMnr);
		        	
		        }
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordMnr = ds.getRecord(i);

		            if ( recordMnr.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		            	recordMnr.setAttribute("APRV_SEQ", "2");
		            	updateEvAprv(recordMnr);
		        		nEmpEstmMnrCnt = updateEmpEstmMnr(recordMnr);
				        nSaveCount = nSaveCount + nEmpEstmMnrCnt;
			        	continue;
		            }
		        }	        
	        }
	        if ( ctx.get(sDsNameSvr) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameSvr);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordSvr = ds.getRecord(i);
	            	refreshEmpEstmSvr(recordSvr);
		        }
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordSvr = ds.getRecord(i);

		            if ( recordSvr.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		            	recordSvr.setAttribute("APRV_SEQ", "3");
		            	updateEvAprv(recordSvr);
		        		nEmpEstmExpCnt = updateEmpEstmSvr(recordSvr);
		        	
				        nSaveCount = nSaveCount + nEmpEstmSvrCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameEstm1) != null ) {
		        ds    = (PosDataset) ctx.get(sDsNameEstm1);
		        nSize = ds.getRecordCount();
		        		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            if(i==0) {
		            	refreshEmpEstm1(record);	//�ʱ�ȭ
		            	//deleteUser(record);		//�ӽû���ڷ� �߰�
		            }
		            
		            String sPassword = (String)record.getAttribute("RES_NO");
		            sPassword = EncryptSHA256.getEncryptSHA256(sPassword);
		            
		            record.setAttribute("PSWD", sPassword);
		            
		            String sEmpNo = (String)record.getAttribute("EMP_NO");
		            if( sEmpNo.trim().length() > 0 ) {
		            	insertUser(record);
		            }
		            
		        	updateEmpEstm1(record);
		        }	        
	        }
	        
	        //�߸ſ��� ���ֻ�������ý��ۿ� �α����� �� �ֵ��� �߰�
	        if ( ctx.get(sDsNameExp) != null ) {
		        ds    = (PosDataset) ctx.get(sDsNameExp);
		        nSize = ds.getRecordCount();
		        		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            String sEstmWkJob = (String)record.getAttribute("ESTM_WK_JOB");
		            if("1003".equals(sEstmWkJob)) {
			            String sPassword = (String)record.getAttribute("RES_NO");
			            sPassword = EncryptSHA256.getEncryptSHA256(sPassword);
			            record.setAttribute("PSWD", sPassword);
			            
			            String sEmpNo = (String)record.getAttribute("EMP_NO");
			            if( sEmpNo.trim().length() > 0 ) {
			            	insertUser(record);
			            }
		        	}	
		        }	        
	        }
	        
	        if ( ctx.get(sDsNameExp) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsNameExp);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord recordExp = ds.getRecord(i);

		            if ( recordExp.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	
		        		nEmpEstmExpCnt = updateEmpEstmExp(recordExp);
				        nSaveCount = nSaveCount + nEmpEstmExpCnt;
			        	continue;
		            }
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    
	    /**
	     * <p> �μ��� ����� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String getAprvYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s14", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));

	        return rtnKey;
	    }	    
	    
	    /**
	     * <p> ������������Ȳ ���� ���� �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int refreshEmpEstm(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));       // 3. �μ��ڵ�
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u01", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> ������������Ȳ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEstm(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EV_GBN"));          // 1. "1��2�� ���� ����" �� ���ڱ��а��� ����
	    	param.setValueParamter(i++, record.getAttribute("SNACK_ESTM_YN"));   // 1. "���� ���� ����
	    	param.setValueParamter(i++, SESSION_USER_ID);                        // 2. ����� ID
	    	
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. �μ��ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));        // 6. �����ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������������Ȳ ��������� ���� �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int refreshEmpEstm1(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));       // 3. �μ��ڵ�
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u09", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> ������������Ȳ ��������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEstm1(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;	
	    	param.setValueParamter(i++, record.getAttribute("EV_GBN"));          // 1. "1��2�� ���� ����" �� ���ڱ��а��� ����
	    	param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));       // 4. �����ڵ�
	    	param.setValueParamter(i++, SESSION_USER_ID);                        // 5. ����� ID
	    	
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 6. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 7. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 8. �μ��ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));        // 9. �����ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u10", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������������Ȳ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEstmExp(PosRecord recordExp)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordExp.getAttribute("ESTM_EMP"));        // 1. ����(B)
	    	param.setValueParamter(i++, recordExp.getAttribute("ESTM_WK_JOB"));     // 2. �򰡺���
	    	param.setValueParamter(i++, recordExp.getAttribute("DIV_NO"));         	// 3. ��ǥ�ҹ�ȣ
	    	param.setValueParamter(i++, recordExp.getAttribute("COMM_NO")); 		// 4. �����ڵ�
	    	param.setValueParamter(i++, recordExp.getAttribute("RELEA_ESC_YN")); 	// 5. �߸Ž����� ������ ����
	    	
	        param.setValueParamter(i++, SESSION_USER_ID);                          	// 6. ����� ID

	        i = 0;
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("ESTM_YEAR"));     // 7. �򰡳⵵
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("ESTM_NUM"));      // 8. ��ȸ��
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("DEPT_CD"));       // 9. �μ��ڵ�
	        param.setWhereClauseParameter(i++, recordExp.getAttribute("EMP_NO"));        // 10. �����ȣ	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_u03", param);
	        return dmlcount;
	    }

	    /**
	     * <p> �ٹ��µ� ���׸� ���� �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int refreshEmpEstmMnr(PosRecord recordMnr)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordMnr.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setValueParamter(i++, recordMnr.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setValueParamter(i++, recordMnr.getAttribute("DEPT_CD"));      // 6. �μ��ڵ�
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u04", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ� ���׸� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEstmMnr(PosRecord recordMnr)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordMnr.getAttribute("EV_GBN"));         // 1. ����(B)
	        param.setValueParamter(i++, SESSION_USER_ID);                         // 3. ����� ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("DEPT_CD"));      // 6. �μ��ڵ�
	        param.setWhereClauseParameter(i++, recordMnr.getAttribute("EMP_NO"));      // 7. �����ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u05", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���� ���׸� ���� �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int refreshEmpEstmSvr(PosRecord recordSvr)
	    {
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordSvr.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setValueParamter(i++, recordSvr.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setValueParamter(i++, recordSvr.getAttribute("DEPT_CD"));      // 6. �μ��ڵ�
	        
	    	int dmlcount = this.getDao("rbmdao").update("rev1070_u06", param);
	    	return dmlcount;
	    }
	    
	    /**
	     * <p> ���� ���׸� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEstmSvr(PosRecord recordSvr)
	    {			
	    	PosParameter param = new PosParameter();
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, recordSvr.getAttribute("EV_GBN"));         // 1. ����(B)
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 3. ����� ID

	        i = 0;
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("ESTM_NUM"));      // 5. ��ȸ��
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("DEPT_CD"));       // 6. �μ��ڵ�
	        param.setWhereClauseParameter(i++, recordSvr.getAttribute("EMP_NO"));        // 7. �����ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1070_u07", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� ������ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEvAprv(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. ���ο�û�� ���
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. ����� ID

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. �����ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("APRV_SEQ"));      // 6. ��������

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� ����� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		delete ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteUser(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	        param.setWhereClauseParameter(i++, record.getAttribute("TEAM_CD"));     //���ڵ�

	        int dmlcount = this.getDao("rbmdao").update("rev1070_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� �߰� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		insert ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertUser(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("PSWD")); 
	    	param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));
	    	param.setValueParamter(i++, SESSION_USER_ID); 
	    	param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	    	param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	    	param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	    	   
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ȭ�� ���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getUpdateYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s11", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_OPN_YN"));

	        return rtnKey;
	    }
}
