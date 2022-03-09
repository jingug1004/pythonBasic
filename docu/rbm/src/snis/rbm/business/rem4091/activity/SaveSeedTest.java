/*================================================================================
 * �ý���			: ���ǰ���
 * �ҽ����� �̸�	: snis.rbm.business.rbs1020.activity.SaveBusiPlanDetlReg.java
 * ���ϼ���		: �����ȹ�󼼵��
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem4091.activity;

import java.util.Iterator;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSeedTest extends SnisActivity {
		public SaveSeedTest(){}
		
		
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
	        
	        sDsName = "dsSeedTest";
	        
	        if ( ctx.get(sDsName) != null ) {
	        	
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            nTempCnt = insertBusiPlanDetl(record);
		        }	        
	        }

	        Util.setSaveCount  (ctx, nSaveCount     );
	        
	    }
	    
	    /**
	     * <p> �����ȹ�󼼰�ȹ ��� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertBusiPlanDetl(PosRecord record)
	    {		
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("COMM_NO"));     // 1.�⵵
	        param.setValueParamter(i++, record.getAttribute("DIV_NO"));   // 2.��������
	        param.setValueParamter(i++, record.getAttribute("TRANSITION_SEQ"));    // 3.ȸ�豸���ڵ�
	        param.setValueParamter(i++, record.getAttribute("MACHINE_ID"));       // 4.�����
	        param.setValueParamter(i++, record.getAttribute("TRADE_DATE"));   // 5.���αݾ�
	        param.setValueParamter(i++, record.getAttribute("TERM_ID"));       // 6.�μ��ڵ� 
	        param.setValueParamter(i++, record.getAttribute("CARD_ID"));        // 7.�����ID	        
	        param.setValueParamter(i++, record.getAttribute("CARD_SERIAL"));   // 8. ����������	        
	        param.setValueParamter(i++, record.getAttribute("REQUEST_FEE"));   // 9. ������밳��	        
	        param.setValueParamter(i++, record.getAttribute("CARD_USER_CODE"));       // 10. ������� ����
	        param.setValueParamter(i++, record.getAttribute("CARD_USER_TYPE"));       // 11. ������� ����	        
	        param.setValueParamter(i++, record.getAttribute("IDATE"));            // 12.  1�б�
	        
	        int dmlcount = this.getDao("rbmdao").update("rem4091_i02", param);
	        
	        return dmlcount;
	    }
	    
}
