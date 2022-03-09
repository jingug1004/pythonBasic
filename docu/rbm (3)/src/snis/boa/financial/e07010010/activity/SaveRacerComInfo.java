/*================================================================================
 * 시스템			: 상금 관리 
 * 소스파일 이름	: snis.boa.RacerComInfoment.e06010010.activity.SaveRacerComInfo.java
 * 파일설명		: 선수사업자정보/계좌 정보 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07010010.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 선수사업자정보/계좌 정보 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveRacerComInfo extends SnisActivity
{    
    public SaveRacerComInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerComInfo");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteRacerComInfo(record);
            }else{
            	nSaveCount = nSaveCount + saveRacerComInfo(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 선수사업자정보/계좌 정보Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerComInfo(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRacerComInfo(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRacerComInfo(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 선수사업자정보/계좌 정보  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateRacerComInfo(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_NM"));
        param.setValueParamter(i++, record.getAttribute("JOB_TPE")); 
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS")); 
        param.setValueParamter(i++, record.getAttribute("BK_CD")); 
        param.setValueParamter(i++, record.getAttribute("BK_ACCUNT"));
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));
        param.setValueParamter(i++, record.getAttribute("MBS_FEE_YN"));
        param.setValueParamter(i++, record.getAttribute("FNC_CHK_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        return this.getDao("boadao").update("tbeg_racer_com_info_uf001", param);
    }

    /**
     * <p>선수사업자정보/계좌 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRacerComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("COM_NM"));
        param.setValueParamter(i++, record.getAttribute("JOB_TPE")); 
        param.setValueParamter(i++, record.getAttribute("JOB_KINDS")); 
        param.setValueParamter(i++, record.getAttribute("BK_CD")); 
        param.setValueParamter(i++, record.getAttribute("BK_ACCUNT"));
        param.setValueParamter(i++, record.getAttribute("COM_LOC_ADDR"));
        param.setValueParamter(i++, record.getAttribute("MBS_FEE_YN"));
        param.setValueParamter(i++, record.getAttribute("FNC_CHK_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_racer_com_info_if001", param);
    }

    /**
     * <p>선수사업자정보/계좌 정보 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteRacerComInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        return  this.getDao("boadao").update("tbeg_racer_com_info_df001", param);
    }
   
}
