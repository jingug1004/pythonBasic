/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03040010.activity.SaveEquipmentRandom.java
 * 파일설명		: 추첨랜덤정보  등록
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2008-02-27
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import java.math.BigDecimal;

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
* 매핑하여 추첨랜덤정보를  저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SaveEquipmentRandom extends SnisActivity
{    
	
	public SaveEquipmentRandom()
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
    	String sStndYear = "";
    	String sMbrCd    = "";
    	String sTms      = "";
    	String sPrtType  = "";
    	String sExclYn   = "";
    	
    	int nSaveCount   = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR ".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD    ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS       ".trim()));
        sPrtType  = Util.nullToStr((String) ctx.get("PRT_TYPE  ".trim()));
        sExclYn   = Util.nullToStr((String) ctx.get("EXCL_YN   ".trim()));
        

        if("001".equals(sPrtType)) { //선수정보저장
        	nSaveCount = saveEquipRandomRacer(sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        }
        if("002".equals(sPrtType) || "003".equals(sPrtType)) { // 모터보트 정보저장
        	nSaveCount = saveEquipRandomMtBt(sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        }
        
        int iSeq      	= getSeq(ctx, sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> 추첨랜덤 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipRandomRacer(String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sPrtType);
        //param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sMbrCd);
        param.setValueParamter(i++, sTms);
        
        return this.getDao("boadao").update("tbec_equip_prt_ic002", param);
    }


    /**
     * <p> 추첨랜덤 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipRandomMtBt(String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sPrtType);
        //param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sMbrCd);
        param.setValueParamter(i++, sTms);
        param.setValueParamter(i++, sExclYn);
        param.setValueParamter(i++, sPrtType);
        
        return this.getDao("boadao").update("tbec_equip_prt_ic003", param);
    }
    
      
    /**
     * <p> 순번가져오기</p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getSeq(PosContext ctx, String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();    
    	int i = 0;
    	param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd);
        param.setWhereClauseParameter(i++, sTms);
    	param.setWhereClauseParameter(i++, sPrtType);
    	param.setWhereClauseParameter(i++, sExclYn);
    	param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd);
        param.setWhereClauseParameter(i++, sTms);
        param.setWhereClauseParameter(i++, sPrtType);
        
        PosRowSet rowset = this.getDao("boadao").find("tbec_equip_prt_fc001", param);
        
        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("PRT_SEQ".trim());
        }
            
        String sResultKey = "dsPrtSeq";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);  
        
        return nCnt.intValue();
    }    
}