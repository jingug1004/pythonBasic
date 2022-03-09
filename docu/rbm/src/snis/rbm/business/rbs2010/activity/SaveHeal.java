/*================================================================================
 * �ý���			: �����������
 * �ҽ����� �̸�	: snis.rbm.business.rbs2010.activity.SaveHeal.java
 * ���ϼ���		: ����������� ���
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-2
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHeal extends SnisActivity {
		public SaveHeal(){}
		
		
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

	        sDsName = "dsHeal";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateThrOutReport(record)) == 0 ) {
			        		nTempCnt = insertThrOutReport(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteThrOutReport(record);	            	
		            }		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> ����������� �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertThrOutReport(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("HEAL_YEAR"));       // 1.�⵵
	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));   // 2.���������ڵ�
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));         // 3.�����
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));         // 4.�μ��ڵ�
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));          // 5.�����ID

	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));     // 6.������۳�� 
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));     // 7.���������
	        param.setValueParamter(i++, record.getAttribute("BUSI_STAT_CD"));    // 8. ��������ڵ� 		                
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 9.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_ins", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ����������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateThrOutReport(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));          // 1. ���������ڵ� 
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));                // 2. �����
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));                // 3. �μ��ڵ�	
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));                 // 4. �����ID	
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));            // 5. ������۳��
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));            // 6. ���������
	        param.setValueParamter(i++, SESSION_USER_ID);                               // 7. ����� ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 8. �򰡳⵵
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs2010_upt", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> ����������� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteThrOutReport(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 1. ��������ڵ�
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_del", param);

	        return dmlcount;
	    }
}
