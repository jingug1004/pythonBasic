/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02070020.activity.SaveTrngExptRacer.java
 * 파일설명		: 훈련예정선수 등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02070020.activity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.StringTokenizer;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 훈련예정선수를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SaveTrngExptRacer extends SnisActivity
{    
    public SaveTrngExptRacer()
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
        int nSize        = 0;

        ds    = (PosDataset)ctx.get("dsOutTrngExptRacer");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            logger.logInfo(record);
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
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        ds    = (PosDataset) ctx.get("dsOutTrngExptRacer");
        nSize = ds.getRecordCount();

        // 삭제
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
            {
            	nDeleteCount = nDeleteCount + deleteTrngExptRacer(record);
            }
        }
        
        // 등록
        String sTrngExptSeq = getTrngExptSeq();
        for ( int i = 0; i < nSize; i++ ) 
        {
	        PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
                if ( (nTempCnt = updateTrngExptRacer(record)) == 0 ) {
                	// 기존 자료가 존재하는 여부를 체크
                	/* 2013.4.10 이수를 못하는 경우가 존재하여 다시 재교육을 요청할 수 있음(조정희과장 요청사항)
	        		if( ExptRaceKeyCount(record) > 0 ) {
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_boa");
		            		
		            		String sDate = (String)record.getAttribute("RACER_NO");
		            		Util.setSvcMsg(ctx, "선수번호[ " + sDate + " ]는  이미 등록되어 있습니다.");
		            		return;
		            	}
	        		}
	        		*/
                	sTrngExptSeq = getNextTrngExptSeq(sTrngExptSeq);
                	nTempCnt = insertTrngExptRacer(record, sTrngExptSeq);
                	
                	//희망훈련 & 커뮤니티 요청인 경우  접수상태로 변경하고 SMS를 발송한다. (기존 승인정보가 아닌경우에만 발송)
                	//if(!"200".equals(record.getAttribute("ACCEPT_STAT")) && "004".equals(record.getAttribute("TRNG_RSN_CD")) && !"".equals(Util.NVL(record.getAttribute("TRNG_RQST_SEQ")))){
            		if(!"200".equals(record.getAttribute("ACCEPT_STAT")) && !"".equals(Util.NVL(record.getAttribute("TRNG_RQST_SEQ")))){
                    	updateTrngRqst(record);
                		
                		String msg = "신청하신 훈련이 접수되었습니다. - 경정 운영팀";
                		sendSms(Util.trim(record.getAttribute("CELPON_NO")), Util.trim(record.getAttribute("NM_KOR")), msg);
                	}
                }

            	nSaveCount = nSaveCount + nTempCnt;
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 훈련예정선수 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected String getTrngExptSeq()
    {
        PosParameter param = new PosParameter();
        PosRowSet rowset = this.getDao("boadao").find("tbeb_trng_expt_racer_fb001", param);

        String sTrngExptSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            sTrngExptSeq = (String) row.getAttribute("TRNG_EXPT_SEQ".trim());
        }
            
        return sTrngExptSeq;
    }

    /**
     * <p> 훈련예정선수 일련번호에 해당하는 다음 일련번호 조회 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected String getNextTrngExptSeq(String sTrngExptSeq)
    {
        String sNextTrngExptSeq = sTrngExptSeq.substring(0, 3);
        int nTemp = Integer.parseInt(sTrngExptSeq.substring(3)) + 1;
        
        return sNextTrngExptSeq = sNextTrngExptSeq + Util.getFormat("0000", Integer.toString(nTemp));
    }

    /**
     * <p> 훈련예정선수 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTrngExptRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR        ".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_DD_NUM ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_STAT_CD".trim()));
        param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_SEQ       ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> 훈련예정선수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertTrngExptRacer(PosRecord record, String sTrngExptSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sTrngExptSeq                                   );
        param.setValueParamter(i++, record.getAttribute("RACER_NO         ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD      ".trim()));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR        ".trim()));
        param.setValueParamter(i++, record.getAttribute("SANC_ISSUE_NO    ".trim()));
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_DD_NUM ".trim()));
        param.setValueParamter(i++, "000");
        param.setValueParamter(i++, record.getAttribute("RMK              ".trim()));
        param.setValueParamter(i++, record.getAttribute("INJURY_SEQ       ".trim()));
        param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD      ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_SEQ"));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_ib001", param);
        return dmlcount;
    }
    
    /**
     * <p> 훈련예정선수 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteTrngExptRacer(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRNG_EXPT_SEQ    ".trim()));

        int dmlcount = this.getDao("boadao").update("tbeb_trng_expt_racer_db001", param);
        return dmlcount;
    }
    
    /**
     * <p> 훈련예정선수가 기존에 등록되었는지 여부를 체크 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int ExptRaceKeyCount(PosRecord record) 
    {
    	String sTrngRsnCd = String.valueOf(record.getAttribute("TRNG_RSN_CD"));
    	
        PosParameter param = new PosParameter();
        int i = 0;

        if (sTrngRsnCd.equals("001") || sTrngRsnCd.equals("002") ||  
            sTrngRsnCd.equals("003")) {	// 제재, 출발위반, 부상훈련
        	
            param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));  	// 선수번호
        	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));  	// 기준년도
        	param.setWhereClauseParameter(i++, record.getAttribute("SANC_ISSUE_NO"));  	// 일련번호
        	param.setWhereClauseParameter(i++, record.getAttribute("INJURY_SEQ"));  // 부상일련번호

        	PosRowSet rtnRecord = this.getDao("boadao").find("tbeb_trng_expt_racer_kc001", param);  
            
            PosRow pr[] = rtnRecord.getAllRow();
            
            String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
            
            return Integer.valueOf(rtnQty).intValue();

        	
        } else {
        	return 0;
        }

    }
    
    /**
     * <p> 훈련신청내역 update </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  none
     * @throws  none
     */
    protected void updateTrngRqst(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, record.getAttribute("TRNG_RQST_SEQ"));

        this.getDao("boadao").update("tbeb_trng_rqst_ub002", param);
    }
    
    
  //SMS 전송
	private boolean sendSms(String recPhoNo, String recNm, String msg){		
		String recPho1 = "";
		String recPho2 = "";
		String recPho3 = "";
		
		String senPho1 = "";
		String senPho2 = "";
		String senPho3 = "";
		
		StringTokenizer recSt = new StringTokenizer(recPhoNo, "),-");
		
		recPho1 = recSt.nextToken().trim();
		recPho2 = recSt.nextToken().trim();
		recPho3 = recSt.nextToken().trim();
		
		StringTokenizer senSt = new StringTokenizer(SESSION_TEL_NO, "),-");
		
		senPho1 = senSt.nextToken().trim();
		senPho2 = senSt.nextToken().trim();
		senPho3 = senSt.nextToken().trim();
		
		boolean isSuccess = false;
		CallableStatement proc = null;
		Connection conn = null;
	
		try{
			conn = this.getDao("boadao").getDBConnection();
			proc = conn.prepareCall("{CALL SMS.SP_SEND_SMS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1, recPho1);
			proc.setString(2, recPho2);
			proc.setString(3, recPho3);
			proc.setString(4, recNm);
			proc.setString(5, senPho1);
			proc.setString(6, senPho2);
			proc.setString(7, senPho3);
			proc.setString(8, SESSION_USER_NM);
			proc.setString(9, msg);
			proc.setString(10, "00000000");
			proc.setString(11, "00000000");
			proc.setString(12, "MRASYS");
			proc.setString(13, SESSION_USER_ID);
			
			isSuccess = proc.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
		
		return isSuccess;
	}
    
}
