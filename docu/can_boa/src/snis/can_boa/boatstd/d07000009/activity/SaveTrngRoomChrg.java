/*================================================================================
 * 시스템		    : 
 * 소스파일 이름	: snis.can.system.d07000009.activity.SaveTrngRoomChrg.java
 * 파일설명		: 훈련숙박비 입력/수정/삭제
 * 작성자		    : 신재선
 * 버전			: 1.0.0
 * 생성일자		: 2013-01-12
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000009.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 신재선
* @version 1.0
*/

public class SaveTrngRoomChrg extends SnisActivity 
{
	public SaveTrngRoomChrg() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String	sucess 문자열
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
  	* @param   ctx		PosContext	저장소 : 일자별 학과평가
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveState(PosContext ctx)
  	{
  		int nSaveCount   = 0;
  		int nDeleteCount = 0;

  		PosDataset ds;
  		
        String sDsName      = "";
  		int nSize 	= 0;

        sDsName = "dsTrngRoomChrg";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteTrngRoomChrg(record);
	 			}

	             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
	             {      
	            	 nSaveCount += mergeUpdateTrngRoomChrg(record);
	             }
	        }
        }

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
     /**
      * <p> 훈련숙박비 삭제 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int deleteTrngRoomChrg(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
        
     	int dmlcount = this.getDao("candao").delete("d07000009_d01", param);
        
     	return dmlcount;
     }

     /**
      * <p> 훈련숙박비 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int mergeUpdateTrngRoomChrg(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, record.getAttribute("SEQ"));
     	param.setValueParamter(i++, record.getAttribute("APLY_STR_DT"));
     	param.setValueParamter(i++, record.getAttribute("APLY_END_DT"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_DAYS"));
     	param.setValueParamter(i++, record.getAttribute("COMP_EDU_CD"));
     	param.setValueParamter(i++, record.getAttribute("ROOM_CHRG_AMT"));
     	param.setValueParamter(i++, record.getAttribute("RMK"));
     	param.setValueParamter(i++, record.getAttribute("COMMUTE_YN"));
     	param.setValueParamter(i++, record.getAttribute("TRNG_RSN_CD"));
        param.setValueParamter(i++, SESSION_USER_ID);
		
     	int dmlcount = this.getDao("candao").update("d07000009_m01", param);
        
     	return dmlcount;
     }
}
