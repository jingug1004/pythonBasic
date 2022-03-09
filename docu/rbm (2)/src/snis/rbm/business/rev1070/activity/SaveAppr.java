/*================================================================================
 * �ý���			: �򰡴�����
 * �ҽ����� �̸�	: snis.rbm.business.rev1070.activity.SaveAppr.java
 * ���ϼ���		: �򰡴�����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-21
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

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveAppr extends SnisActivity {
		public SaveAppr(){}
	
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
	    	String sDeptCd   = (String)ctx.get("DEPT_CD");
	        String sReGrant  = (String)ctx.get("REGRANT");
	    	
	    	int nSaveCount = 0;
	    	
	    	//����� Ȯ���� ���� �ʾҴٸ� �Ұ���
	    	if( !getDeptUpdateYn(sEvalYear, sEvalNum, sDeptCd).equals("Y") ) {
	    		try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�μ��������Ȯ������ ����ڸ� Ȯ���� ��, Ȯ���Ͻ� �Ŀ� ������ּ���.");
            		
            		return;
            	}
	    	}
	    	
	    	//������ �Ǿ��ٸ� �Ұ���
	    	if( sReGrant.equals("N")) {
	    		if( getAprvYn(sEvalYear, sEvalNum, sDeptCd).equals("N") ) {
	    			try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "���ο�û�� �Ǿ��� ������ �����Ͻ� �� �����ϴ�.");
	            		
	            		return;
	            	}
	    		}
	    	}
	    	
	    	//�򰡸����� �Ǿ��ٸ� �Ұ���
	    	if( sReGrant.equals("Y")) {
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
	    	}
	    	
	    	deleteEval(sEvalYear, sEvalNum, sDeptCd);	//����������, �ٹ��µ���, ������ ����
	    	
    		nSaveCount  = insertPrm(sEvalYear, sEvalNum, sDeptCd);	//����������
    		nSaveCount += insertMnr(sEvalYear, sEvalNum, sDeptCd);	//�ٹ��µ���
    		nSaveCount += insertSrv(sEvalYear, sEvalNum, sDeptCd);	//������
    		
    		nSaveCount += insertPrmDtHead1 (sEvalYear, sEvalNum, sDeptCd);	//����������(��ǥ������ �߸ſ���)
    		nSaveCount += insertPrmDtHead2 (sEvalYear, sEvalNum, sDeptCd);	//����������(�߸ſ��� ��ǥ������)
    		nSaveCount += insertPrmDtWork1(sEvalYear, sEvalNum, sDeptCd);	//��߸ſ� 1����
    		nSaveCount += insertPrmDtWork2(sEvalYear, sEvalNum, sDeptCd);	//�߸�/��߸� 2����
    		nSaveCount += insertMnrDt     (sEvalYear, sEvalNum, sDeptCd);	//�ٹ��µ��򰡻�
    		nSaveCount += insertSrvDt     (sEvalYear, sEvalNum, sDeptCd);	//�����򰡻�
    		
    		//������ ����
    		if( sReGrant.equals("Y")) {
    			updateAprv(sEvalYear, sEvalNum, sDeptCd); //�򰡴�� ����� ��, �ٹ��µ���,������ ���λ��� �ʱ�ȭ(������ ����)
    			//���� ��ο�
    			SaveAprvMana SaveAprvMana = new SaveAprvMana();
    			SaveAprvMana.reWorkEvalDept(sEvalYear, sEvalNum, sDeptCd, SESSION_USER_ID);
    		}
    		
	    	Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> ���������� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertPrm(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
       
	        //UNION ����
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertMnr(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertSrv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����������(��ǥ����) �߸ſ� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int deleteEval(String sEvalYear, String sEvalNum, String sDeptCd) {
	    	
	    	int nDelCnt = 0;
	    	nDelCnt  = deletePrm(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMnr(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteSrv(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	nDelCnt += deletePrmDt(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMnrDt(sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteSrvDt(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	return nDelCnt;
	    }
	    
	    /**
	     * <p> ����������(��ǥ������ �߸ſ� ��) Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertPrmDtHead1(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i04_1", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ����������(�߸ſ��� ��ǥ���� ��) Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertPrmDtHead2(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i04_2", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��߸ſ� 1���� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertPrmDtWork1(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �߸�/��߸� 2���� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertPrmDtWork2(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��򰡻� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertMnrDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �����򰡻� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertSrvDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_i08", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���������� �Է¿��� ��ȸ  </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  rtnKey	int			��ǿ� �ɷ��ִ� ����̷� ����
	     * @throws  none
	     */
	    protected int getPrmCnt(String sEvalYear, String sEvalNum, String sDeptCd)  
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

	        return Integer.parseInt(rtnKey);
	    }
	    
	    /**
	     * <p> �����µ��� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deletePrm(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �����µ��򰡻� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deletePrmDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMnr(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��򰡻� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMnrDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteSrv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteSrvDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1070_d07", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� ����� Ȯ�� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getDeptUpdateYn(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1070_s12", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));

	        return rtnKey;
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
	     * <p> �ٹ��µ���, ������ ���λ��� �ʱ�ȭ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAprv(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1070_u08", param);
	        
	        return dmlcount;
	    }
}
