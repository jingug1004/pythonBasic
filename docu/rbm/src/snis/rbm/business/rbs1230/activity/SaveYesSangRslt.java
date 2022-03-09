/*================================================================================
 * �ý���			: ������ ���߷� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * ���ϼ���		: ������ ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2014-09-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1230.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveYesSangRslt extends SnisActivity {
		public SaveYesSangRslt(){}
		
		
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

	        sDsName = "dsYesSangRslt";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

				    nSaveCount += updateYesSangRslt(record);
		        	continue;
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    
	    /**
	     * <p> ������ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateYesSangRslt(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("YESANG"));     	// 1.����
	        param.setValueParamter(i++, record.getAttribute("AB"));   			// 2.A-B
	        param.setValueParamter(i++, record.getAttribute("AC"));    			// 3.A-C
	        param.setValueParamter(i++, record.getAttribute("BA"));       		// 4.B-A
	        param.setValueParamter(i++, record.getAttribute("CA"));   			// 5.C-A
	        param.setValueParamter(i++, record.getAttribute("ABC"));    		// 6.�ﺹ�� 
	        param.setValueParamter(i++, record.getAttribute("EX_AB"));        	// 7.AB����ġ                
	        param.setValueParamter(i++, record.getAttribute("EX_ABC"));        	// 8.ABC����ġ                
	        param.setValueParamter(i++, record.getAttribute("EX_AC"));        	// 9.AC����ġ                
	        param.setValueParamter(i++, record.getAttribute("EX_A"));        	// 10.�� ����ġ                
	        param.setValueParamter(i++, record.getAttribute("AUTO"));        	// 11.�ڵ���������                
	        param.setValueParamter(i++, record.getAttribute("RATE"));        	// 12.����(�ֽ�)   
	        param.setValueParamter(i++, record.getAttribute("RATE_QU"));      	// 13.����(����)   
	        param.setValueParamter(i++, record.getAttribute("ETC"));        	// 14.������������ü��                
	        param.setValueParamter(i++, record.getAttribute("EXCLUSION"));      // 15.��������               
	        param.setValueParamter(i++, record.getAttribute("EX_BA"));        	// 16.BA����ġ                
	        param.setValueParamter(i++, record.getAttribute("RT_AB"));        	// 17.AB��������ġ      
	        param.setValueParamter(i++, record.getAttribute("RT_BA"));        	// 18.BA��������ġ                
	        param.setValueParamter(i++, SESSION_USER_ID);                      	// 19.������ ���		        
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("YES_COM_SEQ")); // 20. ��ü��ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 21. ���/���� ����
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR")); 	// 22. �⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS")); 		// 23. ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DAY_ORD")); 	// 24. ����
	        param.setWhereClauseParameter(i++, record.getAttribute("RACE_NO")); 	// 25. ���ֹ�ȣ
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1230_u01", param);
	        return dmlcount;
	    }

}
