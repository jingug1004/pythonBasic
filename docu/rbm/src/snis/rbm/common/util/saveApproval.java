/*================================================================================
 * �ý���			: ����
 * �ҽ����� �̸�	: snis.rbm.common.util.saveApproval.java
 * ���ϼ���		: �����Ϸù�ȣ ��ȸ �� ������� ����
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class saveApproval extends PosActivity
{    
    public saveApproval()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	Util.clearReturnParam(ctx);
    	
        String sSeqNo = getSeqNo(ctx);
        
        Util.addReturnParam(ctx);
        
        //saveAppr(ctx, sSeqNo);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �����Ϸù�ȣ ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private String getSeqNo(PosContext ctx)
    {
    	String[] arrRet = {""};
        PosParameter param = new PosParameter();        
        
        String sJob 		= Util.getCtxStr(ctx, "JOB");
		String sStndYear 	= Util.getCtxStr(ctx, "STND_YEAR");		
        String sTms 		= Util.getCtxStr(ctx, "TMS");
        String sDayOrd 		= Util.getCtxStr(ctx, "DAY_ORD");
        String sRaceNo 		= Util.getCtxStr(ctx, "RACE_NO");
        String sUserId 		= Util.getCtxStr(ctx, "USER_ID");
        String sFirstSeq	= sJob+sStndYear+sTms+sDayOrd+sRaceNo+sUserId;
        
        int iParam = 0;
        param.setWhereClauseParameter(iParam++, sJob);
    	param.setWhereClauseParameter(iParam++, sStndYear);
    	param.setWhereClauseParameter(iParam++, sTms);
    	param.setWhereClauseParameter(iParam++, sDayOrd);
    	param.setWhereClauseParameter(iParam++, sRaceNo);
    	param.setWhereClauseParameter(iParam++, sUserId);
    	
    	PosRowSet prs = this.getDao("rbmdao").find("common_approval_seq_no", param);
        
    	PosRow pr[] = prs.getAllRow();    	  		
    	arrRet[0] = sFirstSeq+Util.getRosStr(pr[0], "SEQ");		
    	Util.setReturnParam(ctx, "SEQ", arrRet[0]);
    	
    	return arrRet[0].toString();
    	
    }
    
    
    /**
     * <p> ������� ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private int saveAppr(PosContext ctx, String sSeqNo)
    {
    	PosParameter param = new PosParameter();        
        
        String sJob 		= Util.getCtxStr(ctx, "JOB");
		String sStndYear 	= Util.getCtxStr(ctx, "STND_YEAR");		
        String sTms 		= Util.getCtxStr(ctx, "TMS");
        String sDayOrd 		= Util.getCtxStr(ctx, "DAY_ORD");
        String sRaceNo 		= Util.getCtxStr(ctx, "RACE_NO");
        String sSeq 		= Util.getCtxStr(ctx, "SEQ");
        String sUserId 		= Util.getCtxStr(ctx, "USER_ID");
        String sSubject		= Util.getCtxStr(ctx, "SUBJECT");
                
        int iParam = 0;
        param.setValueParamter(iParam++, sSeqNo);		//�Ϸù�ȣ
        param.setValueParamter(iParam++, sJob);			//��������
    	param.setValueParamter(iParam++, sStndYear);	//���س⵵
    	param.setValueParamter(iParam++, sTms);			//ȸ��
    	param.setValueParamter(iParam++, sDayOrd);		//����
    	param.setValueParamter(iParam++, sRaceNo);		//���ֹ�ȣ
    	param.setValueParamter(iParam++, sSeq);			//����
    	param.setValueParamter(iParam++, sUserId);		//�����
    	param.setValueParamter(iParam++, sSubject);		//��������
    	param.setValueParamter(iParam++, sUserId);		//�ۼ���ID
    	param.setValueParamter(iParam++, sUserId);		//������ID
    	
    	
    	int dmlcount = this.getDao("rbmdao").update("common_approval_ins", param);
        
    	return dmlcount;
    	
    }
}

