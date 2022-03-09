package snis.rbm.system.rsy7010.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

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
    	String sRecvId   = (String)ctx.get("RECV_ID");
    	String sMnId     = (String)ctx.get("MN_ID");
    	String sHpNo     = (String)ctx.get("HP_NO");
    	String sTitle    = (String)ctx.get("TITLE");
    	String sCntnt    = (String)ctx.get("CNTNT");
    	String sUrl      = (String)ctx.get("URL");
    	String sReqTime  = (String)ctx.get("REQ_TIME");
    	//System.out.println("sRecvId="+sRecvId);    	
    	//System.out.println("sHpNo="+sHpNo);    	
    	

    	PosRecord record = new PosRecord();
    	record.setAttribute("ALARM_GBN",	sAlarmGbn);
    	record.setAttribute("MN_ID", 		sMnId);
    	record.setAttribute("TITLE", 		sTitle);
    	record.setAttribute("CNTNT", 		sCntnt);
    	record.setAttribute("REQ_TIME", 	sReqTime);
    	
    	String sSmsSeqNo = "";
    	if(sAlarmGbn.equals("001")) {	
    		// ���� ����
    		Util.sendMessenger(SESSION_USER_ID, sRecvId, sCntnt, sUrl);
    		
    		// ���۱�� ����
    		aRecvId   = sRecvId.split(",");
        	for (int i=0;i<aRecvId.length;i++) {

            	record.setAttribute("RECV_ID", 		aRecvId[i]);
            	record.setAttribute("HP_NO", 		null);
            	record.setAttribute("RECV_USER_NM", null);

            	nSaveCount += insertAlarmHist(record);
        	}
    	} else {
    	
			//���ڸ޼��� ����
	    	String sSendHpNo = (String)ctx.get("SEND_HP_NO");
	    	String sSendUserNm = (String)ctx.get("SEND_USER_NM");
	    	String sRecvUserNm = (String)ctx.get("RECV_USER_NM");
	    	
	    	record.setAttribute("SEND_HP_NO", sSendHpNo);
	    	record.setAttribute("SEND_USER_NM", sSendUserNm);
	
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
	    Util.setSaveCount  (ctx, nSaveCount);
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
        
     
        
        int dmlcount = this.getDao("rbmdao").update("rsy7010_i01", param);
        
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
        
        int dmlcount = this.getDao("rbmdao").update("rsy7010_u01", param);
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
        
        String sReqDate = "-";
        String sReqTime = "-";
        String sReqDateTime = (String)record.getAttribute("REQ_TIME");
    	sReqDate = "00000000";					//����߼�����	
    	sReqTime = "00000000";					//����߼۽ð�	
        if (!Util.isNull(sReqDateTime)) {
        	if (sReqDateTime.length()==16) {
	        	sReqDate = sReqDateTime.substring(0,8);	//����߼�����	
	        	sReqTime = sReqDateTime.substring(8);	//����߼۽ð�
        	}
        }


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

        param.setValueParamter(i++, sReqDate);		  			//����߼����� 
        param.setValueParamter(i++, sReqTime);		  			//����߼۽ð� 
        param.setValueParamter(i++, SESSION_USER_ID);		//�����»�� ID

        int dmlcount = this.getDao("rbmdao").update("rsy7010_i02", param);
        
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
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy7010_s02");        
        	
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

        int dmlcount = this.getDao("rbmdao").update("rsy7010_i03", param);
        
        return dmlcount;
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
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy7010_s03");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SMS_SEQ_NO"));	//÷������Ű
   
        return rtnKey;
    }
}
    
