/*================================================================================
 * �ý���			: �򰡸���
 * �ҽ����� �̸�	: snis.rbm.business.rev3020.activity.SaveEstmEnd.java
 * ���ϼ���		: �򰡸���
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
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1050.activity.*;

public class SaveEstmEnd extends SnisActivity {
		public SaveEstmEnd(){}
		
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
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        	
	        sDsName = "dsEstmEnd";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt = dsEstmEnd(record);	//�򰡸��� ó��
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        /*
	        //�ٸ��򰡿��� ���ܿ��ΰ� üũ�Ǿ� ���ܵ� ��� (��ü ��հ� ����)
	        sDsName = "dsExcept";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt   = updateExcept(record);
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        updateEvWkMult(sEvalYear, sEvalNum);	//�ٸ��� �׷��� 0�� ���(�׷��� 3���� ���� �ʴ� ���)
	        
	        */
	        
	        updateRec(sEvalYear, sEvalNum);
	        
	        updateAvgExcept(sEvalYear, sEvalNum);	//������� ����
	        
	        //�򰡸� ���� ���� ���(������ ����)
	        sDsName = "dsNoEval";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            nTempCnt   = updateExcept(record);
			        nSaveCount = nSaveCount + nTempCnt;
			    }	        
	        }
	        
	        //����ȸ��
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.deleteAuthMu(sEvalYear, sEvalNum, "", 3, SESSION_USER_ID);
	        
	        //�ӽû���� ����
	        deleteUser();
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> �򰡸��� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int dsEstmEnd(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u01", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateExcept(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));   
	        param.setValueParamter(i++, SESSION_USER_ID);    

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3020_u03", param);
	        return dmlcount;
	    }

	    /**
	     * <p> �ٸ����� 3���� �ʵǴ� �׷쿡 ��հ� �ֱ� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEvWkMult(String sEvalYear, String sEvalNum)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	  
	       // i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ӽû���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteUser()
	    {			
	        int dmlcount = this.getDao("rbmdao").update("rev3020_d01");
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��հ� ����(���ܰ� üũ�� ��� / ���� ���� ����� 80%�� ���� �ʴ� ��� / �ٸ��򰡱׷��� 3�� ������ ���(�׷� �ڵ尡 0�� ���) </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAvgExcept(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, SESSION_USER_ID); 
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);
	        param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��հ� ����(���ܰ� üũ�� ��� / ���� ���� ����� 80%�� ���� �ʴ� ��� / �ٸ��򰡱׷��� 3�� ������ ���(�׷� �ڵ尡 0�� ���) </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateRec(String sEvalYear, String sEvalNum)
	    {			
	    	PosParameter param = new PosParameter();
	        
	    	int i = 0;
	    	param.setValueParamter(i++, SESSION_USER_ID); 
	    	param.setValueParamter(i++, sEvalYear);
	        param.setValueParamter(i++, sEvalNum);

	        int dmlcount = this.getDao("rbmdao").update("rev3020_u06", param);
	        
	        return dmlcount;
	    }
}
