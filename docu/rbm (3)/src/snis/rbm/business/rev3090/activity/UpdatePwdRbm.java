/*================================================================================
 * �ý���			: ��й�ȣ�ʱ�ȭ
 * �ҽ����� �̸�	: snis.rbm.business.rev3090.activity.UpdatePwd.java
 * ���ϼ���		: �ٸ��� Ȩ�������� ��й�ȣ�� �ʱ�ȭ(�ֹι�ȣ �� 7�ڸ�) ��Ų��.
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-12-08
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev3090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.EncryptSHA256;

public class UpdatePwdRbm extends SnisActivity {
		public UpdatePwdRbm(){}

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

	    protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount = 0;
	    	int nSize      = 0;	
	    	
	    	String sDsName   = "";
	    	PosDataset ds;
	    	
	    	sDsName = "dsEvEmpRbm";
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("RES_NO")));
			        	nSaveCount += updatePwdRbm(record);		        	
			        }	        
		        }	        
	        }
	    	
	        Util.setSaveCount(ctx, nSaveCount);
	    }
	    
	    /**
	     * <p> ��й�ȣ �ʱ�ȭ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updatePwdRbm(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("PSWD"));			//��ȣ�ʱ�ȭ ��
	        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO" ));
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3090_u02", param);
	        
	        return dmlcount;
	    }	    
}