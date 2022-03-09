/*================================================================================
 * 시스템			: 편성 관리 
 * 소스파일 이름	: snis.can.cyclestd.d02000035.activity.SaveBatch10Record.java
 * 파일설명		: 종합집계(종합)
 * 작성자			: 전홍조
 * 버전			: 1.0.0
 * 생성일자		: 2008-07-13
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
* 자료 종합 생성한다.
* @auther 전홍조
* @version 1.0
*/

public class SaveBatch10Record  extends SnisActivity{
	
	public SaveBatch10Record()
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
         //전회차 집계자료를  최초생성
         ds    = (PosDataset) ctx.get("dsOutBatch");
         nSize = ds.getRecordCount();
         
         for ( int i = 0; i < nSize; i++ ){
         
         	PosRecord record = ds.getRecord(i);
            	nSaveCount = nSaveCount + SaveBatch10(record);
         }
         Util.setSaveCount  (ctx, nSaveCount     );
          
         
     }
     protected int SaveBatch10(PosRecord record)
     {
     	int effectedRowCnt = 0;
     	effectedRowCnt = updateTbdbRecTot(record);
     	
     	
     	return effectedRowCnt;
     }
     /**
      * <p> 종합 tot 테이블 수정 </p>
      * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount int		update 레코드 개수
      * @throws  none
      */
     protected int updateTbdbRecTot(PosRecord record)
     {
         PosParameter param = new PosParameter();
         int i = 0;
         for(int j=0;j<7;j++){
	    	 param.setValueParamter(i++, record.getAttribute("RACE_YY"));
	         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
	         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
	         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
	         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
	         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         }
         for(int j=0;j<4;j++){
        	 param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         }
        /*
         //출주횟수 종합집계
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //착순낙차 종합집계
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //승률 연대율 종합집계
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //총순위 득점
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         //위반행위
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //선두 유도원
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //마지막 기어배수
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
         param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
         //전회차 집계
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         param.setValueParamter(i++, record.getAttribute("RACE_YY"));
         */
         return  this.getDao("candao").update("tbdb_rec_tot_ub009", param); 
         
     }
}
