/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030036.activity.SaveMailNoti.java
 * 파일설명		: 선수주선통보등록
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2010-03-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
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
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 선수주선통보를 저장(입력/수정)및 삭제하는 클래스이다.
* @version 1.0
*/
public class SaveMailNoti extends SnisActivity
{
    public SaveMailNoti()
    {
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
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
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
       		// 선수주선통보목록  등록
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
     * <p> 선수주선통보 마스터 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
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
     * <p> 선수주선통보목록 입력(CHK 된것만...) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
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
