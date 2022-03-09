/*================================================================================
 * 시스템			: 기타소득세 내역 확정
 * 소스파일 이름	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * 파일설명		: 기타소득세 확정내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-15
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveGitaCfmCntnt extends SnisActivity {
	
	public SaveGitaCfmCntnt(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sPayYear = (String)ctx.get("PAY_YEAR");	//지급년도
        String sPayMm   = (String)ctx.get("PAY_MM");	//지급월
      
        
        
        //불일치 데이터 존재
        if( getValidCnt(sPayYear, sPayMm) > 0 ) {
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsgCode(ctx, "SNIS_RBM_D005");
        		Util.setReturnParam(ctx, "DBErr", "O");	            		
        		return;
        	}
    	}
        
        
        // 일치 건수와 기타의 건수 비교
        
        // 기타 건수 조회 
        int nCnt = getCfmCnt(ctx);
        
        // 일치 건수 조회
        int intSameCount =  sameGitaCfmCntntCount(ctx);
        
        
        if( nCnt != intSameCount) {
       	 try { 
       			throw new Exception(); 
           	} catch(Exception e) {
           		this.rollbackTransaction("tx_rbm");
           		Util.setSvcMsgCode(ctx, "SNIS_RBM_D002");
           		Util.setReturnParam(ctx, "DBErr", "O");
           		return;
           	}
       }
        
        nSaveCount = saveGitaCfmCntntAll(ctx);
        
        Util.setReturnParam(ctx, "RESULT", "T"); // 결과
        Util.setSaveCount(ctx, nSaveCount);
        
    }

    /**
     * <p> 기타소득세 확정여부 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveGitaCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("PC_MEET_CD"));	//경륜장코드
        param.setValueParamter(i++, record.getAttribute("PC_SELL_CD"));	//운영처코드
        param.setValueParamter(i++, record.getAttribute("PC_TSN"));	    //경주권번호
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//지급년도    
        param.setValueParamter(i++, record.getAttribute("PAY_MM"));     //지금월

        param.setValueParamter(i++, record.getAttribute("CFM_CD"));		//확정코드
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);					//사용자ID(수성자)
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_m01", param);

        return dmlcount;
    }
    
    /**
     * <p> 기타소득세 일괄확정 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveGitaCfmCntntAll(PosContext ctx) 
    {	
    	PosRowSet prs = null;
    	PosRow[] pr = null;
        PosParameter param = new PosParameter();
        
        
        
        String PAY_YEAR = (String)ctx.get("PAY_YEAR");	// 지급년도
        String PAY_MM   = (String)ctx.get("PAY_MM");	// 지급월
        String MEET_CD = (String)ctx.get("MEET_CD");	// 경륜 경정 구분 코드
        String SELL_CD   = (String)ctx.get("SELL_CD");	// 판매처
        
        int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		
		prs = this.getDao("rbmdao").find("rsm5060_s09", param);
		pr = prs.getAllRow();
		
		String[] arrQuery = new String[pr.length];
		
		for(int prI=0;prI<pr.length;prI++)
		{	
			
			arrQuery[prI] = " 	MERGE INTO TBRD_GITA_CFM_CNTNT A  --기타소득세 확정내역	       \n"+ 
			"                     /* rsm5060_m01 */				       \n"+ 
			"             USING						       \n"+ 
			"                     (SELECT					       \n"+ 
			"                              '"+pr[prI].getAttribute("PC_MEET_CD")+"' AS MEET_CD     --경륜장코드	       \n"+ 
			"                             ,'"+pr[prI].getAttribute("PC_SELL_CD")+"' AS SELL_CD     --운영처코드	       \n"+  
			"                             ,'"+pr[prI].getAttribute("PC_PAY_YEAR")+"' AS PAY_YEAR    --지급년도	       \n"+ 
			"                             ,'"+pr[prI].getAttribute("PC_PAY_MM")+"' AS PAY_MM      --지급월	       \n"+
			"                             ,'001' AS CFM_CD      --확정코드	       \n"+ 
			"                             ,'"+SESSION_USER_ID+"' AS INST_ID     --작성자	       \n"+ 
			"                             ,'"+SESSION_USER_ID+"' AS UPDT_ID     --수정자	       \n"+ 
			"     								       \n"+ 
			"                        FROM    DUAL ) B			       \n"+ 
			"                 ON (						       \n"+ 
			"                         A.MEET_CD  = B.MEET_CD    --경륜장코드       \n"+ 
			"                     AND A.SELL_CD  = B.SELL_CD    --운영처코드       \n"+  
			"                     AND A.PAY_YEAR = B.PAY_YEAR   --지급년도	       \n"+ 
			"                     AND A.PAY_MM   = B.PAY_MM     --지급월	       \n"+ 
			"             )							       \n"+ 
			"             							       \n"+ 
			"             WHEN MATCHED THEN					       \n"+ 
			"                 UPDATE SET 					       \n"+ 
			"                      A.CFM_CD  = B.CFM_CD    --확정코드	       \n"+ 
			"                     ,A.UPDT_ID = B.UPDT_ID   --수정자		       \n"+ 
			"                     ,A.UPDT_DT = SYSDATE     --수정일시	       \n"+ 
			"                 						       \n"+ 
			"             WHEN NOT MATCHED THEN				       \n"+ 
			"                 INSERT (					       \n"+ 
			"             							       \n"+ 
			"                      A.MEET_CD      --경륜장코드		       \n"+ 
			"                     ,A.SELL_CD      --운영처코드		       \n"+
			"                     ,A.PAY_YEAR     --지급년도		       \n"+ 
			"                     ,A.PAY_MM       --지급월			       \n"+ 
			"                     						       \n"+ 
			"                     ,A.CFM_CD       --확정코드		       \n"+ 
			"                     ,A.INST_ID      --작성자			       \n"+ 
			"                     ,A.INST_DT      --작성일자		       \n"+ 
			"                     						       \n"+ 
			"                 ) VALUES (					       \n"+ 
			"                      B.MEET_CD      --경륜장코드		       \n"+ 
			"                     ,B.SELL_CD      --운영처코드		       \n"+
			"                     ,B.PAY_YEAR     --지급년도		       \n"+ 
			"                     ,B.PAY_MM       --지급월			       \n"+
			"                     ,B.CFM_CD          --확정코드		       \n"+ 
			"                     ,B.INST_ID      --작성자			       \n"+ 
			"                     ,SYSDATE        --작성일자		       \n"+ 
			"                 )						       \n";

	        	
		}
		
		int [] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
		int intResult = 0; // 결과값
		int failure_count = 0;
		
		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}        

        return intResult;
    }
    
    
    /**
     * <p> 기타소득세 일괄확정 갯수 카운트 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int sameGitaCfmCntntCount(PosContext ctx) 
    {
    	
    	PosRowSet prs = null;
    	PosRow[] pr = null;
        PosParameter param = new PosParameter();
        
        String PAY_YEAR = (String)ctx.get("PAY_YEAR");	// 지급년도
        String PAY_MM   = (String)ctx.get("PAY_MM");	// 지급월
        String MEET_CD = (String)ctx.get("MEET_CD");	// 경륜 경정 구분 코드
        String SELL_CD   = (String)ctx.get("SELL_CD");	// 판매처
        
        int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		
		prs = this.getDao("rbmdao").find("rsm5060_s10", param);
		pr = prs.getAllRow();
		
		int intCNT = Util.nullToInt(String.valueOf(pr[0].getAttribute("CNT")));
		
        return intCNT;
    }
    /**
     * <p> 기타소득세 전체 개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			기타소득세 개수
     * @throws  none
     */
    protected int getCfmCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_MM" ));  	//지급월
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 기타소득세 전체 개수 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			기타소득세 개수
     * @throws  none
     */
    protected int getCfmCnt(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, ctx.get("PAY_YEAR"));	//지급년도
        param.setWhereClauseParameter(i++, ctx.get("PAY_MM" ));  	//지급월
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> 기타소득세내역 일치여부 확인  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			일치여부( 0 : 일치 0초과 : 불일치)
     * @throws  none
     */
    protected int getValidCnt(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sPayYear);	//지급년도
        param.setWhereClauseParameter(i++, sPayMm);	    //지급월
        param.setWhereClauseParameter(i++, sPayYear);	//지급년도
        param.setWhereClauseParameter(i++, sPayMm);	    //지급월
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}
