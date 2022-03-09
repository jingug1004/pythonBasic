/*================================================================================
 * �ý���			: ���ǰ������
 * �ҽ����� �̸�	: snis.rbm.business.rbs1040.activity.SaveBusiProgReg.java
 * ���ϼ���		: ������� ���
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-17
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiProgReg extends SnisActivity {
		public SaveBusiProgReg(){}
		
		
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

	        	
	        // �����ȹ - ��������, ���  UPDATE 
	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        
		        // LOOP 
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        nTempCnt = updateBusiPlan(record);
			        
				    nSaveCount = nSaveCount + nTempCnt;
			        
			        		        
		        }
		        // LOOP END
	        }
	        
	        
	        
	        // ���೻�� - INSERT, UPDATE, DELETE 
	        sDsName = "dsExec";
	        
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateBusiExec(record)) == 0 ) {
			        		nTempCnt = insertBusiExec(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteBusiExec(record);	            	
		            }		        
		        }	        
	        }
	        
	        
	        //  ������� UPDATE
	        
	        
	        

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> �����ȹ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateBusiPlan(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("QUAR_1_DELAY_RSN"));     // 1. 1�б� ��������
	        param.setValueParamter(i++, record.getAttribute("QUAR_2_DELAY_RSN"));     // 2. 2�б� ��������
	        param.setValueParamter(i++, record.getAttribute("QUAR_3_DELAY_RSN"));     // 3. 3�б� ��������
	        param.setValueParamter(i++, record.getAttribute("QUAR_4_DELAY_RSN"));     // 4. 4�б� ��������
	        
	        param.setValueParamter(i++, record.getAttribute("QUAR_1_RMK"));           // 5. 1�б� ���
	        param.setValueParamter(i++, record.getAttribute("QUAR_2_RMK"));           // 6. 2�б� ���
	        param.setValueParamter(i++, record.getAttribute("QUAR_3_RMK"));           // 7. 3�б� ���
	        param.setValueParamter(i++, record.getAttribute("QUAR_4_RMK"));           // 8. 4�б� ���
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 9. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 10. �ð����ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1040_u01", param);
	        return dmlcount;
	    }
	    
	    

	    /**
	     * <p> ���೻�� ��� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertBusiExec(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 1.�ð����ڵ�
	        param.setValueParamter(i++, record.getAttribute("REPORT_NO"));     // 2.���Ǽ���ȣ
	        param.setValueParamter(i++, record.getAttribute("EXEC_DT"));       // 3.��������
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT"));    // 4.���೻��
	        param.setValueParamter(i++, record.getAttribute("EXEC_AMT"));      // 5.����ݾ�
	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 6.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1040_i01", param);
	        
	        return dmlcount;
	      
	    }
	    
	    
	    
	    /**
	     * <p> ���೻�� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateBusiExec(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
			
	        
	        param.setValueParamter(i++, record.getAttribute("REPORT_NO"));     // 1. ���Ǽ���ȣ 
	        param.setValueParamter(i++, record.getAttribute("EXEC_DT"));       // 2. ��������
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT"));    // 3. ���೻��	
	        param.setValueParamter(i++, record.getAttribute("EXEC_AMT"));      // 4. ����ݾ�
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                      // 5. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_CNTNT_CD"));   // 6. ���೻���ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1040_u02", param);
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
	        param.setValueParamter(i++, record.getAttribute("EXEC_CNTNT_CD"));   // 1. ���೻���ڵ�
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1040_d01", param);

	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ������� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateBusiProg(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("PREP_AMT_CD"));   // 1. �ð����ڵ�
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("", param);

	        return dmlcount;
	    }
}
