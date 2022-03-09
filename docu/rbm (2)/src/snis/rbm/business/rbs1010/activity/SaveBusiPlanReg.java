/*================================================================================
 * �ý���			: ���ǰ������
 * �ҽ����� �̸�	: snis.rbm.business.rbs1010.activity.SaveBusiPlanReg.java
 * ���ϼ���		: �����ȹ���
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-09
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiPlanReg extends SnisActivity {
		public SaveBusiPlanReg(){}
		
		
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

	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateBusiPlan(record)) == 0 ) {
			        		nTempCnt = insertBusiPlan(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
		            	deleteFileMana(record);		// ÷������ ����
		            	deleteBusiExec(record);		// ���೻�� ����
			        	nDeleteCount = nDeleteCount + deleteBusiPlan(record);	// �����ȹ ��� ����
		            }		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> �����ȹ��� �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertBusiPlan(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1.�⵵
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2.��������
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3.ȸ�豸���ڵ�
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4.�����
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5.���αݾ�

	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6.�μ��ڵ� 
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7.�����ID
	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 8.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> �����ȹ��� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateBusiPlan(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));     // 1. �⵵ 
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_TMS"));   // 2. ��������
	        param.setValueParamter(i++, record.getAttribute("ACC_BGN_CD"));    // 3. ȸ�豸���ڵ�	
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));       // 4. �����
	        param.setValueParamter(i++, record.getAttribute("CONFIRM_AMT"));   // 5. ���αݾ�
	        
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));       // 6. �μ��ڵ�
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));        // 7. �����ID
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 8. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 9. �ð����ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1010_u01", param);
	        return dmlcount;
	    }

	    
	    /** �����ȹ��� ���� ������ Relation �Ǿ� �ִ� �ڽ� ���̺���� ���� �� ������ ������ ���� �Ѵ�. (���Ἲ �������� ���� ����) **/
	    /**
	     * <p> ÷������  ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteFileMana(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"      ) );	// ÷������
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d_file", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> ���೻�� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteBusiExec(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // �ð����ڵ�
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d_exec", param);

	        return dmlcount;
	    }
	    
	    /**
	     * <p> �����ȹ��� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteBusiPlan(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // �ð����ڵ�
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1010_d01", param);

	        return dmlcount;
	    }
}
