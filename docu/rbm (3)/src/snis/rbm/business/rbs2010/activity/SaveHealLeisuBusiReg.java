/*================================================================================
 * �ý���			: �����������
 * �ҽ����� �̸�	: snis.rbm.business.rbs2010.activity.SaveHealLeisuBusiReg.java
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

public class SaveHealLeisuBusiReg extends SnisActivity {
		public SaveHealLeisuBusiReg(){}
		
		
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
			        	if ( (nTempCnt = updateHealLeisu(record)) == 0 ) {
			        		nTempCnt = insertHealLeisu(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteHealLeisu(record);	            	
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
	    protected int insertHealLeisu(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR").toString().substring(0, 4) );  // 1.�⵵
	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));    // 2.���������ڵ�
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));          // 3.�����
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));          // 4.�μ��ڵ�
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));           // 5.�����ID
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));      // 6.������۳�� 
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));      // 7.���������
	        if(  record.getAttribute("BUSI_YM_END") == null ||  record.getAttribute("BUSI_YM_END").equals("") ){
	        	param.setValueParamter(i++, "001");      // 8.��������� - ����
	        }else{
	        	param.setValueParamter(i++, "002");      // 8.��������� - ����
	        }
	        
	        param.setValueParamter(i++, record.getAttribute("ORD"));      // 9.���ļ���
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                         // 10.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_ins", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ����������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateHealLeisu(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("HEAL_LEISU_CD"));          // 1. ���������ڵ� 
	        param.setValueParamter(i++, record.getAttribute("BUSI_NM"));                // 2. �����
	        param.setValueParamter(i++, record.getAttribute("DPRT_CD"));                // 3. �μ��ڵ�	
	        param.setValueParamter(i++, record.getAttribute("MNG_ID"));                 // 4. �����ID	
	        
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_STR"));            // 5. ������۳��
	        param.setValueParamter(i++, record.getAttribute("BUSI_YM_END"));            // 6. ���������
	        if(  record.getAttribute("BUSI_YM_END") == null ||  record.getAttribute("BUSI_YM_END").equals("") ){
	        	param.setValueParamter(i++, "001");      // 7.��������� - ����
	        }else{
	        	param.setValueParamter(i++, "002");      // 7.��������� - ����
	        }
	        
	        param.setValueParamter(i++, record.getAttribute("ORD"));                    // 8. ���ļ���
	        param.setValueParamter(i++, SESSION_USER_ID);                               // 9. ����� ID      
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 10. ��������ڵ�
	        

	        int dmlcount = this.getDao("rbmdao").update("rbs2010_upt", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> ����������� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteHealLeisu(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BUSI_CNTNT_CD"));   // 1. ��������ڵ�
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs2010_del", param);

	        return dmlcount;
	    }
}
