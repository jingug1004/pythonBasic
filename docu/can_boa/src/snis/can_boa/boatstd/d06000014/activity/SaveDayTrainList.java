/*================================================================================
 * 시스템		    : 
 * 소스파일 이름	: snis.can.system.d07000002.activity.SaveRacerTrngMst.java
 * 파일설명		: 모터 분해 조립 평가 항목 
 * 작성자		    : 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000014.activity;

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
* @auther 유동훈
* @version 1.0
*/

public class SaveDayTrainList extends SnisActivity 
{
	public SaveDayTrainList() { }
	
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsDiaryMstList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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

        sDsName = "dsDiaryMstList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        
	        logger.logDebug("================================================================" + nSize);
	        
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	 			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
	 				nDeleteCount = nDeleteCount + deleteRacerTrngMst(record);
	 			}
	        }
        }

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    

     /**
      * <p> 일자별 학과평가 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int deleteRacerTrngMst(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
        param.setWhereClauseParameter(i++, record.getAttribute("DT"));
        
     	int dmlcount = this.getDao("candao").delete("tbdn_trng_diary_mst_db101", param);
     	this.getDao("candao").delete("tbdn_trng_diary_acdnt_db101", param);
        this.getDao("candao").delete("tbdn_trng_diary_detl_db101", param);
     	this.getDao("candao").delete("tbdn_trng_diary_abs_db101", param);

     	return dmlcount;
     }
}
