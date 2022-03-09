/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030036.activity.SaveMailNoti.java
 * ���ϼ���		: �����ּ��뺸���
 * �ۼ���			: ����ȭ
 * ����			: 1.0.0
 * ��������		: 2010-03-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030036.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �����ּ��뺸�� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @version 1.0
*/
public class SaveMailNoti extends SnisActivity
{
    public SaveMailNoti()
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
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
	        
        sDsName = "dsOutMailNotiList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int nTempCnt     = 0; 
        String sDsName   = "";

        String StndYear = (String)ctx.get("STND_YEAR");
        String Seq = (String)ctx.get("SEQ");
        String Title = (String)ctx.get("TITLE");
        String Sender = (String)ctx.get("SENDER");
        String MailSendReqDay = (String)ctx.get("MAIL_SEND_REQ_DAY");
        String Notice = (String)ctx.get("NOTICE");
        
    	PosDataset ds;
        int nSize        = 0;

        sDsName = "dsOutMailNotiList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
       		nTempCnt = insertMailNoti(StndYear, Seq, Title, Sender, MailSendReqDay, Notice);
       		// �����ּ��뺸���  ���
       		for ( int i = 0; i < nSize; i++ ) 
           	{
           		PosRecord record = ds.getRecord(i);
           		if ( record.getAttribute("CHK").equals("1") ) {
               		nTempCnt = insertMailNotiList(record, ctx);
               		nSaveCount = nSaveCount + nTempCnt;
               	}
           	}
        }

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> �����ּ��뺸 ������ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMailNoti(String StndYear, String Seq, 
    							String Title, String Sender, String MailSendReqDay, String Notice) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, StndYear);
        param.setValueParamter(i++, Title);
        param.setValueParamter(i++, Sender);
        param.setValueParamter(i++, Notice);
        param.setValueParamter(i++, MailSendReqDay);
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, StndYear);

        int dmlcount = this.getDao("boadao").update("tbeb_mail_noti_ib001", param);
        return dmlcount;
    }

    /**
     * <p> �����ּ��뺸��� �Է�(CHK �Ȱ͸�...) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMailNotiList(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR") );
        param.setValueParamter(i++, record.getAttribute("RACER_NO") );
        param.setValueParamter(i++, record.getAttribute("EMAIL_ADDR") );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));

        int dmlcount = this.getDao("boadao").update("tbeb_mail_noti_ib002", param);
        return dmlcount;
    }
    
}
