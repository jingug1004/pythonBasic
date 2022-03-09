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
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

        saveState(ctx);

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
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
    		// 쪽지 전송
    		Util.sendMessenger(SESSION_USER_ID, sRecvId, sCntnt, sUrl);
    		
    		// 전송기록 저장
    		aRecvId   = sRecvId.split(",");
        	for (int i=0;i<aRecvId.length;i++) {

            	record.setAttribute("RECV_ID", 		aRecvId[i]);
            	record.setAttribute("HP_NO", 		null);
            	record.setAttribute("RECV_USER_NM", null);

            	nSaveCount += insertAlarmHist(record);
        	}
    	} else {
    	
			//문자메세지 전송
	    	String sSendHpNo = (String)ctx.get("SEND_HP_NO");
	    	String sSendUserNm = (String)ctx.get("SEND_USER_NM");
	    	String sRecvUserNm = (String)ctx.get("RECV_USER_NM");
	    	
	    	record.setAttribute("SEND_HP_NO", sSendHpNo);
	    	record.setAttribute("SEND_USER_NM", sSendUserNm);
	
	    	// 동시에 여러 명에게 전송하는 경우(수신자사번, 수신자 전화번호, 수신자이름)
	    	aRecvId   = sRecvId.split(",");
	    	aRecvHpNo = sHpNo.split(",");
	    	aRecvNm   = sRecvUserNm.split(",");
	    	
	    	for (int i=0;i<aRecvId.length;i++) {
	
	        	record.setAttribute("RECV_ID", 		aRecvId[i]);
	        	record.setAttribute("HP_NO", 		aRecvHpNo[i]);
	        	record.setAttribute("RECV_USER_NM", aRecvNm[i]);
	
	        	// 90자이내인 경우 단문 문자전송
	        	if (Util.lengthB(sCntnt) <= 90) {
		    		sSmsSeqNo = getSmsDataSeq();
		    		record.setAttribute("SMS_SEQ_NO", sSmsSeqNo);
		    		
		    		insertAlarmSmsData(record);			//SMS DATA 입력
	        	} else {
		    		sSmsSeqNo = getLmsDataSeq();
		    		record.setAttribute("SMS_SEQ_NO", sSmsSeqNo);
	
		    		insertAlarmLmsData(record);    		//LMS DATA 입력
	        	}
		
	        	nSaveCount += insertAlarmHist(record);
	    	}
    	}
	    Util.setSaveCount  (ctx, nSaveCount);
	}

    


    /**
     * <p> 알림이력 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAlarmHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));	//알람구분
        param.setValueParamter(i++, record.getAttribute("RECV_ID"));	//받는사람ID
        
        //param.setValueParamter(i++, record.getAttribute("ALARM_GBN"));	//알람구분
        //param.setValueParamter(i++, record.getAttribute("RECV_ID"));	//받는사람ID
        
        param.setValueParamter(i++, record.getAttribute("MN_ID"));		//메뉴ID
        param.setValueParamter(i++, record.getAttribute("HP_NO"));		//이동전화번호
        
        param.setValueParamter(i++, record.getAttribute("TITLE"));		//제목
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		//내용
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));	
        
        param.setValueParamter(i++, SESSION_USER_ID);					//작성자
        
     
        
        int dmlcount = this.getDao("rbmdao").update("rsy7010_i01", param);
        
        return dmlcount;
    }

    
    /**
     * <p> 알림이력 수정  </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAlarmHist(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));		//테이블명
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ALARM_GBN" ));	//첨부파일키
        param.setWhereClauseParameter(i++, record.getAttribute("RECV_ID" ));	//첨부파일키
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO" ));			//순번
        
        int dmlcount = this.getDao("rbmdao").update("rsy7010_u01", param);
        return dmlcount;
    }
	

    /**
     * <p> SMS DATA 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
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
    	sReqDate = "00000000";					//예약발송일자	
    	sReqTime = "00000000";					//예약발송시간	
        if (!Util.isNull(sReqDateTime)) {
        	if (sReqDateTime.length()==16) {
	        	sReqDate = sReqDateTime.substring(0,8);	//예약발송일자	
	        	sReqTime = sReqDateTime.substring(8);	//예약발송시간
        	}
        }


        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));				//연번
        
        param.setValueParamter(i++, sRecvHpNo.substring(0, 3));						//수신자 번호1 HP_NO
        param.setValueParamter(i++, sRecvHpNo.substring(3, nRecvHpNo-4));			//수신자 번호2        
        param.setValueParamter(i++, sRecvHpNo.substring(nRecvHpNo-4, nRecvHpNo));	//수신자 번호3
        param.setValueParamter(i++, record.getAttribute("RECV_USER_NM"));			//수신자명
        
        param.setValueParamter(i++, sSendHpNo.substring(0, 3));						//송신자 번호1 
        param.setValueParamter(i++, sSendHpNo.substring(3, nSendHpNo-4));			//송신자 번호2        
        param.setValueParamter(i++, sSendHpNo.substring(nSendHpNo-4, nSendHpNo));	//송신자 번호3        
        param.setValueParamter(i++, record.getAttribute("SEND_USER_NM"));			//송신자명
        
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS 내용 

        param.setValueParamter(i++, sReqDate);		  			//예약발송일자 
        param.setValueParamter(i++, sReqTime);		  			//예약발송시간 
        param.setValueParamter(i++, SESSION_USER_ID);		//보내는사람 ID

        int dmlcount = this.getDao("rbmdao").update("rsy7010_i02", param);
        
        return dmlcount;
    }

    /**
     * <p> SMS DATA  KEY 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getSmsDataSeq() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy7010_s02");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SMS_SEQ_NO"));	//첨부파일키
   
        return rtnKey;
    }
    /**
     * <p> LMS DATA 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
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
        	sReqTime = "00000000000000";	//예약발송이 아닌 경우 즉시 발송으로 설정	
        }
        
        param.setValueParamter(i++, record.getAttribute("SMS_SEQ_NO"));				//연번        
        param.setValueParamter(i++, sReqTime);										//예약발송일시        
        param.setValueParamter(i++, sRecvHpNo);										//수신자 번호
        param.setValueParamter(i++, sSendHpNo);										//송신자 번호        
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS 내용 : 제목용
        param.setValueParamter(i++, record.getAttribute("CNTNT"));		  			//SMS 내용 

        int dmlcount = this.getDao("rbmdao").update("rsy7010_i03", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> LMS DATA  KEY 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getLmsDataSeq() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy7010_s03");        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
        rtnKey = String.valueOf(pr[0].getAttribute("SMS_SEQ_NO"));	//첨부파일키
   
        return rtnKey;
    }
}
    
