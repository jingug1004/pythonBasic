/*================================================================================
 * �ý���			: �򰡹��ǥ   ��
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveDistribution extends SnisActivity {
		public SaveDistribution(){}
		
		
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
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//�򰡳⵵
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//��ȸ��
	        
	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "�μ�������ڸ� Ȯ���� �μ��� �����ϹǷ� �����ڷḦ �����Ͻ� �� �����ϴ�.");
	        		
	        		return;
	        	}
	        }
	        
	        sDsName = "dsDistribution";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateDistribution(record)) == 0 ) {
			        		nTempCnt = insertDistribution(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        } 
		        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
			        	nDeleteCount = nDeleteCount + deleteDistribution(record);	            	
		            } 
		            
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> �򰡹��ǥ �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertDistribution(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));       // 1.�����򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));        // 2.������ȸ��
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 3.�򰡴���ο���
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 4.�����򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 5.������ȸ��

	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 6.�򰡴���ο��� 
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));    // 7.�򰡴���ο���
	        param.setValueParamter(i++, record.getAttribute("S_GRD_NUM"));       // 8. S�ο��� 	
	        param.setValueParamter(i++, record.getAttribute("A_GRD_NUM"));       // 9. A�ο���
	        param.setValueParamter(i++, record.getAttribute("B_GRD_NUM"));       // 10.B�ο���
	        param.setValueParamter(i++, record.getAttribute("C_GRD_NUM"));       // 11.C�ο���
	        param.setValueParamter(i++, record.getAttribute("D_GRD_NUM"));       // 12.D�ο���		        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 13.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1040_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateDistribution(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("S_GRD_NUM"));            // 1. S�ο��� 
	        param.setValueParamter(i++, record.getAttribute("A_GRD_NUM"));            // 2. A�ο���
	        param.setValueParamter(i++, record.getAttribute("B_GRD_NUM"));            // 3. B�ο���	
	        param.setValueParamter(i++, record.getAttribute("C_GRD_NUM"));            // 4. C�ο���	
	        param.setValueParamter(i++, record.getAttribute("D_GRD_NUM"));            // 5. D�ο���	

	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. ����� ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 7. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 8. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DISTR_CD"));      // 9. ���ǥ�ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_OBJ_NUM"));  // 10.�򰡴���ο��� 

	        int dmlcount = this.getDao("rbmdao").update("rev1040_u01", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteDistribution(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setValueParamter(i++, record.getAttribute("DISTR_CD"));      // 3. ���ǥ�ڵ�
	        param.setValueParamter(i++, record.getAttribute("ESTM_OBJ_NUM"));  // 4. �򰡴���ο��� 
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1040_d01", param);

	        return dmlcount;
	    }

}
