/*================================================================================
 * �ý���			: �μ�������ڼ���
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �μ�������� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1050.activity.SaveAprvMana;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveEmp extends SnisActivity {
		public SaveEmp(){}
		
		
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
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");

	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "�μ�������ڸ� Ȯ���� �μ��� �����ϹǷ� �����Ͻ� �� �����ϴ�.");
	        		
	        		return;
	        	}
	        }
	        
	        //���� ȸ��(�����)
	        SaveAprvMana SaveAprvMana = new SaveAprvMana();
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 1);
	        
	        sDsName = "dsEmpDept";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		    
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			        	updateAprv    (record);	//����
			        	updateAprvCfm (record);	//����
			        	updateEmpEmpty(record);	

			        	nTempCnt = updateEmp(record);
			        	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        }	        
	        }
	        
	        //���� �ο�(�����) :: ����� ����� ���� �߸� �ο��� ������� �޴� ���� �������� ����(����!!)
	        System.out.println("****************************");
	        SaveAprvMana.reEval(sEvalYear, sEvalNum, SESSION_USER_ID, 2);
	        
	        Util.setSaveCount  (ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> �μ�������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmp(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);                             // 1. ����� ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 2. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 3. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      // 4. �����ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));      // 5. �����ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u02", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ��� ������ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAprv(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. ���ο�û�� ���
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. ����� ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. �����ȣ
	        param.setWhereClauseParameter(i++, "1");       							  // 6. ��������

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ��� Ȯ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAprvCfm(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	    	
	    	int i = 0;
	    	
	    	param.setValueParamter(i++, record.getAttribute("EMP_NO"));        		  // 1. ���ο�û�� ���
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. ����� ID

	        i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));       // 5. �����ȣ
	        param.setWhereClauseParameter(i++, "4");       							  // 6. ��������

	        int dmlcount = this.getDao("rbmdao").update("rev1060_u03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �μ�������� �μ� ��ü ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmpEmpty(PosRecord record)
	    {
    		PosParameter param = new PosParameter();
	    	
	    	int i = 0;

	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      // 3. �����ȣ

	        int nEptyCnt = this.getDao("rbmdao").update("rev1060_u01", param);
	        return nEptyCnt;
	    }
	    
	    /**
	     * <p> ���� ���� ��ȸ  </p>
	     * @param   
	     *          String        sEvalYear  	
	     *          String		  sEvalNum
	     * @return  nRtnKey	int			        sAttFileKey�� ���� ÷������ ����
	     * @throws  none
	     */
		public int getAprvCnt(String sEvalYear, String sEvalNum)
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;

	        param.setWhereClauseParameter(i++, sEvalYear);	//�⵵
	        param.setWhereClauseParameter(i++, sEvalNum);	//ȸ��
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1060_s03", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String sRtnKey = String.valueOf(pr[0].getAttribute("CNT"));
	        int nRtnKey    = Integer.parseInt(sRtnKey);
	        
	        return nRtnKey;
	    }

}
