/*================================================================================
 * 시스템			: 삼진아웃활동결과  관리
 * 소스파일 이름	: snis.rbm.business.rem2010.activity.SaveThrOutReport.java
 * 파일설명		: 삼진아웃활동결과 관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-24
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rem2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveThrOutReport extends SnisActivity {
		public SaveThrOutReport(){
			
			
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
	    	int nDeleteCount = 0; 
	    	String sDsName   = "";
	    	
	    	PosDataset ds;
	        int nSize        = 0;
	        int nTempCnt     = 0;

	        sDsName = "dsThrOutReport";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateThrOutReport(record)) == 0 ) {
			        		nTempCnt = insertThrOutReport(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteThrOutReport(record);	            	
		            }		        
		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> 삼진아웃활동결과  입력 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int insertThrOutReport(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
	        param.setValueParamter(i++, record.getAttribute("ACT_YEAR"));		//활동년도
	        param.setValueParamter(i++, record.getAttribute("ACT_MM"));			//활동월
	        param.setValueParamter(i++, record.getAttribute("CRA_PRSN_NUM"));	//경륜인원
	        param.setValueParamter(i++, record.getAttribute("MRA_PRSN_NUM"));	//경정인원


	        param.setValueParamter(i++, record.getAttribute("ETC_PRSN_NUM"));	//기타인원
	        param.setValueParamter(i++, record.getAttribute("WARN_CNT"));		//구매상한위반_경고회수
	        param.setValueParamter(i++, record.getAttribute("LEAV_CNT"));		//구매상한위반_퇴장회수
	        param.setValueParamter(i++, record.getAttribute("ENT_REFU_CNT"));	//구매상한위반_입장거부회수
	        param.setValueParamter(i++, record.getAttribute("WARN_MINR_CNT"));		//기초질서위반_경고회수
	        param.setValueParamter(i++, record.getAttribute("LEAV_MINR_CNT"));		//기초질서위반_퇴장회수
	        param.setValueParamter(i++, record.getAttribute("ENT_REFU_MINR_CNT"));	//기초질서위반_입장거부회수
	        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고

	        param.setValueParamter(i++, SESSION_USER_ID);

	        
	        int dmlcount = this.getDao("rbmdao").update("rem2010_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> 삼진아웃활동결과  수정 </p>
	     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount int		update 레코드 개수
	     * @throws  none
	     */
	    protected int updateThrOutReport(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ACT_MM"));			//활동월
	        param.setValueParamter(i++, record.getAttribute("CRA_PRSN_NUM"));	//경륜인원
	        param.setValueParamter(i++, record.getAttribute("MRA_PRSN_NUM"));	//경정인원
	        param.setValueParamter(i++, record.getAttribute("ETC_PRSN_NUM"));	//기타인원
	        param.setValueParamter(i++, record.getAttribute("WARN_CNT"));		//구매상한위반_경고회수
	        param.setValueParamter(i++, record.getAttribute("LEAV_CNT"));		//구매상한위반_퇴장회수
	        param.setValueParamter(i++, record.getAttribute("ENT_REFU_CNT"));	//구매상한위반_입장거부회수
	        param.setValueParamter(i++, record.getAttribute("WARN_MINR_CNT"));		//기초질서위반_경고회수
	        param.setValueParamter(i++, record.getAttribute("LEAV_MINR_CNT"));		//기초질서위반_퇴장회수
	        param.setValueParamter(i++, record.getAttribute("ENT_REFU_MINR_CNT"));	//기초질서위반_입장거부회수
	        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
	        param.setValueParamter(i++, SESSION_USER_ID);      					//수정자
	  
	       
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//지점코드
	        param.setWhereClauseParameter(i++, record.getAttribute("ACT_YEAR"));//활동년도
	        param.setWhereClauseParameter(i++, record.getAttribute("ACT_MM"));	//활동월

	        int dmlcount = this.getDao("rbmdao").update("rem2010_u01", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> 삼진아웃활동결과  삭제 </p>
	     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	     * @return  dmlcount	int		update 레코드 개수
	     * @throws  none
	     */
	    protected int deleteThrOutReport(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
	        param.setValueParamter(i++, record.getAttribute("ACT_YEAR"));	//활동년도
	        param.setValueParamter(i++, record.getAttribute("ACT_MM"));		//활동월
	        
	        
	        int dmlcount = this.getDao("rbmdao").update("rem2010_d01", param);

	        return dmlcount;
	    }
}
