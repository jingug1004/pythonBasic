/*================================================================================
 * �ý���			: ���ΰ���
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveAprvMana.java
 * ���ϼ���		: �����ڵ� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-26
 * ������������	: 2014.6.26 
 * ����������		: ���缱
 * ������������	: �����̷°��� ��� �߰�
=================================================================================*/
package snis.rbm.business.rev1050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;
import snis.rbm.business.rev1070.activity.*;
import snis.rbm.business.rev1080.activity.*;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveAprvMana extends SnisActivity {
		public SaveAprvMana(){}
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

	    protected void saveState(PosContext ctx) 
	    {
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        String sAprvCd   = (String)ctx.get("APRVCD");
	        String sAprvDate = (String)ctx.get("APRVDATE");
	        String sAprvDpet = (String)ctx.get("APRVDEPT");
	        String sMenuId   = (String)ctx.get("MENU_ID");
	        String sMngDept  = getMngDept(sEvalYear, sEvalNum);
	        
	        if(sMenuId.equals("REV1080")) {	//�ٸ����ڱ׷켱��
	        	if( sAprvCd.equals("01")) {	//���ο�û
		        	updateAprv(sEvalYear, sEvalNum, "002", sMngDept, "13", "");
		        } else if( sAprvCd.equals("02") ) {	//ȸ��
		        	updateAprv(sEvalYear, sEvalNum, "001", sMngDept, "13", "");
		        	//�ٸ���  ����
		        	SaveMultAppr SaveMultAppr = new SaveMultAppr();
		        	SaveMultAppr.deleteMultEval(sEvalYear, sEvalNum, "");
		        } else if( sAprvCd.equals("03") ) {	//����	
		        	updateAprv(sEvalYear, sEvalNum, "003", sMngDept, "13", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//�ݷ�
		        	updateAprv(sEvalYear, sEvalNum, "001", sMngDept, "13", "");
		        } else if( sAprvCd.equals("05") ) {	//�������
		        	//ȭ�鰳�õǾ��ٸ� �Ұ���
		        	SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
			        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
			        	try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�� ���ð� ���۵Ǿ��� ������ ������Ұ� �Ұ����մϴ�.");
		            		
		            		return;
		            	}
			        }
		        	updateAprv(sEvalYear, sEvalNum, "002", sMngDept, "13", "");
		        }
	        } else if( sMenuId.equals("REV1090")) {	//�μ�������ڼ���
	        	if( sAprvCd.equals("01")) {	//Ȯ����û
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "4", sAprvDate);
		        } else if( sAprvCd.equals("03") ) {	//ȸ��
		        	//�μ������ڼ������� '����'�� ���ٸ� ȸ�� �Ұ���
		        	if( getAprvYn(sEvalYear, sEvalNum, sAprvDpet).equals("N") ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "[ �μ������ڼ��� ]�� ���ڰ� ���εǾ��� ������ [ Ȯ�� ��� ]�Ͻ� �� �����ϴ�.");
		            		
		            		return;
		            	}
		        	}
			        
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "4", "");
		        	//����������, �ٹ��µ���, ������ ����
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);
		        } 
	        } else if(sMenuId.equals("REV1070")) {	//�μ����򰡼���
	        	if( sAprvCd.equals("01")) {	//���ο�û
	    	        
	        		if( !(getCfmUpdateYn(sEvalYear, sEvalNum, sAprvDpet) > 0) ) {
	        			try { 
	            			throw new Exception(); 
	                	} catch(Exception e) {       		
	                		this.rollbackTransaction("tx_rbm");
	                		Util.setSvcMsg(ctx, "�򰡴������� �� �Ŀ� ������ּ���.");
	                		
	                		return;
	                	}
	        		}
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "1", "");
		        } else if( sAprvCd.equals("02") ) {	//ȸ��
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "1", "");
		        	
		        	//����������, �ٹ��µ���, ������ ����
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);	
		        } else if( sAprvCd.equals("03") ) {	//����
		        	if( !(getAprvReqYn(sEvalYear, sEvalNum, sAprvDpet) > 0) ) {
	        			try { 
	            			throw new Exception(); 
	                	} catch(Exception e) {       		
	                		this.rollbackTransaction("tx_rbm");
	                		Util.setSvcMsg(ctx, "���ο�û���°� �ƴմϴ�.");
	                		
	                		return;
	                	}
	        		}
		        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "1", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//�ݷ�
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "1", "");
		        	//����������, �ٹ��µ���, ������ ����
		        	SaveAppr SaveAppr = new SaveAppr();
		        	SaveAppr.deleteEval(sEvalYear, sEvalNum, sAprvDpet);
		        } else if( sAprvCd.equals("05") ) {	//�������
		        	
		        	//ȭ�鰳�õǾ��ٸ� �Ұ���
		        	SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
			        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
			        	try { 
		        			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�� ���ð� ���۵Ǿ��� ������ ������Ұ� �Ұ����մϴ�.");
		            		
		            		return;
		            	}
			        }
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "1", "");
		         }
	        } else if(sMenuId.equals("REV2020")) {	//�ٹ��µ���
	        	if( sAprvCd.equals("01")) {	//���ο�û
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("02") ) {	//ȸ��
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("03") ) {	//����	        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "2", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//�ݷ�
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "2", "");
		        } else if( sAprvCd.equals("05") ) {	//�������			        
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
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "2", "");
		        }
	        } else if(sMenuId.equals("REV2030")) {	//������
	        	if( sAprvCd.equals("01")) {	//���ο�û
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("02") ) {	//ȸ��
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("03") ) {	//����	        	
		        	updateAprv(sEvalYear, sEvalNum, "003", sAprvDpet, "3", sAprvDate);
		        } else if( sAprvCd.equals("04") ) {	//�ݷ�
		        	updateAprv(sEvalYear, sEvalNum, "001", sAprvDpet, "3", "");
		        } else if( sAprvCd.equals("05") ) {	//�������
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
		        	updateAprv(sEvalYear, sEvalNum, "002", sAprvDpet, "3", "");
		        }
	        }
	    }
	    
	    /**
	     * <p> ���λ��� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAprv(String sEvalYear, String sEvalNum, String SAprvStas, String sDeptCd, String sAprvSeq, String SAprvDate)
	    {		
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, SAprvDate);            // 1. ���γ�¥
	    	param.setValueParamter(i++, SAprvStas);            // 1. ���λ���
	        param.setValueParamter(i++, SESSION_USER_ID);      // 2. ����� ID

	        i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, sEvalNum);      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, sDeptCd);       // 5. �����ȣ
	        param.setWhereClauseParameter(i++, sAprvSeq);      // 6. ��������
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1050_u02", param);
	        
	        return dmlcount;
	    }

	    /**
	     * <p> �μ��� ���� ���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int getCfmUpdateYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s07", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        
	        return Integer.valueOf(rtnKey).intValue();
	    }

	    /**
	     * <p> �μ��� ���� ���ο�û���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int getAprvReqYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s16", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        
	        return Integer.valueOf(rtnKey).intValue();
	    }
	    /**
	     * <p> ��� �μ� �˻� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getMngDept(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1080_s05", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("MNG_DEPT"));
	        
	        return rtnKey;
	    }
	    
	    /**
	     * <p> �μ��� ���� ���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getAprvYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s13", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));
	        
	        return rtnKey;
	    }
	    
	    /* ***********************************************************************************
	              ���Ѻο�
	    *********************************************************************************** */ 
	    /**
	     * <p> �μ��� �ý��� ��� ���� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyDeptHead(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0; //setValueParamter
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� �޴� ��� ���� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuDeptHead(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, 
	    		                           String sAproYn, String sUserId, String curDtm)
	    {		
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
			
	        this.getDao("rbmdao").update("rev1080_u03", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i04", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� �޴� ��� ���� �ο�(�ٹ��µ���,������) </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuDeptHeadMnrSvr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, 
	    		                                 String sAproYn, String sItemCd, String sUserId, String curDtm)
	    {	
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);	    	
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sItemCd);			
	        this.getDao("rbmdao").update("rev1080_u05", param);	        
	        
	        // 2) �ű� ���� �ο�
	        param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sItemCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i19", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� �ý��� ��� ���� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyMng(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� �޴� ��� ���� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuMng(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {			
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);			
	        this.getDao("rbmdao").update("rev1080_u04", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();
	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1�� ���� �ý��� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyReleaMng(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1�� ���� �ý��� ��� ���� ��ü �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyReleaMngAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i13", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1�� ���� �޴� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuReleaMng(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                           String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {		
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);
	        //Union ����	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u09", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();
	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        //Union ����
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1�� ���� �޴� ��� ���� ��ü �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuReleaMngAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                              String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {
    		/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //Union ����	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
			
	        this.getDao("rbmdao").update("rev1080_u06", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        //Union ����	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i14", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� �ý��� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyMnr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� �ý��� ��� ���� ��ü �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplyMnrAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i15", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� �޴� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuMnr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                      String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {	
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u10", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();
	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i10", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� �޴� ��� ���� ��ü �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuMnrAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                         String sInst_yn, String sAproYn, String sUserId, String curDtm)
	    {	
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        			
	        this.getDao("rbmdao").update("rev1080_u07", param);
	        
	        // 2) �ű� ���� �Է�
	    	
	    	param = new PosParameter();
	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i12", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� �ý��� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplySvr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i11", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� �ý��� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthAplySvrAll(String sEvalYear, String sEvalNum, String sUserId)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i17", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� �޴� ��� ���� �μ��� �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuSvr(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                      String sInst_yn, String sAproYn, String sEvDept, String sUserId, String curDtm)
	    {			
	    	/* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, curDtm);        
	        param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);	
	        param.setValueParamter(i++, sEvDept);		
	        this.getDao("rbmdao").update("rev1080_u11", param);
	        
	        // 2) �ű� ���� �Է�
	    	param = new PosParameter();	    	
	    	i = 0; 
	    	param.setValueParamter(i++, sMenuId);
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);	        
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i16", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� �޴� ��� ���� ��ü �ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertAuthMuSvrAll(String sEvalYear, String sEvalNum, String sMenuId, String sSrchYn, 
	    		                         String sInst_yn, String sAproYn,   String sUserId, String curDtm)
	    {
	        /* ���� ������ ������ �ű� ���� �ο� */
	    	
	    	// 1)�ش� ����ڵ��� ������ ��� ����.. ������ ����...
	        PosParameter param = new PosParameter();       					
	    	param = new PosParameter();
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sUserId);
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sMenuId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        			
	        this.getDao("rbmdao").update("rev1080_u08", param);
	        
	        // 2) �ű� ���� �Է�	    	
	    	param = new PosParameter();	    	
	    	i = 0; //setValueParamter
	    	param.setValueParamter(i++, sMenuId);
	    	param.setValueParamter(i++, curDtm);	    	
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInst_yn);
	        param.setValueParamter(i++, sAproYn);
	        param.setValueParamter(i++, sEvalYear+"�⵵ "+sEvalNum+"ȸ�� ����");
	        param.setValueParamter(i++, sUserId);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i18", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���� ȸ�� </p>
	     * @param   
	     * @return  
	     * @throws  none
	     */
	    public void deleteAuthMu(String sEvalYear, String sEvalNum, String sEvDept, int nStatus, String sUserId) {

	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	if( nStatus == 1) {	//�μ���, �����
	    		//ȭ�� ���� ȸ��
	    		deleteAuthMuDeptHead(sEvalYear, sEvalNum, sUserId, curDateTime);	//�μ��� ����ȸ��
		    	deleteAuthMuMng     (sEvalYear, sEvalNum, sUserId, curDateTime);	//����� ����ȸ��
		    	
		    	//�ý��� ������ ȸ��
		    	deleteAuthAplyDeptHead(sEvalYear, sEvalNum); //�μ��� ����ȸ��
		    	deleteAuthAplyMng     (sEvalYear, sEvalNum); //����� ����ȸ��
	    	} else if( nStatus == 2) {	//��ǥ����, 1������, �ٹ��µ�����, ��������
	    		//ȭ�� ���� ȸ��
	    		deleteAuthMuReleaMng(sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//��ǥ����, 1�� ���� ����ȸ��
		    	deleteAuthMuMnr     (sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//�ٹ��µ����� ����ȸ��
		    	deleteAuthMuSvr     (sEvalYear, sEvalNum, sEvDept, sUserId, curDateTime);	//�������� ����ȸ��
		    	
		    	//�ý��� ������ ȸ��
		    	deleteAuthAplyReleaMng(sEvalYear, sEvalNum, sEvDept); //��ǥ����, 1�� ���� ����ȸ��
		    	deleteAuthAplyMnr     (sEvalYear, sEvalNum, sEvDept); //�ٹ��µ����� ����ȸ��
		    	deleteAuthAplySvr     (sEvalYear, sEvalNum, sEvDept); //�������� ����ȸ��
	    	} else if( nStatus == 3) {	//��ü
	    		//ȭ�� ���� ȸ��
	    		deleteAuthMuDeptHead(sEvalYear, sEvalNum, sUserId, curDateTime);	//�μ��� ����ȸ��
		    	deleteAuthMuMng     (sEvalYear, sEvalNum, sUserId, curDateTime);	//����� ����ȸ��
		    	deleteAuthMuReleaMng(sEvalYear, sEvalNum, "", sUserId, curDateTime);	//��ǥ����, 1�� ���� ����ȸ��
		    	deleteAuthMuMnr     (sEvalYear, sEvalNum, "", sUserId, curDateTime);	//�ٹ��µ����� ����ȸ��
		    	deleteAuthMuSvr     (sEvalYear, sEvalNum, "", sUserId, curDateTime);	//�������� ����ȸ��
		    	
		    	//�ý��� ������ ȸ��
		    	deleteAuthAplyDeptHead(sEvalYear, sEvalNum); //�μ��� ����ȸ��
		    	deleteAuthAplyMng     (sEvalYear, sEvalNum); //����� ����ȸ��
		    	deleteAuthAplyReleaMng(sEvalYear, sEvalNum, ""); //��ǥ����, 1�� ���� ����ȸ��
		    	deleteAuthAplyMnr     (sEvalYear, sEvalNum, ""); //�ٹ��µ����� ����ȸ��
		    	deleteAuthAplySvr     (sEvalYear, sEvalNum, ""); //�������� ����ȸ��
	    	} else if( nStatus == 4) {	//��ǥ����, 1������, �ٹ��µ�����, ��������
	    		//ȭ�� ���� ȸ��
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2010", sUserId, curDateTime);	//���������� ����ȸ��
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2020", sUserId, curDateTime);	//�ٹ��µ����� ����ȸ��
	    		deleteAuthMuOne(sEvalYear, sEvalNum, sEvDept, "REV2030", sUserId, curDateTime);	//�������� ����ȸ��
		    	
		    	//�ý��� ������ ȸ��
		    	//deleteAuthAplyReleaMng(sEvalYear, sEvalNum, sEvDept); //��ǥ����, 1�� ���� ����ȸ��
		    	//deleteAuthAplyMnr     (sEvalYear, sEvalNum, sEvDept); //�ٹ��µ����� ����ȸ��
		    	//deleteAuthAplySvr     (sEvalYear, sEvalNum, sEvDept); //�������� ����ȸ��
	    	}
	    }
	    
	    /**
	     * <p> �μ��� ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuDeptHead(String sEvalYear, String sEvalNum, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuMng(String sEvalYear, String sEvalNum, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1������ ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuReleaMng(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuMnr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuSvr(String sEvalYear, String sEvalNum, String sEvDept, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� �ý��� ������ ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthAplyDeptHead(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����� �ý��� ������ ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthAplyMng(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> 1������, ��ǥ���� �ý��� ������ ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthAplyReleaMng(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ����� �ý��� ������ ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthAplyMnr(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d09", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �������� �ý��� ������ ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthAplySvr(String sEvalYear, String sEvalNum, String sEvDept)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvDept);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d10", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1������, ����, ���� ���� ��ü ��ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int reWorkEval(String sEvalYear, String sEvalNum, String sUserId) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	//���� �ο�
        	//��ǥ����, 1�� ���� ���Ѻο�
	    	insertAuthAplyReleaMngAll(sEvalYear, sEvalNum, sUserId);	//��ǥ����, 1�� ���� �ý��� ��� ���Ѻο�
	    	//nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//�������
	    	//nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//������
	    	nInsertCnt += insertAuthMuReleaMngAll(sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sUserId, curDateTime);	//����������
        	
        	//�ٹ��µ����� ���Ѻο�
	    	nInsertCnt += insertAuthAplyMnrAll(sEvalYear, sEvalNum, sUserId);	//�ٹ��µ����� �ý��� ��� ���Ѻο�
	    	//nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//�������
	    	//nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//������
	    	nInsertCnt += insertAuthMuMnrAll(sEvalYear, sEvalNum, "REV2020", "Y", "Y", "Y", sUserId, curDateTime);	//�ٹ��µ���
        	
        	//�����µ�����
        	insertAuthAplySvrAll(sEvalYear, sEvalNum, sUserId);	//�������� �ý��� ��� ���Ѻο�
        	//nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//�������
        	//nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//������
        	nInsertCnt += insertAuthMuSvrAll(sEvalYear, sEvalNum, "REV2030", "Y", "Y", "Y", sUserId, curDateTime);	//������
        	
	    	return nInsertCnt;
	    }
	    
	    /**
	     * <p> �μ���, ����� ���� ��ο�(REV1060) </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    public  int reEval(String sEvalYear, String sEvalNum, String sUserId, int nCode) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	        
	    	// nCode = 1�϶�, ȸ��
	    	// nCode = 2�϶�, ���غ� ���õ� ���� �ο�
	    	// nCode = 3�϶�, �򰡿� ���õ� ���Ѻο�
	        
	    	if( nCode == 1 ) {
	    		deleteAuthMu(sEvalYear, sEvalNum, "", 1, sUserId);	//����ȸ��
	    	}
	    	
	    	if( nCode == 2 ) {
		    	//���Ѻο�
	        	//�μ��� ���Ѻο�(2�� ����)
		    	nInsertCnt  = insertAuthAplyDeptHead(sEvalYear, sEvalNum, sUserId);	//�μ��� �ý��� ��� ���Ѻο�
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);	//�������
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "REV1000", "Y", "Y", "Y", sUserId, curDateTime);	//�򰡵��
		    	nInsertCnt += insertAuthMuDeptHead(sEvalYear, sEvalNum, "REV1070", "Y", "N", "N", sUserId, curDateTime);	//�μ������ڼ���
		    	
	        	//����� ���Ѻο�
		    	nInsertCnt += insertAuthAplyMng(sEvalYear, sEvalNum, sUserId);	//����� �ý��� ��� ���Ѻο�
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sUserId, curDateTime);		//�������
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1000", "Y", "Y", "Y", sUserId, curDateTime);		//�򰡵��
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV3000", "Y", "Y", "Y", sUserId, curDateTime);		//����Ȳ
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1070", "Y", "Y", "Y", sUserId, curDateTime);		//�μ������ڼ���
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1090", "Y", "N", "Y", sUserId, curDateTime);		//�μ��������Ȯ��
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV3040", "Y", "N", "N", sUserId, curDateTime);		//�μ���������Ȳ
		    	nInsertCnt += insertAuthMuMng(sEvalYear, sEvalNum, "REV1120", "Y", "Y", "Y", sUserId, curDateTime);		//�߸Ž��� ���ܱⰣ����
		    }
	    	
	    	if( nCode == 3 ) {
	    		nInsertCnt += insertAuthMuDeptHead   (sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sUserId, curDateTime);	//������
	    		nInsertCnt += insertAuthMuDeptHead   (sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sUserId, curDateTime);	//����������
		    	nInsertCnt += insertAuthMuDeptHeadMnrSvr(sEvalYear, sEvalNum, "REV2020", "Y", "N", "N", "2", sUserId, curDateTime);	//�ٹ��µ���
		    	nInsertCnt += insertAuthMuDeptHeadMnrSvr(sEvalYear, sEvalNum, "REV2030", "Y", "N", "N", "3", sUserId, curDateTime);	//������
	    	}
	    	
		    return nInsertCnt;
	    }
	    
	    /**
	     * <p> ��ǥ����, 1������, ����, ���� ���� �μ��� ��ο� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int reWorkEvalDept(String sEvalYear, String sEvalNum, String sAprvDpet, String sUserId) {
	    	int nInsertCnt = 0;
	        String  curDateTime = "";
	        UtilDb udb = new UtilDb();
	        curDateTime = udb.getCurTime();
	    	
	    	//deleteAuthMu(sEvalYear, sEvalNum, sAprvDpet, 4);	//����ȸ��

	    	//���� �ο�
	    	//��ǥ����, 1�� ���� ���Ѻο�
	    	insertAuthAplyReleaMng(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//��ǥ����, 1�� ���� �ý��� ��� ���Ѻο�
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//�������
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//������
	    	insertAuthMuReleaMng(sEvalYear, sEvalNum, "REV2010", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//����������

	    	//�ٹ��µ����� ���Ѻο�
	    	insertAuthAplyMnr(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//�ٹ��µ����� �ý��� ��� ���Ѻο�
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//�������
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//������
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV2020", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//�ٹ��µ���
	    	insertAuthMuMnr(sEvalYear, sEvalNum, "REV3200", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//�߸Ž������ܱⰣ

	    	//�����µ�����
	    	insertAuthAplySvr(sEvalYear, sEvalNum, sAprvDpet, sUserId);	//�������� �ý��� ��� ���Ѻο�
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "R000007", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//�������
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "REV2000", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//������
	    	insertAuthMuSvr(sEvalYear, sEvalNum, "REV2030", "Y", "Y", "Y", sAprvDpet, sUserId, curDateTime);	//������

        	
	    	return nInsertCnt;
	    }
	    
	    /**
	     * <p> �μ��� ���������� ȭ�� ����ȸ�� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteAuthMuOne(String sEvalYear, String sEvalNum, String sEvDept, String sMenuId, String sUserId, String curDtm)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	param.setValueParamter(i++, curDtm);
	    	param.setValueParamter(i++, sUserId);
	    	i = 0;
	    	param.setWhereClauseParameter(i++, sMenuId);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sEvDept);
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d13", param);
	        
	        return dmlcount;
	    }
}
