/*================================================================================
 * �ý���			: �򰡴������
 * �ҽ����� �̸�	: snis.rbm.business.rev1100.activity.SaveEvEmp.java
 * ���ϼ���		: �μ������� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-11-27
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1100.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEvEmp extends SnisActivity {
		public SaveEvEmp(){}
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

	        //�ٸ��򰡽��ν�, �����Ұ���
	        if( getAprvYn(sEvalYear, sEvalNum).equals("N")) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "[ �ٸ����ڱ׷켱�� ]���� ���ο�û�� �Ǿ��� ������ �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	     
	        sDsName = "dsEvEmp";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	nTempCnt += updateEvEmp(record);	//�������� ����
			        	
			        	//����
			        	deletePrm(record);
			        	deletePrmDt(record);
			        	deleteMnr(record);
			        	deleteMnrDt(record);
			        	deleteSrv(record);
			        	deleteSrvDt(record);
			        }     
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount  );
	    }
	    
	    /**
	     * <p> ����ڸ� �������� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEvEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_RSN"));
	        param.setValueParamter(i++, SESSION_USER_ID);		  
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_u01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���������� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deletePrm(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���������� �󼼻��� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deletePrmDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d02", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMnr(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d03", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ��򰡻� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteMnrDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d04", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteSrv(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d05", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �����򰡻� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteSrvDt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	                					
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1100_d06", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٸ��� ���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String getAprvYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1100_s02", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("APRV_YN"));
	        
	        return rtnKey;
	    }
}
