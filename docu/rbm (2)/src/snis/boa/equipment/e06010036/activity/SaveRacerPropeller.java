/*================================================================================
 * 시스템		: 장비관리
 * 소스파일 이름: snis.boa.equipment.e06010035.activity.SavePropeller.java
 * 파일설명		: 프로펠러 사용 등록
 * 작성자			: 정민화
 * 버전			: 1.0.0
 * 생성일자		: 2011-03-16
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010036.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 부상선수정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @version 1.0
*/
public class SaveRacerPropeller extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerPropeller()
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
    	int nSaveCount   	= 0; 
    	int nDeleteCount 	= 0; 

    	PosDataset ds;
        int nSize        	= 0;
        PosRecord record  	= null;
        
        ds    = (PosDataset) ctx.get("dsOutRacerPropeller");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerPropeller(record);
                  }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> Propeller Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerPropeller(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  mergeRacerPropeller(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>Propeller  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeRacerPropeller(PosRecord record) 
    {
    	//if ( record.getAttribute("RACER_NO") == null || record.getAttribute("RACER_NO").equals("")) return 0;
    	
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO   ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER1 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER2 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER3 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER4 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("PROPELLER5 ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK        ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").insert("tbef_propeller_mf002", param);
        return dmlcount;
    }

}


