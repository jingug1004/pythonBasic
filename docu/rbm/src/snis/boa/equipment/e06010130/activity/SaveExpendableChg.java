/*================================================================================
 * 시스템			: 장비 관리 
 * 소스파일 이름	: snis.boa.equipment.e06010130.activity.SaveExpendableChg.java
 * 파일설명		: 장비 관리 
 * 버전			: 1.0.0
 * 생성일자		: 2011-06-30
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010130.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 해당회차 출주모터에 대한 소모성부품 교체내역을 등록, 수정, 삭제 한다.
* @version 1.0
*/
public class SaveExpendableChg extends SnisActivity
{    
    public SaveExpendableChg()
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
        
        ds    = (PosDataset) ctx.get("dsOutExpendableChg");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteExpendableChg(record);
            }else{
            	nSaveCount = nSaveCount + saveExpendableChg(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveExpendableChg(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeExpendableChg(record);
        return effectedRowCnt;
    }

    

    /**
     * <p>장비 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeExpendableChg(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE1_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE2_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE3_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE4_YN"));
        param.setValueParamter(i++, record.getAttribute("EXPENDABLE5_YN"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbef_expendable_chg_mf001", param);
    }

    /**
     * <p>장비 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteExpendableChg(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("MOT_NO"));
        
        return  this.getDao("boadao").update("tbef_expendable_chg_df001", param);
    }
   
}
