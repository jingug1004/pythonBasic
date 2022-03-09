/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02030035.activity.SaveArrangeNoti.java
 * 파일설명		: 선수주선통보등록
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02030035.activity;

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
public class SaveArrangeNotiList extends SnisActivity
{
    public SaveArrangeNotiList()
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
	        
        sDsName = "dsOutArrangeNotiList";
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

    	PosDataset ds;
        int nSize        = 0;
        String sDsName   = "";

        sDsName = "dsOutArrangeNotiList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
    	    // 선수주선통보목록  등록
    	    for ( int i = 0; i < nSize; i++ ) 
    	    {
    	    	PosRecord record = ds.getRecord(i);
    	        if ( (record.getAttribute("STND_YEAR").equals(record.getAttribute("MAPPING_YEAR")))
    	          && (record.getAttribute("TMS").equals(record.getAttribute("MAPPING_TMS")))
    	          && (record.getAttribute("SEQ").equals(record.getAttribute("MAPPING_SEQ"))) ) {    
		            nTempCnt = updateArrangeNotiList(record, ctx);
		            nSaveCount = nSaveCount + nTempCnt;
    	        }
    	    }
        }

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 선수주선통보목록 담당자입력사항 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateArrangeNotiList(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COMMENTARY") );

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_noti_ub002", param);
        return dmlcount;
    }
    
}
