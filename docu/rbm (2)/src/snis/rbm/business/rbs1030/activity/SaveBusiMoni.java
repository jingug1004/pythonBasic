/*================================================================================
 * �ý���			: ���ǰ������
 * �ҽ����� �̸�	: snis.rbm.business.rbs1030.activity.SaveBusiMoni.java
 * ���ϼ���		: ��� ����͸�
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBusiMoni extends SnisActivity {
		public SaveBusiMoni(){}
		
		
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
	        
	        
	        String sInputEndYN = (String) ctx.get("INPUT_END_YN");
	        
	        sDsName = "dsList";
	        
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			        	if(sInputEndYN.equals("Y"))  // �Է¸��� 001
			        	{
			        		nTempCnt = saveInputEndY(record);
			        	}
			        	else  // �������� 002
			        	{
			        		nTempCnt = saveInputEndN(record);
			        	}
			        	
			        
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }			        		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> �Է¸��� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int saveInputEndY(PosRecord record) 
	    {	   
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);   // 1. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 2. �ð����ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1030_u01", param);
	        return dmlcount;	        
	       
	    }
	    
	    
	    
	    /**
	     * <p> �������� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int saveInputEndN(PosRecord record) 
	    {	   
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, SESSION_USER_ID);   // 1. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("PREP_AMT_CD"));   // 2. �ð����ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1030_u02", param);
	        return dmlcount;	        
	       
	    }	   
}
