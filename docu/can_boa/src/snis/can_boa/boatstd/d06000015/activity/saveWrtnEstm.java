/*================================================================================
 * 시스템			: 학과평가
 * 소스파일 이름	: snis.can.system.d06000015.activity.SaveSubjectJudg.java
 * 파일설명		: 코드 관리
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000015.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 정리
* @version 1.0
*/

public class saveWrtnEstm extends SnisActivity 
{
	public saveWrtnEstm() { }
	
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
        
    	PosDataset ds;
        int nSize = 0;
        String sDsName = "";
                
        //학과평가 일자
        sDsName = "dsDateList";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
	        }
        }
        //학과일자별 세부학과 평가
        sDsName = "dsWrtExam_Detail";
        if ( ctx.get(sDsName) != null ) {
        	ds = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logDebug(record);
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

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsDateList");
  		nSize = ds.getRecordCount();

  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
  			{
  				nDeleteCount = nDeleteCount + deleteWrtnEstm(record);
  			}
  		}
  		
  		ds = (PosDataset) ctx.get("dsWrtExam_Detail");
  		nSize = ds.getRecordCount();
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);
            if ( record.getType() != com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nTempCnt = mergeWrtnEstm(record);
                nSaveCount = nSaveCount + nTempCnt;
            }
  		}
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
  		
  	}


    /**
     * <p> 일자별 학과평가 삭제</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
   protected int deleteWrtnEstm(PosRecord record) 
   {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
    	param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
    	param.setWhereClauseParameter(i++, record.getAttribute("ROUND".trim()));
		param.setWhereClauseParameter(i++, record.getAttribute("DT".trim()));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_wrt_exam_detail_de001", param);
		 
		return dmlcount;
   }
   
   
   /**
    * <p> 일자별 학과평가 입력 </p>
    * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
    * @return  dmlcount	int		insert 레코드 개수
    * @throws  none
    */
	protected int mergeWrtnEstm(PosRecord record)
	{
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdn_wrtn_estm_detail_mer001", param);
	
	 	return dmlcount;
	}   


   
   
   
     /**
      * <p> 일자별 학과평가 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
	protected int insertWrtnEstm(PosRecord record)
	{
	 	PosParameter param = new PosParameter();       					
	 	int i = 0;
	
	 	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").insert("tbdn_wrtn_estm_detail_in001", param);
	
	 	return dmlcount;
	}
     
	/**
	 * <p> 일자별 학과평가 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */

	
	/**
      * <p> 일자별 학과평가 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
    protected int updateWrtnEstm(PosRecord record) 
    {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		param.setValueParamter(i++, record.getAttribute("SCR01".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR03".trim()));
		param.setValueParamter(i++, record.getAttribute("SCR04".trim()));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("CAND_NO".trim()));
		param.setValueParamter(i++, record.getAttribute("ROUND".trim()));
		param.setValueParamter(i++, record.getAttribute("DT".trim()));

		int dmlcount = this.getDao("candao").update("tbdn_wrtn_estm_detail_up001", param);

		return dmlcount;
    }
}
