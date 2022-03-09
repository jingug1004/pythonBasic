/*================================================================================
 * 시스템			: 편성 관리 
 * 소스파일 이름	: snis.can.cyclestd.d02000035.activity.SaveBatch3Record.java
 * 파일설명		: 종합집계
 * 작성자			: 정의태 
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-19
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can.cyclestd.d02000035.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 승률/연대율 집계자료 생성한다.
* @auther 정의태
* @version 1.0
*/
public class SaveBatch5Record extends SnisActivity
{    
    public SaveBatch5Record()
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
        //총순위득점 집계자료를  최초생성
        ds    = (PosDataset) ctx.get("dsOutBatch");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
        
        	PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveBatch5(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
         
        
    }

    /**
     * <p> 총순위득점 집계자료 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int SaveBatch5(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateTbdbRecTot(record);
    	
    	
    	return effectedRowCnt;
    }
     
    /**
     * <p> 총순위득점 집계테이블 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateTbdbRecTot(PosRecord record)
    {
        
        
         
        PosParameter param = new PosParameter();
        int i = 0;
        
      
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
        param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
        param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        return  this.getDao("candao").update("tbdb_rec_tot_ub004", param); 
        
    }
  
}