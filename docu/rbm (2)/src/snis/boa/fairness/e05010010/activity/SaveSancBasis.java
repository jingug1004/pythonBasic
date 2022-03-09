/*================================================================================
 * 시스템			: 공정 관리 
 * 소스파일 이름	: snis.boa.fairness.e05010010.activity.SaveSancBasis.java
 * 파일설명		: 양정기준등록
 * 작성자			: 한영택 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-04
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.fairness.e05010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 양정기준코드 정보 대한 등록, 수정, 삭제 한다.
* @auther 한영택 
* @version 1.0
*/
public class SaveSancBasis extends SnisActivity
{    
    public SaveSancBasis()
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
        
        ds    = (PosDataset) ctx.get("dsOutSancBasis");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            switch (record.getType())
            {
	            case com.posdata.glue.miplatform.vo.PosRecord.DELETE:
	            	nDeleteCount = nDeleteCount + deleteSancBasis(record);
	            	break;
	            case com.posdata.glue.miplatform.vo.PosRecord.INSERT:
	            	nSaveCount = nSaveCount + insertSancBasis(record);
	            	break;	
	            case com.posdata.glue.miplatform.vo.PosRecord.UPDATE:
	            	nSaveCount = nSaveCount + updateSancBasis(record);
	            	break;	
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 양정기준코드 정보Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveSancBasis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateSancBasis(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertSancBasis(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 양정기준코드 정보  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSancBasis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("TERM")); 
        param.setValueParamter(i++, record.getAttribute("UNIT")); 
        param.setValueParamter(i++, record.getAttribute("STR_DT")); 
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_ITEM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD"));
        return this.getDao("boadao").update("tbee_sanc_basis_ue001", param);
    }

    /**
     * <p>양정기준코드 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSancBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        param.setValueParamter(i++, record.getAttribute("SMRY"));
        param.setValueParamter(i++, record.getAttribute("TERM")); 
        param.setValueParamter(i++, record.getAttribute("UNIT")); 
        param.setValueParamter(i++, record.getAttribute("STR_DT")); 
        param.setValueParamter(i++, record.getAttribute("END_DT"));
        param.setValueParamter(i++, record.getAttribute("SANC_ITEM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
       	return this.getDao("boadao").update("tbee_sanc_basis_ie001", param);
    }

    /**
     * <p>양정기준코드 정보 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteSancBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SANC_BASIS_CD")); 
        return  this.getDao("boadao").update("tbee_sanc_basis_de001", param);
    }
}
