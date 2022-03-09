/*================================================================================
 * �ý���			: ���ǰ������
 * �ҽ����� �̸�	: snis.rbm.business.rbs1090.activity.SaveYearAccuAmt.java
 * ���ϼ���		: �⵵�� �����ݾ� ���
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-23
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYearAccuAmt extends SnisActivity {
		public SaveYearAccuAmt(){}
		
		
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
			        		nTempCnt = insertSav(record);
			        	}
			        	else if(sIsNew.equals("F"))
			        	{
			        		nTempCnt = updateSav(record);
			        	}
			        	
					    nSaveCount = nSaveCount + nTempCnt;
			        	//continue;
			        }	        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );	   
	        
	    }
	    


	    /**
	     * <p> �����ݾ� ��� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertSav(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YEAR"));   // 1.����⵵	        
	        param.setValueParamter(i++, record.getAttribute("ACC_CD"));      // 2.ȸ�豸���ڵ�
	        param.setValueParamter(i++, record.getAttribute("SAV_AMT"));     // 3.�����ݾ�
	        param.setValueParamter(i++, record.getAttribute("AMU_AMT"));     // 4.�����ܾ�
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                    // 5.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1090_i_sav", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> �����ݾ� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateSav(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("SAV_AMT"));    // 1.�����ݾ�
	        param.setValueParamter(i++, record.getAttribute("AMU_AMT"));    // 2.�����ܾ�
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                   // 3. �����ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("SAV_CNTNT_CD"));    // 3. ���������ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1090_u_sav", param);
	        return dmlcount;
	    }
	    
}
