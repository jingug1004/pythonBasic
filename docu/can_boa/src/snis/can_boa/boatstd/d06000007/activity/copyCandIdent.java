/*================================================================================
 * 시스템			: 후보생 신상 관리
 * 소스파일 이름	: snis.can.system.d06000007.activity.copyCandIdent.java
 * 파일설명		: 후보생 신상 관리
 * 작성자			: 노인수
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;


/**
* 후보생선발 합격자를 후보생신상 정보로 복사한다.
* @auther 정의태
* @version 1.0
*/ 
public class copyCandIdent  extends SnisActivity
{    
	
    public copyCandIdent ()
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

    	PosDataset ds;
        int nSize        = 0;
        
        //경주테이블 저장	
        ds    = (PosDataset) ctx.get("dsCopyPerioNo");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
              
           	nSaveCount = nSaveCount + CopyCandIdent(record);
        }
        
        
    }
    /**
     * <p> 후보생신상테이블 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int CopyCandIdent(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = insertCandIdent(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> 후보생신상테이블  Insert </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    
     protected int insertCandIdent(PosRecord record)
    {
        
    	PosParameter param = new PosParameter();
         
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	
     	int inscount  = this.getDao("candao").insert("d06000007_copyCandIdent", param);
        return inscount;
    	 
    	 
    }
     
}