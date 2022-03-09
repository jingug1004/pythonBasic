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
package snis.rbm.business.rbs1220.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYesSang extends SnisActivity {
		public SaveYesSang(){}
		
		
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

	        sDsName = "dsYesSang";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

				    nSaveCount += updateYesSang(record);
		        	continue;
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> ���󳻿� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateYesSang(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));     	// 1.���س⵵
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));   		// 2.������� ����
	        param.setValueParamter(i++, record.getAttribute("TMS"));    		// 3.ȸ��
	        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));       	// 4.����
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));   		// 5.���ֹ�ȣ
	        param.setValueParamter(i++, record.getAttribute("YES_COM_SEQ"));    // 6.��������ü ������ȣ 
	        param.setValueParamter(i++, record.getAttribute("YESANG"));        	// 7.���󳻿�                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 8.������ ���		        
	       
	        int dmlcount = this.getDao("rbmdao").update("rbs1220_u01", param);
	        return dmlcount;
	    }

}
