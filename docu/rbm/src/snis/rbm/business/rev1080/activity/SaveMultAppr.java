/*================================================================================
 * �ý���			: �򰡴�����
 * �ҽ����� �̸�	: snis.rbm.business.rev1070.activity.SaveMultAppr.java
 * ���ϼ���		: �򰡴�����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1080.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1080.activity.*;
import snis.rbm.business.rev2010.activity.SavePrmRslt;

public class SaveMultAppr extends SnisActivity {
		public SaveMultAppr(){}
	
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
	        String sGrant    = (String)ctx.get("GRANT");
	        
	    	//���ο�û ��, ���� �Ұ���
	    	SaveEmp SaveEmp = new SaveEmp();
	    	
	    	if( sGrant.equals("N") ) {	//REV1080(�ٸ��򰡱׷켱��)���� �Ѿ�Դٸ� 
		        if( SaveEmp.getCfmYn(sEvalYear, sEvalNum).equals("N") ) {
		        	try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "���ο�û�� ���� ������ �򰡴������� �Ͻ� �� �����ϴ�.");
	            		Util.setReturnParam(ctx, "RESULT", "F");
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
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �򰡴������� �Ͻ� �� �����ϴ�.");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		return;
            	}
	        }
	        
	    	int nSaveCount = 0;
	    	
	    	deleteMultEval(sEvalYear, sEvalNum, sDeptCd);
    		
	    	insertMult  (sEvalYear, sEvalNum, sDeptCd);	//�ٸ����� 
	    	insertMultDt(sEvalYear, sEvalNum, sDeptCd);	//�ٸ����� ��
	    	
	    	Util.setReturnParam(ctx, "RESULT", "S");
	    	Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> �ٸ����� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertMult(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٸ����ڻ� Insert </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertMultDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        param.setValueParamter(i++, sDeptCd);
	        
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev1080_i02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٸ�����ü Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int deleteMultEval(String sEvalYear, String sEvalNum, String sDeptCd) {
	    	int nDelCnt = 0;
	    	
	    	nDelCnt  = deleteMult  (sEvalYear, sEvalNum, sDeptCd);
	    	nDelCnt += deleteMultDt(sEvalYear, sEvalNum, sDeptCd);
	    	
	    	return nDelCnt;
	    }
	   
		
	    /**
	     * <p> �ٸ����� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMult(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d11", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٸ����ڻ� Delete </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMultDt(String sEvalYear, String sEvalNum, String sDeptCd) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        param.setWhereClauseParameter(i++, sDeptCd);
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1080_d12", param);
	        
	        return dmlcount;
	    }
}
