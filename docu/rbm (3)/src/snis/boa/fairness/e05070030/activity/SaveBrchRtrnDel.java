/*================================================================================
 * 시스템			: 공정 관리
 * 소스파일 이름	: snis.boa.fairness.e05070030.activity.SaveBrchRtrnDel.java
 * 파일설명		: 지점고액환급정보 관리
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2009-12-2
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05070030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

public class SaveBrchRtrnDel extends SnisActivity
{    
    public SaveBrchRtrnDel()
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
		int nSize = 0;
		String sDsName = "dsOutBrchRtrn";

		if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
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
    * @return  SUCCESS	String		sucess 문자열
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
		int nDeleteCount = 0;
		int nSize = 0;

		String sDsName = "dsOutBrchRtrn";
		PosDataset ds;

        if (ctx.get(sDsName) != null) {
			ds = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();

			for (int i = 0; i < nSize; i++) {
				PosRecord record = ds.getRecord(i);
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + deleteBrchRtrn(record);
				}
			}
		}

        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> 지점고액환급정보삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
	protected int deleteBrchRtrn(PosRecord record) {
		PosParameter param = new PosParameter();

		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("SR_NO".trim()));

		int dmlcount = this.getDao("boadao").delete("tbee_brch_rtrn_de001", param);

		return dmlcount;
	}

}
