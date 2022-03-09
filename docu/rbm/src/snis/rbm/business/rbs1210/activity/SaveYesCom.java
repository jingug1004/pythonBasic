/*================================================================================
 * �ý���			: ������ ���߷� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * ���ϼ���		: ������ ��ü ���
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2014-08-31
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1210.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYesCom extends SnisActivity {
		public SaveYesCom(){}
		
		
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

	        sDsName = "dsYesCom";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteYesCom(record);		// ��������ü ����
		            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateYesCom(record)) == 0 ) {
			        		nTempCnt = insertYesCom(record);
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
	     * <p> ������ ��ü �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertYesCom(PosRecord record) 
	    {	   
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));     	// 1.������� ����
	        param.setValueParamter(i++, record.getAttribute("YES_COM_NM"));   	// 2.��ü��
	        param.setValueParamter(i++, record.getAttribute("MNGR_NM"));    	// 3.����� �̸�
	        param.setValueParamter(i++, record.getAttribute("TEL_NO"));       	// 4.��ȭ��ȣ
	        param.setValueParamter(i++, record.getAttribute("USE_YN"));   		// 5.�������
	        param.setValueParamter(i++, record.getAttribute("REG_DT"));       	// 6.������� 
	        param.setValueParamter(i++, record.getAttribute("ORDR_NO"));        // 7.��ȸ����	    	                
	        param.setValueParamter(i++, record.getAttribute("RMK"));        	// 8.���	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 9.����� ���		        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1210_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> ������ ��ü ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateYesCom(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));     	// 1.������� ����
	        param.setValueParamter(i++, record.getAttribute("YES_COM_NM"));   	// 2.��ü��
	        param.setValueParamter(i++, record.getAttribute("MNGR_NM"));    	// 3.����� �̸�
	        param.setValueParamter(i++, record.getAttribute("TEL_NO"));       	// 4.��ȭ��ȣ
	        param.setValueParamter(i++, record.getAttribute("USE_YN"));   		// 5.�������
	        param.setValueParamter(i++, record.getAttribute("REG_DT"));       	// 6.������� 
	        param.setValueParamter(i++, record.getAttribute("ORDR_NO"));        // 7.��ȸ����	    	                
	        param.setValueParamter(i++, record.getAttribute("RMK"));        	// 8.���	    	                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 9.������ ���		        
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("YES_COM_SEQ"));   // 10. ��������ü ������ȣ

	        int dmlcount = this.getDao("rbmdao").update("rbs1210_u01", param);
	        return dmlcount;
	    }

	    /**
	     * <p> ������ ��ü ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteYesCom(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("YES_COM_SEQ"));   // ��������ü ������ȣ
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1210_d01", param);

	        return dmlcount;
	    }
}
