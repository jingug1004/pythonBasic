/*================================================================================
 * 시스템			: 장비 관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquip.java
 * 파일설명		: 장비 관리 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06050010.activity;


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
public class SaveSuppCode extends SnisActivity
{    
    public SaveSuppCode()
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
        
        ds    = (PosDataset) ctx.get("dsSuppCd");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteSuppCode(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	nSaveCount = nSaveCount + updateSuppCode(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            	nSaveCount = nSaveCount + insertSuppCode(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }


    /**
     * <p>장비 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("SPEC"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("DAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("e06050010_i01", param);
    }

    /**
     * <p>장비 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int updateSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("SPEC"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("SAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SUPP_CD"));
        return this.getDao("boadao").update("e06050010_u01", param);
    }

    /**
     * <p>장비 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        
        return  this.getDao("boadao").update("e06050010_d01", param);
    }
   
}
