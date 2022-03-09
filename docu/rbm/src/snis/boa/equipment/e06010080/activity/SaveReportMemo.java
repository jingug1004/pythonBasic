/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveRunInspPrint.java
 * 파일설명		: 출주검사표 출력 여부 저장
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-01
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010080.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 출주검사표 점검내용 저장
* @version 1.0
*/
public class SaveReportMemo extends SnisActivity
{    
    public SaveReportMemo()
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
    	//사용자 정보 확인
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutReportMemo");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            nSaveCount = nSaveCount + saveReportMemo(record);
        }
        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
  
    /**
     * <p> Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveReportMemo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateReportMemo(record);
    	
        return effectedRowCnt;
    }

    /**
     * <p> 점검내용 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateReportMemo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	param.setValueParamter(i++, record.getAttribute("MEMO1"));  
    	param.setValueParamter(i++, record.getAttribute("MEMO2")); 
        param.setValueParamter(i++, SESSION_USER_ID);
            
        return this.getDao("boadao").update("tbef_run_insp_print_uf001", param);
    }
    
}
