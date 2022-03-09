/*================================================================================
 * �ý���			: �����ǰ���
 * �ҽ����� �̸�	: snis.rbm.business.rbs1110.activity.SaveHeal.java
 * ���ϼ���		: �����ǰ��� ���
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-09-2
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1110.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHnrRefreTms extends SnisActivity {
		public SaveHnrRefreTms(){}
		
		
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

	        sDsName = "dsHnrRefreTms";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
				        nSaveCount += insertHnrRefreTms(record);
			        } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
				        nSaveCount += updateHnrRefreTms(record);
			        } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
			        	nDeleteCount += deleteHnrRefreTms(record);	            	
		            }		        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

	    /**
	     * <p> ������ ���� �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertHnrRefreTms(PosRecord record) 
	    {			           
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));    	// 1.���ֱ���ڵ�
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   	// 2.���س⵵
	        param.setValueParamter(i++, record.getAttribute("MEET_CD"));        // 3.���ֱ���ڵ�
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));      // 4.���س⵵
	        param.setValueParamter(i++, record.getAttribute("EVENT_DY"));       // 5.�������
	        param.setValueParamter(i++, record.getAttribute("RMK"));     		// 6.��� 
	        param.setValueParamter(i++, SESSION_USER_ID);                       // 7.����� ID	
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rbs1110_i01", param);
	        
	        return dmlcount;
	    }
	    	
	    /**
	     * <p> ������ ���� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateHnrRefreTms(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("EVENT_DY"));       	// 1. ���������ڵ� 
	        param.setValueParamter(i++, record.getAttribute("RMK"));            	// 2. �����
	        param.setValueParamter(i++, SESSION_USER_ID);                      		// 3. ����� ID     
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 4. ���ֱ���ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 5. ���س⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));   		// 6. ����	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1110_u01", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> ������ ���� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteHnrRefreTms(PosRecord record) 
	    {		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD")); 	// 1. ���ֱ���ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   // 2. ���س⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));   		// 3. ����	        

	        int dmlcount = this.getDao("rbmdao").update("rbs1110_d01", param);

	        return dmlcount;
	    }
}
