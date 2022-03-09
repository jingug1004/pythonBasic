/*================================================================================
 * 시스템			: 사고조사 보고서 관리  
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquip.java
 * 파일설명		: 장비 관리 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 모터 보트등 장비에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveAcdntReport extends SnisActivity
{    
    public SaveAcdntReport()
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
        
        ds    = (PosDataset) ctx.get("dsOutAcdntReport");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteAcdntReport(record);
            }else{
            	nSaveCount = nSaveCount + saveAcdntReport(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 사고조사보고서 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveAcdntReport(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateAcdntReport(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertAcdntReport(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 사고조사보고서  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateAcdntReport(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NOS"));  
        param.setValueParamter(i++, record.getAttribute("RACER_NOS"));   
        param.setValueParamter(i++, record.getAttribute("RACER_NMS")); 
        param.setValueParamter(i++, record.getAttribute("MOT_NOS"));  
        param.setValueParamter(i++, record.getAttribute("BOAT_NOS"));
        param.setValueParamter(i++, record.getAttribute("CNF_DT")); 
        param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRS_STAT")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_SUMMARY")); 
        param.setValueParamter(i++, record.getAttribute("ACDENT_DESC")); 
        param.setValueParamter(i++, record.getAttribute("PRS_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
	        
        return this.getDao("boadao").update("tbef_acdnt_report_uf001", param);
    }

    /**
     * <p>사고조사보고서 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertAcdntReport(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));   
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NOS")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NOS"));  
        param.setValueParamter(i++, record.getAttribute("RACER_NMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NOS")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_NOS")); 
        param.setValueParamter(i++, record.getAttribute("CNF_DT")); 
        param.setValueParamter(i++, record.getAttribute("CIRCUIT_CNT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_LOC_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRS_STAT")); 
        param.setValueParamter(i++, record.getAttribute("ACDNT_SUMMARY")); 
        param.setValueParamter(i++, record.getAttribute("ACDENT_DESC")); 
        param.setValueParamter(i++, record.getAttribute("PRS_DESC")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbef_acdnt_report_if001", param);
    }

    /**
     * <p>사고조사보고서 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteAcdntReport(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_NO")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        
        return  this.getDao("boadao").update("tbef_acdnt_report_df001", param);
    }
   
}
