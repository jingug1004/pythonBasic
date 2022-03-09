/*================================================================================
 * �ý���			: �ĺ�������
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d09000001.activity.SaveHealth.java
 * ���ϼ���		: �ǰ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.common.util;

import snis.can_boa.common.util.Util;
import snis.can_boa.common.util.SnisActivity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveAlarmHist extends SnisActivity {

	public SaveAlarmHist(){
		
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
    	String[] aRecvId;
    	String[] aRecvHpNo;
    	String[] aRecvNm;

    	String sAlarmGbn = (String)ctx.get("ALARM_GBN");
    	String sRecvId = (String)ctx.get("RECV_ID");
    	String sMnId = (String)ctx.get("MN_ID");
    	String sHpNo = (String)ctx.get("HP_NO");
    	String sTitle = (String)ctx.get("TITLE");
    	String sCntnt = (String)ctx.get("CNTNT");
    	

    	PosRecord record = new PosRecord();
    	record.setAttribute("ALARM_GBN", sAlarmGbn);
    	record.setAttribute("RECV_ID", sRecvId);
    	record.setAttribute("MN_ID", sMnId);
    	record.setAttribute("HP_NO", sHpNo);
    	record.setAttribute("TITLE", sTitle);
    	record.setAttribute("CNTNT", sCntnt);


    	String sSmsSeqNo = "";
    	if(sAlarmGbn.equals("002")){
        	String sSendHpNo = (String)ctx.get("SEND_HP_NO");
        	String sSendUserNm = (String)ctx.get("SEND_USER_NM");
        	String sRecvUserNm = (String)ctx.get("RECV_USER_NM");
        	
        	record.setAttribute("SEND_HP_NO", sSendHpNo);
        	record.setAttribute("SEND_USER_NM", sSendUserNm);
        	record.setAttribute("RECV_USER_NM", sRecvUserNm);
        	
	    	// ���ÿ� ���� ���� �����ϴ� ���(�����ڻ��, ������ ��ȭ��ȣ, �������̸�)
	    	aRecvId   = sRecvId.split(",");
	    	aRecvHpNo = sHpNo.split(",");
	    	aRecvNm   = sRecvUserNm.split(",");
	    	
	    	for (int i=0;i<aRecvId.length;i++) {
	
	        	record.setAttribute("RECV_ID", 		aRecvId[i]);
	        	record.setAttribute("HP_NO", 		aRecvHpNo[i]);
	        	record.setAttribute("RECV_USER_NM", aRecvNm[i]);
	
	        	// 90���̳��� ��� �ܹ� ��������
	        	if (Util.lengthB(sCntnt) <= 90) {
		    		sSmsSeqNo = getSmsDataSeq();
		    		record.setAttribute("SMS_SEQ_NO", sSmsSeqNo);
		    		
		    		insertAlarmSmsData(record);			//SMS DATA �Է�
	        	} else {
		    		sSmsSeqNo = getLmsDataSeq();
		    		record.setAttribute("SMS_SEQ_NO", sSmsSeqNo);
	
		    		insertAlarmLmsData(record);    		//LMS DATA �Է�
	        	}
		
	        	nSaveCount += insertAlarmHist(record);
	    	}
    	}
    	
    	
    	nSaveCount = insertAlarmHist(record);
    	
    	
        Util.setSaveCount  (ctx, nSaveCount     );

    }
    


    /**
     * <p> �˸��̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAlarmHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));	//�˶�����
        param.setValueParamter(i++, record.getAttribute("RECV_ID"));	//�޴»��ID
        
        //param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));	//�˶�����
        //param.setValueParamter(i++, record.getAttribute("RECV_ID"));	//�޴»��ID
        
        param.setValueParamter(i++, record.getAttribute("MN_ID"));		//�޴�ID
        param.setValueParamter(i++, record.getAttribute("HP_NO"));		//�̵���ȭ��ȣ
        
        param.setValueParamter(i++, record.getAttribute("TITLE"));		//����
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		//����
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));	
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
        
     
        
        int dmlcount = this.getDao("candao").update("common_insert_alarm_hist", param);
        
        return dmlcount;
    }

    
    /**
     * <p> �˸��̷� ����  </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAlarmHist(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));		//���̺��
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ALARM_GBN" ));	//÷������Ű
        param.setWhereClauseParameter(i++, record.getAttribute("RECV_ID" ));	//÷������Ű
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));			//����
        
        int dmlcount = this.getDao("candao").update("common_update_alarm_hist", param);
        return dmlcount;
    }
	
    
    /**
     * <p> SMS DATA �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAlarmSmsData(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
   
        String sSendHpNo = (String)record.getAttribute("SEND_HP_NO");
        if(sSendHpNo == null) sSendHpNo= "";
        sSendHpNo = sSendHpNo.replaceAll("-", "").replaceAll("\\)", "").replaceAll(" ", "");
        
        int nSendHpNo = sSendHpNo.length();
        //if(nSendHpNo<10) return 0;
        
        String sRecvHpNo = (String)record.getAttribute("HP_NO");
        if(sRecvHpNo == null) sRecvHpNo= "";
        sRecvHpNo = sRecvHpNo.replaceAll("-", "").replaceAll("\\)", "").replaceAll(" ", "");

        int nRecvHpNo = sRecvHpNo.length();
        
        if(nRecvHpNo<10) return 0;
        

        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));				//����
        
        param.setValueParamter(i++, sRecvHpNo.substring(0, 3));						//������ ��ȣ1 HP_NO
        param.setValueParamter(i++, sRecvHpNo.substring(3, nRecvHpNo-4));			//������ ��ȣ2        
        param.setValueParamter(i++, sRecvHpNo.substring(nRecvHpNo-4, nRecvHpNo));	//������ ��ȣ3
        param.setValueParamter(i++, record.getAttribute("RECV_USER_NM"));			//�����ڸ�
        
        param.setValueParamter(i++, sSendHpNo.substring(0, 3));						//�۽��� ��ȣ1 
        param.setValueParamter(i++, sSendHpNo.substring(3, nSendHpNo-4));			//�۽��� ��ȣ2        
        param.setValueParamter(i++, sSendHpNo.substring(nSendHpNo-4, nSendHpNo));	//�۽��� ��ȣ3        
        param.setValueParamter(i++, record.getAttribute("SEND_USER_NM"));			//�۽��ڸ�
        
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS ���� 
        param.setValueParamter(i++, SESSION_USER_ID);		//�����»�� ID


        int dmlcount = this.getDao("candao").update("common_insert_smsdata", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> SMS DATA  KEY ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getSmsDataSeq() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("candao").find("common_select_smsdata_key");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SMS_SEQ_NO"));	//÷������Ű
   
        return rtnKey;
    }
    

    
    /**
     * <p> LMS DATA  KEY ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getLmsDataSeq() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("candao").find("common_select_mmsdata_key");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SMS_SEQ_NO"));	//÷������Ű
   
        return rtnKey;
    }
    

    /**
     * <p> LMS DATA �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAlarmLmsData(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
   
        String sSendHpNo = (String)record.getAttribute("SEND_HP_NO");
        if(sSendHpNo == null) sSendHpNo= "";
        sSendHpNo = sSendHpNo.replaceAll("-", "").replaceAll("\\)", "").replaceAll(" ", "");
        
        //int nSendHpNo = sSendHpNo.length();        
        //if(nSendHpNo<10) return 0;
        
        String sRecvHpNo = (String)record.getAttribute("HP_NO");
        if(sRecvHpNo == null) sRecvHpNo= "";
        sRecvHpNo = sRecvHpNo.replaceAll("-", "").replaceAll("\\)", "").replaceAll(" ", "");

        if(sRecvHpNo.length()<10) return 0;
        
        String sReqTime = (String)record.getAttribute("REQ_TIME");
        if (Util.isNull(sReqTime)) {
        	sReqTime = "00000000000000";	//����߼��� �ƴ� ��� ��� �߼����� ����	
        }
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));				//����        
        param.setValueParamter(i++, sReqTime);										//����߼��Ͻ�        
        param.setValueParamter(i++, sRecvHpNo);										//������ ��ȣ
        param.setValueParamter(i++, sSendHpNo);										//�۽��� ��ȣ        
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS ���� : �����
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS ���� 

        int dmlcount = this.getDao("candao").update("common_insert_mmsdata", param);
        
        return dmlcount;
    }
    
}