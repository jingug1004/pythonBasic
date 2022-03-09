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

public class ChagePswd extends SnisActivity {
		public ChagePswd(){}
		
		
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

		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
		            	
			        	nTempCnt = updateYesCom(record);
			        	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }			        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> ������ ��ü ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateYesCom(PosRecord record)
	    {			
	    	String sChk = "";
	    	sChk = (String)record.getAttribute("CHK");
	    	System.out.println("sChk="+sChk);	    	
	    	if (!"1".equals(sChk)) return 0;
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("YES_COM_PSWD"));     		// 1.������� ����
	        param.setValueParamter(i++, SESSION_USER_ID);                      			// 2.������ ���		        
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("YES_COM_SEQ"));   	// 3. ��������ü ������ȣ

	        int dmlcount = this.getDao("rbmdao").update("rbs1210_u02", param);
	        return dmlcount;
	    }

}
