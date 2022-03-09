/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03070010.activity.SavePointBasisInfo.java
 * 파일설명		: 상벌점 기준정보등록
 * 작성자		: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-04
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03070010.activity;

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
* 매핑하여 상벌점 기준정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화
* @version 1.0
*/
public class SavePointBasisInfo extends SnisActivity
{    
    public SavePointBasisInfo()
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
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPointBasis");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deletePointBasis(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + savePointBasis(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 상벌점 기준 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int savePointBasis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =insertPointBasis(record);
        
        return effectedRowCnt;
    }

    /**
     * <p>상벌점 기준 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertPointBasis(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String sSeq      	= getPointBasisMax(record);
        String tmpSeq 		= "0";        
        if (record.getAttribute("RWNPT_CD".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("RWNPT_CD".trim()));

        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_GBN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TITL")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);       
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, sSeq);
        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_GBN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TITL")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_rwnpt_point_bas_ic001", param);
    }

    /**
     * <p>상벌점 기준 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deletePointBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("RWNPT_CD"));
        
        return  this.getDao("boadao").update("tbec_rwnpt_point_bas_dc001", param);
    }
    
    /**
     * <p> Max 값 가져오기 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		Max
     * @throws  none
     */
    protected String getPointBasisMax(PosRecord record)
    {
        PosParameter param = new PosParameter();
        
        param.setWhereClauseParameter(0, Util.trim(record.getAttribute("STND_YEAR".trim())));
        PosRowSet rowset = this.getDao("boadao").find("tbec_rwnpt_point_bas_fc002", param);

        String nStr = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nStr = (String)row.getAttribute("strMax".trim());
        }
            
        return nStr;
    }
}