/*================================================================================
 * �ý���			: �� ����
 * �ҽ����� �̸�	: snis.rbm.business.rev3020.activity.SaveStart.java
 * ���ϼ���		: �� ���� : TBRF_MASTER�� �÷��� ���� �ٲٰ�, ���ڵ鿡�� ������ �ش�.
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

import snis.rbm.business.rev1050.activity.*;
import snis.rbm.business.rev1060.activity.*;

public class SaveStart extends SnisActivity {
		public SaveStart(){}

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
	    	int nSaveCount = 0;
	    	
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        nSaveCount = updateMasterStart(sEvalYear, sEvalNum); //TBRF_EV_MASTER�� ���� �÷��װ� ����
	        
	        //���Ѻο�
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.reWorkEval(sEvalYear, sEvalNum, SESSION_USER_ID);
	        
	        //�μ��� ��ȭ�� �ο�(REV2010, REV2020, REV2030)
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 3);
	        
	        //�� �����
	        updateEvalUser(sEvalYear, sEvalNum);
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> TBRF_EV_MASTER�� ���� �÷��װ� ����</p>
	     * @param   
	     * @return  nUptCnt int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateMasterStart(String sEvalYear, String sEvalNum)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;          
	        
	        param.setValueParamter(i++, SESSION_USER_ID);   //�����ID
	        
	        i = 0; 
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);

	        int nUptCnt = this.getDao("rbmdao").update("rev3020_u04", param);
	        
	        return nUptCnt;
	    }
	    
	    /**
	     * <p> �򰡻���� �߰��ϱ� </p>
	     */
	    protected int updateEvalUser(String sEvalYear, String sEvalNum)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        int nInsertCnt = 0;
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet pEvalUser = this.getDao("rbmdao").find("rev3020_s18", param);
	        
	        for(int nRow = 0; nRow <  pEvalUser.count(); nRow++) {
				PosRow pr[] = pEvalUser.getAllRow();
	    		
	    		String sPswd = String.valueOf(pr[nRow].getAttribute("PSWD"));
	    		sPswd = EncryptSHA256.getEncryptSHA256(sPswd);
	    		pr[nRow].setAttribute("PSWD", sPswd);
	    		
	    		nInsertCnt += insertEvalUser(pr[nRow]);
	        }
			return nInsertCnt;
	    }
	    
	    /**
	     * <p> �򰡻���� Insert </p>
	     */
	    protected int insertEvalUser(PosRow prUser)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, prUser.getAttribute("EMP_NO"));
	        param.setValueParamter(i++, prUser.getAttribute("PSWD"));
	        param.setValueParamter(i++, prUser.getAttribute("EMP_NM"));
	        param.setValueParamter(i++, prUser.getAttribute("HP_NO"));
	        param.setValueParamter(i++, prUser.getAttribute("TEAM_DETL_CD"));
	        
	        param.setValueParamter(i++, prUser.getAttribute("TEAM_CD"));
	        param.setValueParamter(i++, prUser.getAttribute("ASSO_CD"));
	        param.setValueParamter(i++, prUser.getAttribute("WK_JOB_NM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        int nInsertCnt = this.getDao("rbmdao").update("rev3020_i01", param);
	        
			return nInsertCnt;
	    }
}
