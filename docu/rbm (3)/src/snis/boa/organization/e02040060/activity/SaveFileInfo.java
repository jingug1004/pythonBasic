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
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 출주표의 메일 발송여부를 저장하는 클래스이다.
* @auther 신재선
* @version 1.0
*/
public class SaveFileInfo extends SnisActivity
{    
    public SaveFileInfo()
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

    	// 출주표 파일정보 저장
    	nSaveCount   = saveFileInfo(ctx);

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 출주표 홈페이지 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveFileInfo(PosContext ctx) 
    {

		int nSaveCount = 0;
    	PosDataset ds;
        String sDsName      = "";
        int nSize        = 0;
        int nTempCnt     = 0;
		

        // 경주회차 저장
        sDsName = "dsOutFile";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // 저장
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
	            {
	            	nTempCnt = mergeFileInfo(ctx, record, i+1);
                	nSaveCount = nSaveCount + nTempCnt;
	            }
	        }
        }
        
		Util.setSaveCount(ctx, nSaveCount);

		return nSaveCount;
    	
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
	protected int mergeFileInfo(PosContext ctx, PosRecord record, int iSeq) {
		PosParameter param = new PosParameter();
		int i = 0;
		
		param.setValueParamter(i++, ctx.get("STND_YEAR"));
		param.setValueParamter(i++, ctx.get("MBR_CD"));
		param.setValueParamter(i++, ctx.get("TMS"));
		param.setValueParamter(i++, ctx.get("DAY_ORD"));
		param.setValueParamter(i++, ctx.get("CFM_YN"));		
		param.setValueParamter(i++, iSeq);
		
		param.setValueParamter(i++, Util.trim(record.getAttribute("FILE_ID".trim())));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FILE_NAME".trim())));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FILE_SIZE".trim())));
		param.setValueParamter(i++, Util.trim(record.getAttribute("FILE_URL".trim())));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		return this.getDao("boadao").insert("e02040060_u01", param);
	}
	
}
