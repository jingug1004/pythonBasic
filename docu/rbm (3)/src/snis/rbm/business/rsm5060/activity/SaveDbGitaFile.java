/*================================================================================
 * 시스템			: 기타소득세내역
 * 소스파일 이름	: snis.rbm.business.rsm5060.activity.SaveDbGitaFile
 * 파일설명		: 지급조서관리_기타소득세내역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-14
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.FileReader;

public class SaveDbGitaFile extends SnisActivity {
	
	public SaveDbGitaFile(){}

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

    protected void saveState(PosContext ctx) 
    {  	
    	String sFilePath = (String)ctx.get("FILE_PATH");	//폴더경로
    	String sPayYear  = (String)ctx.get("PAY_YEAR");		//지급년도
    	String sPayMonth = (String)ctx.get("PAY_MM");		//지급월
    	
    	if( getCfmCheck(sPayYear, sPayMonth) == -1) {
    		Util.setSvcMsgCode(ctx, "SNIS_RBM_D008");
    		Util.setReturnParam(ctx, "DBErr", "O");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	FileReader File = new FileReader();
    	list = File.executeInterface(sFilePath);

    	//기존삭제
    	deleteGitaCntnt(sPayYear, sPayMonth);

    	int nSaveCnt = insertGitaCntnt(list, sPayYear, sPayMonth);	//Insert


    	deleteGitaGrn(sPayYear, sPayMonth);    	//그린카드 기존삭제
    	insertGitaGrn(sPayYear, sPayMonth);	    //그린카드 판매지점으로 변경

    	nSaveCnt += updateGitaCntntGrn(sPayYear, sPayMonth);	//Insert
    	
    	Util.setSaveCount  (ctx, 0);
    }
    
    /**
     * <p> 기타소득세 추가 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertGitaCntnt(ArrayList list, String sPayYear,String sPayMonth) 
    {
    	RbmJdbcDao rbmdao  = this.getRbmDao("rbmjdbcdao");
    	String[] sArrMap   = new String[list.size()];
    	String sCommCd, sDivNo;
    	StringBuffer sb = new StringBuffer();

    	//파일을 읽어들여 DB INSERT
    	for(int i=0; i < list.size() ;i++) {  
    		
    		HashMap map = (HashMap)list.get(i);

    		//형식 맞추기    
    		//column3(지점번호)   => 한자리일 경우 앞자리에 '0' 추가 
    		//column4(투표서번호) => 한자리일 경우 앞자리에 '0' 추가 
    		//column14(기타)     => "\" 제거
    		
			int iWinAmt 	= Util.NVL(map.get("column11"),0) + Util.NVL(map.get("column17"),0) + Util.NVL(map.get("column23"),0) + Util.NVL(map.get("column29"),0) + Util.NVL(map.get("column35"),0) + Util.NVL(map.get("column41"),0);
			int iJegeupAmt 	= Util.NVL(map.get("column12"),0) + Util.NVL(map.get("column18"),0) + Util.NVL(map.get("column24"),0) + Util.NVL(map.get("column30"),0) + Util.NVL(map.get("column36"),0) + Util.NVL(map.get("column42"),0);
			int iGita1 		= Util.NVL(map.get("column13"),0) + Util.NVL(map.get("column19"),0) + Util.NVL(map.get("column25"),0) + Util.NVL(map.get("column31"),0) + Util.NVL(map.get("column37"),0) + Util.NVL(map.get("column43"),0);
			int iGita2		= Util.NVL(map.get("column14"),0) + Util.NVL(map.get("column20"),0) + Util.NVL(map.get("column26"),0) + Util.NVL(map.get("column32"),0) + Util.NVL(map.get("column38"),0) + Util.NVL(map.get("column44"),0);
			int iGitaPay 	= Util.NVL(map.get("column15"),0) + Util.NVL(map.get("column21"),0) + Util.NVL(map.get("column27"),0) + Util.NVL(map.get("column33"),0) + Util.NVL(map.get("column39"),0) + Util.NVL(map.get("column45"),0);
			
    		sCommCd = (String)map.get("column"+5);
    		if(sCommCd.length() == 1)	sCommCd = "0" + sCommCd;
    		sDivNo = (String)map.get("column"+6);
    		if(sDivNo.length() == 1)	sDivNo = "0" + sDivNo;
    		
    		sb.delete(0, sb.length()); // StringBuffer 비우기
    		
    		sb.append(" INSERT INTO TBRD_GITA_CNTNT ( ");
    		sb.append("             SELL_CD         " );
    		sb.append("             ,MEET_CD        " );
    		sb.append("             ,PERF_NO        " );
    		sb.append("             ,BRNC_CD        " );
    		sb.append("             ,DIV_NO         " );
    		sb.append("             ,REFUND_AMT     " );
    		sb.append("             ,SELL_AMT       " );
    		sb.append("             ,TSN_NO         " );
    		sb.append("             ,PAY_AMT        " );
    		sb.append("             ,COST           " );
    		sb.append("             ,INCO_TAX       " );
    		sb.append("             ,INHA_TAX       " );
    		sb.append("             ,DIF_PAY_AMT    " );
    		sb.append("             ,PAY_YEAR       " );
    		sb.append("             ,PAY_MM         " );
    		sb.append("             ,INST_ID        " );
    		sb.append("             ,INST_DT        " );
    		sb.append(" 			) VALUES (      " );
    		sb.append(" '0" +  (String)map.get("column"+0)   +"' " );
    		sb.append(",'00"+  (String)map.get("column"+1)   +"' " );
    		sb.append(",'"   +  (String)map.get("column"+2)  +"' " );
    		sb.append(",'"   +  sCommCd  +                    "' " );
    		sb.append(",'"   +  sDivNo   +                    "' " );
    		sb.append(",'"   +  (String)map.get("column"+7)  +"' " );
    		sb.append(",'"   +  (String)map.get("column"+8)  +"' " );
    		sb.append(",'"   +  (String)map.get("column"+9)  +"' " );
    		sb.append(",'"   +  iJegeupAmt  +"' " );
    		sb.append(",'"   +  iWinAmt 	+"' " );
    		sb.append(",'"   +  iGita1 		+"' " );
    		sb.append(",'"   +  iGita2 		+"' " );
    		sb.append(",'"   +  iGitaPay 	+"' " );
    		sb.append(",'"   +  sPayYear +                    "' " );
    		sb.append(",'"   +  sPayMonth +                   "' " );
    		sb.append(",'"   +  SESSION_USER_ID +             "' " );
    		sb.append(", SYSDATE                               ) " );
    		
    		sArrMap[i] = sb.toString();
    	}

    	int nRtn     = 0;
    	int nFailCnt = 0;
    	
    	int[] insertCounts = rbmdao.executeBatch( sArrMap );
    	
		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				nFailCnt++;
			} else {
				nRtn += insertCounts[i];
			}
		}
		
		if( nFailCnt > 0 ) {
			return nFailCnt*-1; 
		}
		
    	return nRtn;
    }
    
    /**
     * <p> 확정여부 검사  </p>
     * @param   PosGenericDao dao			DB Connect 정보
     *          String        sAttFileKey	FileKey
     * @return  nRtnKey	int			        sAttFileKey데 대한 첨부파일 개수
     * @throws  none
     */
	public int getCfmCheck(String sPayYear, String sPayMm)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int nRtnKey = -1;
        
        param.setWhereClauseParameter(i++, sPayYear);	//지급년도
        param.setWhereClauseParameter(i++, sPayMm);	    //지급월
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        
        if( pr.length > 1) {
        	return -1;	//확정과 미확정이 섞여있을 경우
        } else if( pr.length == 0) {
        	return 0;	
        }
        
        String sRtnKey = String.valueOf(pr[0].getAttribute("CFM_CD"));
        
        if( sRtnKey.equals("001")) {
        	nRtnKey = -1;	//확정
        } else {
        	nRtnKey = 0;	//미확정
        }
        
        return nRtnKey;
    }
	
    /**
     * <p> 입력받은 년도/월의 기타소득세 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGitaCntnt(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//지급년도
        param.setValueParamter(i++, sPayMm);	//지급월
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d01", param);

        return dmlcount;
    }
    

    /**
     * <p> 입력받은 년도/월의 기타소득세 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteGitaGrn(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//지급년도
        param.setValueParamter(i++, sPayMm);	//지급월
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d03", param);

        return dmlcount;
    }
    

    /**
     * <p> 그린카드DB에서 년도/월의 기타소득세 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertGitaGrn(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//입력자 사번
        param.setValueParamter(i++, sPayYear+sPayMm);			//지급년월
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_i02", param);

        return dmlcount;
    }

    /**
     * <p> 기타소득세(그린카드) 자료 변경 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateGitaCntntGrn(String sPayYear,String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//수정자 사번
        param.setValueParamter(i++, sPayYear);			//지급년도
        param.setValueParamter(i++, sPayMm);			//지급월
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_u01", param);

        return dmlcount;
    }
    
}