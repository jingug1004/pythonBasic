/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.updateUserTrace.java
 * ���ϼ���		: �α���
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-29
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * 
 * @auther ���缱
 * @version 1.0
 */
public class SaveUserTrace extends SnisActivity {
	public SaveUserTrace() {
	}

	/**
	 * <p>
	 * SaveStates Activity�� �����Ű�� ���� �޼ҵ�
	 * </p>
	 * 
	 * @param ctx
	 *            PosContext �����
	 * @return SUCCESS String sucess ���ڿ�
	 * @throws none
	 */
	public String runActivity(PosContext ctx) {

    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
		String sJobType = (String) ctx.get("JOB_TYPE".trim());
		
		if ("E".equals(sJobType)) {		// ������ ������� ��������
			finishUserTrace(ctx);
		} else if ("P".equals(sJobType)) {	// �������� ���� ����
			SavePersonalAccess(ctx);
		}

		return PosBizControlConstants.SUCCESS;
	}

	
	private void finishUserTrace(PosContext ctx) {
    	int nSaveCount   = 0; 
    	String sDsName   =  "dsUserTrace";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  
	        	nTempCnt = updateUserTrace(record);	        	
		        nSaveCount = nSaveCount + nTempCnt;
	        }	        
        }        
        Util.setSaveCount  (ctx, nSaveCount     );
	}	

    
    /**
     * <p> IP ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUserTrace(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("LOGOUT_DT"));	// �����Ͻ�

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("LOG_SEQ"));		// �������������Sequence

        int dmlcount = this.getDao("rbmdao").update("user_trace_finish", param);
        return dmlcount;
    }

	
	private void SavePersonalAccess(PosContext ctx) {
        PosParameter param = new PosParameter();  
        String sQueryId    = "personal_access_insert";
    	
		String sLogSeq     = (String) ctx.get("LOG_SEQ");
		String sFormId     = (String) ctx.get("FORM_ID");
		String sAccessType = (String) ctx.get("ACCESS_TYPE");
		String sAction     = (String) ctx.get("ACTION");
		String sParam      = (String) ctx.get("PARAM");
		
		logger.logInfo("SavePersonalAccess------------------>");
		logger.logInfo("FORM_ID["+sFormId+"]");
		logger.logInfo("ACCESS_TYPE["+sAccessType+"]");
		logger.logInfo("ACTION["+sAction+"]");
		logger.logInfo("PARAM["+sParam+"]");
		
    	int i = 0;		
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sLogSeq);			//
        param.setValueParamter(i++, sFormId);			//
        param.setValueParamter(i++, sAccessType);		//
        param.setValueParamter(i++, sAction);			//
        param.setValueParamter(i++, sParam);			//

        int nSaveCount = this.getDao("rbmdao").update(sQueryId, param);

        Util.setSaveCount  (ctx, nSaveCount     );		
        
	}	

    	
}
