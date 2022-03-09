/*================================================================================
 * �ý���			: ������û���� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs6020.activity.SaveEvntMana.java
 * ���ϼ���		: �⵿����� ����ϰ� �����ϴ� ����� �����Ѵ�.
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs6020.activity;

import java.text.SimpleDateFormat; 
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.system.rsy7010.activity.SaveAlarmHist;

public class SaveSuprtStat extends SnisActivity {
	
	public SaveSuprtStat(){}

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

		
        String StatCd = (String)ctx.get("CNCL_STAT_CD");
        if ("100".equals(StatCd) || "110".equals(StatCd) || "120".equals(StatCd)) {	// ��ҿ�û�̳� ���ó���� ���
            // ������û���� ����
        	nDeleteCount += saveSuptCancel(ctx, StatCd);			        
        } else {
            // ������û���� ����
            nSaveCount += saveSuptStat(ctx);			        
        }
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
        
        return ;
    }

    /**
     * <p> ������û���� ��� ��û </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSuptStat(PosContext ctx) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        String StatCd = (String)ctx.get("STAT_CD");
        String BrncAprvId = null;        
        String MngrAprvId = null;
        String RsltAprvId = null;
        if ("002".equals(StatCd) || "012".equals(StatCd) || "022".equals(StatCd)) {	// ���ο�û�� �ش�μ� ������ ��ȸ�Ͽ� �����Ѵ�.
        	PosParameter param2 = new PosParameter();
        	PosRowSet rowset;
    		i = 0;            
    		
    		String teamCd = ctx.get("TEAM_CD").toString().substring(2);
            param2.setWhereClauseParameter(i++, teamCd);			//���ڵ�
            
    		rowset = this.getDao("rbmdao").find("rbs6020_s03", param2);
    		if (rowset.hasNext()) {		// ������ ������ ��ȸ(�� �μ�(��,��)�� �μ���)
				PosRow row = rowset.next();
				if ("002".equals(StatCd)) {
					BrncAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//BrncAprvId = "01047";
				} else if ("012".equals(StatCd)) {
					BrncAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//BrncAprvId = "01047";
				} else {
					RsltAprvId = (String)row.getAttribute("CAPTAIN_ID");
					//RsltAprvId = "01047";
				}
    		} else {
    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E027");
    			return -1;
    		}
        }
        i = 0;
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//����
        param.setValueParamter(i++, BrncAprvId);		//������ ���
        param.setValueParamter(i++, MngrAprvId);		//�����μ� ������ ���
        param.setValueParamter(i++, RsltAprvId);		//�����μ� ������ ���
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//����
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//����
        param.setValueParamter(i++, (String)ctx.get("STAT_CD"));		//����        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        param.setValueParamter(i++, (String)ctx.get("SEQ_NO"));			//������û ����
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_u01", param);
        
        return dmlcount;
    }

    /**
     * <p> ������û���� ��� ��û </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSuptCancel(PosContext ctx, String CancelStat) 
    {
    	String StatCd = null;
    	String CnclStatCd = null;
        PosParameter param = new PosParameter();   
        int i = 0;        
       
        if ("110".equals(CancelStat)) {
        	CnclStatCd = "002";
        	StatCd = "032";			// ���
        } else if ("120".equals(CancelStat)) {
        	CnclStatCd = "000";
        } else {
        	CnclStatCd = "001";		// ��ҿ�û
        }
        
        
        param.setValueParamter(i++, CnclStatCd);						//��ҿ�û����
        param.setValueParamter(i++, StatCd);							//����
        param.setValueParamter(i++, (String)ctx.get("CNCL_RSN"));		//��һ���
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        param.setValueParamter(i++, (String)ctx.get("SEQ_NO"));			//������û ����
                		
        int dmlcount = this.getDao("rbmdao").update("rbs6020_u02", param);
        
        return dmlcount;
    }
}