/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rbs2020.activity.SaveHealLeisuPrsnNumReg.java
 * ���ϼ���		: �������� �ο����
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs2020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHealLeisuPrsnNumReg extends SnisActivity {
		public SaveHealLeisuPrsnNumReg(){}
		
		
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

	        sDsName = "dsList";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
			        {
			        	String sIsNew = (String)record.getAttribute("IS_NEW");
			        	
			        	if(sIsNew.equals("T"))
			        	{
			        		nTempCnt = insertHealLeisuExec(record);
			        	}
			        	else if(sIsNew.equals("F"))
			        	{
			        		nTempCnt = updateHealLeisuExec(record);
			        	}
			        	
					    nSaveCount = nSaveCount + nTempCnt;
			        	//continue;
			        }	        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );	   
	        
	    }
	    


	    /**
	     * <p> ���������ο���� �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertHealLeisuExec(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_CD"));     // 1.��������ڵ�
	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_YEAR"));   // 2.���������⵵
	        param.setValueParamter(i++, record.getAttribute("EXEC_YEAR"));         // 3.����⵵
	        param.setValueParamter(i++, record.getAttribute("EXEC_MM"));           // 4.�����
	        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));          // 5.�ο�
	      	        
	        param.setValueParamter(i++, SESSION_USER_ID);                          // 6.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2020_ins", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ����������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateHealLeisuExec(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        
	        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));                // 1. �ο�	        
	        param.setValueParamter(i++, SESSION_USER_ID);                                // 2. ����� ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BUSI_CNTNT_CD"));    // 3. ��������ڵ�
	        //param.setWhereClauseParameter(i++, record.getAttribute("HEAL_LEISU_YEAR"));  // 4. ���������⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_YEAR"));        // 5. ����⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("EXEC_MM"));          // 6. �����
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs2020_upt", param);
	        return dmlcount;
	    }
	   
}
