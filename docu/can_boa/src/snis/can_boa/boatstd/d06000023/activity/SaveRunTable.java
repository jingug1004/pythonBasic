/*================================================================================
 * 시스템			: 학과평가
 * 소스파일 이름	: snis.can.system.d06000015.activity.SaveSubjectJudg.java
 * 파일설명		: 모터 분해 조립 평가 항목 
 * 작성자			: 
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000023.activity;

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
* @auther 최문규
* @version 1.0
*/

public class SaveRunTable extends SnisActivity 
{
	public SaveRunTable() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String	sucess 문자열
     * @throws  none
     */    
	    
    public String runActivity(PosContext ctx)
    {
    	PosDataset ds;

    	int nSize1 = 0;
        
        String[] dsname = new String[5];
        dsname[0] = "dsOutCandRace1";
        dsname[1] = "dsOutCandRace2";
        dsname[2] = "dsOutCandRace3";
        dsname[3] = "dsOutCandRace4";
        dsname[4] = "dsOutCandRace5";
        
        
        PosRecord record;
        
        for (int a=0; a<dsname.length; a++) {
        	if ( ctx.get(dsname[a]) != null ) {
    	        ds = (PosDataset)ctx.get(dsname[a]);
    	        nSize1 = ds.getRecordCount();

    	        for ( int i = 0; i < nSize1; i++ ) 
    	        {
    	            record = ds.getRecord(i);
    	        }
            }// end if  
            
            if(nSize1 > 0){
            	saveStateGeneral(ctx,dsname[a]); 
    		}
        }//end for
        
        return PosBizControlConstants.SUCCESS;
    }
     
      
  	/**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 일자별 학과평가
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateGeneral(PosContext ctx,String dsn ) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;
  		
  		int nSize 	= 0;
  		int nTempCnt    = 0;


		ds = (PosDataset) ctx.get(dsn);
  		nSize = ds.getRecordCount();
  		
  		for ( int i = 0; i < nSize; i++ ) 
  		{ //for2
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateStateGeneral(record)) == 0 ) {
  					nTempCnt = insertStateGeneral(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}
  			
  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteStateGeneral(record);
  			}
  			
  		} //end for2

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    

     /**
      * <p> 일자별 학과평가 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertStateGeneral(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, record.getAttribute("DT"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("CCIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NO"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("MOTOR_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO1"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE1"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME1"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO2"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE2"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME2"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO3"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE3"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME3"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO4"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE4"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME4"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO5"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE5"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME5"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO6"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE6"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME6"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO7"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE7"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME7"));
        param.setValueParamter(i++, record.getAttribute("ARRIV_ORD"));
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("VIOL"));
        param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
        param.setValueParamter(i++, SESSION_USER_ID);
     	int dmlcount = this.getDao("candao").insert("tbdn_cand_race_detl_in001", param);

     	return dmlcount;
     }
     
     /**
      * <p> 일자별 학과평가 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateStateGeneral(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
     	
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        param.setValueParamter(i++, record.getAttribute("DT"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("GBN"));
        param.setValueParamter(i++, record.getAttribute("CCIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
        
        param.setValueParamter(i++, record.getAttribute("TEAM_NO"));
        param.setValueParamter(i++, record.getAttribute("CAND_NO"));
        param.setValueParamter(i++, record.getAttribute("MOTOR_NO"));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO"));

        param.setValueParamter(i++, record.getAttribute("RACE_NO1"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE1"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME1"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO2"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE2"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME2"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO2"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE3"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME3"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO4"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE4"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME4"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO5"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE5"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME5"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO6"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE6"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME6"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO7"));
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE7"));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME7"));
        
        param.setValueParamter(i++, record.getAttribute("ARRIV_ORD"));
        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
        param.setValueParamter(i++, record.getAttribute("VIOL"));
        param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT"));
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT"));
        
        param.setValueParamter(i++, SESSION_USER_ID);
        
        
		i = 0;    
		param.setWhereClauseParameter( i++, record.getAttribute("RACE_SEQ") );
		
		int dmlcount = this.getDao("candao").update("tbdn_cand_race_detl_un001", param);

		return dmlcount;
     }
 
     /**
      * <p> 일자별 학과평가 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteStateGeneral(PosRecord record) 
     {
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, record.getAttribute("RACE_SEQ"));
		        
		int dmlcount = this.getDao("candao").delete("tbdn_cand_race_detl_dn001", param);
		 
		return dmlcount;
     }
      
}
