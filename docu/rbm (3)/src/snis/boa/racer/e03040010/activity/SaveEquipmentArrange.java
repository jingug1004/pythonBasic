/*================================================================================
 * 시스템		: 선수관리
 * 소스파일 이름: snis.boa.racer.e03040010.activity.SaveEquipmentArrange.java
 * 파일설명		: 모터/보트배정 등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-23
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

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
* @auther 김경화
* @version 1.0
*/
public class SaveEquipmentArrange extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveEquipmentArrange()
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

        ds    = (PosDataset) ctx.get("dsOutRacerArrange");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerArrange(record);
                  }
        }


        ds    = (PosDataset) ctx.get("dsOutRacerArrange_0");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
                  {
            		nSaveCount = nSaveCount + saveRacerArrange(record);
                  }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 모터/보트배정  Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerArrange(PosRecord record)
    {
    	updateEquipPrt(record);
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerArrange(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>모터/보트배정  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRacerArrange(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO")));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO")));
        
        return this.getDao("boadao").update("tbeb_arrange_uc001", param);        
    }

    
    /**
     * <p>모터/보트배정  입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected void updateEquipPrt(PosRecord record) 
    {
    	if ( record.getAttribute("ORG_RACER_NO") == null ) return;
    	if ( record.getAttribute("RACER_NO"    ) == null ) return;
    	if ( record.getAttribute("ORG_RACER_NO").equals("") ) return;
    	if ( record.getAttribute("RACER_NO"    ).equals("") ) return;
    	
    	if ( record.getAttribute("ORG_RACER_NO").equals(record.getAttribute("RACER_NO"    ))) return;

    	
    	PosParameter param = new PosParameter();
        int i = 0;
       
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"   ));
        param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"      ));
        param.setWhereClauseParameter(i++, record.getAttribute("TMS"         ));
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_RACER_NO"));
        
        this.getDao("boadao").update("tbec_equip_prt_uc001", param);        
    }

}


