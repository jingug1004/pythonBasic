/*================================================================================
 * �ý���			: �ٸ��򰡱׷����
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �ٸ��򰡱׷� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1110.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSrvKCSI extends SnisActivity {
		public SaveSrvKCSI(){}
		
		
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
	    	
	    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        //�򰡸����� �Ǿ��ٸ� ���� �Ұ���
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
               	
	        nSaveCount = updateSrvKCSI(sEvalYear, sEvalNum);

	        Util.setReturnParam(ctx, "RESULT", "S");
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    /**
	     * <p> KCSI�������� �����򰡿� �ݿ� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateSrvKCSI(String sEvalYear, String sEvalNum)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);            // 1. �򰡳⵵
	        param.setValueParamter(i++, sEvalNum);             // 2. ��ȸ��      
	  
	        i = 0;
	  //      param.setWhereClauseParameter(i++, sEvalYear);     // 3. �򰡳⵵
	  //      param.setWhereClauseParameter(i++, sEvalNum);      // 4. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev1110_u02", param);
	        return dmlcount;
	    }

}
