/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040060.activity.RegistHomePage.java
 * 파일설명		: 출주표홈페이지등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import java.util.HashMap;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 출주표의 메일 발송여부를 저장하는 클래스이다.
* @auther 신재선
* @version 1.0
*/
public class SaveMailHist extends SnisActivity
{    
    public SaveMailHist()
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 

    	// 출주표 발송여부 저장
    	nSaveCount   = saveMail(ctx);

    	if ( nSaveCount == 0 ) {
    		Util.setSvcMsg(ctx, "편성작업이 아직 이루어 지지 않았습니다.");
    	}
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 출주표 홈페이지 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveMail(PosContext ctx) 
    {

		int nInsertCount = 0;
		
		PosParameter param = new PosParameter();
		int i = 0;

		nInsertCount = insertMail(ctx);
		
		Util.setSaveCount(ctx, nInsertCount);

		return nInsertCount;
    	
    }
    

	/**
	 * <p>
	 * 등록
	 * </p>
	 * 
	 * @param record
	 *            PosRecord 데이타셋에 대한 하나의 레코드
	 * @return dmlcount int update 레코드 개수
	 * @throws none
	 */
	protected int insertMail(PosContext ctx) {
		PosParameter param = new PosParameter();
		int i = 0;
		
		param.setValueParamter(i++, ctx.get("STND_YEAR"));
		param.setValueParamter(i++, ctx.get("MBR_CD"));
		param.setValueParamter(i++, ctx.get("TMS"));
		param.setValueParamter(i++, ctx.get("DAY_ORD"));
		param.setValueParamter(i++, ctx.get("CFM_YN"));
		
		param.setValueParamter(i++, ctx.get("STND_YEAR"));
		param.setValueParamter(i++, ctx.get("MBR_CD"));
		param.setValueParamter(i++, ctx.get("TMS"));
		param.setValueParamter(i++, ctx.get("DAY_ORD"));
		param.setValueParamter(i++, ctx.get("CFM_YN"));
		
		param.setValueParamter(i++, "1");
		param.setValueParamter(i++, "");
		param.setValueParamter(i++, SESSION_USER_ID);
		
		return this.getDao("boadao").insert("e02040060_i01", param);
	}
	
}
