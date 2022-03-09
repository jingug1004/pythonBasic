/*================================================================================
 * 시스템			: 세부교육일정
 * 소스파일 이름	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * 파일설명		: 코드 관리
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class DelDtStrtMesur extends SnisActivity 
{
	public DelDtStrtMesur() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String	sucess 문자열
     * @throws  none
     */    
	
    
    public String runActivity(PosContext ctx)
    {  
    	//사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	saveStateDt_Strt_Mesur(ctx);         	
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 일자별체력측정
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateDt_Strt_Mesur(PosContext ctx) 
  	{
  		int nDeleteCount = 0; 

  		nDeleteCount = nDeleteCount + deleteDt_Strt_Mesur(ctx);


  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
 
  	/**
      * <p> 일자별체력측정 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteDt_Strt_Mesur(PosContext ctx) 
     {
		logger.logInfo("deleteDt_Strt_Mesur start============================");
		PosParameter param = new PosParameter();
		String	racerPerioNo    = (String) ctx.get("racerPerioNo");
		String	dt				= (String) ctx.get("dt");
		System.out.println("deleteDt_Strt_Mesur start============================" + "racerPerioNo:" + racerPerioNo);
		
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(racerPerioNo));
		param.setWhereClauseParameter(i++, Util.trim(dt));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_dt_strt_mesur_dn002", param);
		 
		logger.logInfo("deleteDt_Strt_Mesur end============================");
		return dmlcount;
     }
}
