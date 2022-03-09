/*================================================================================
 * 시스템			: 입장인원등록
 * 소스파일 이름	: snis.rbm.business.rbo1010.activity.saveProfitMana
 * 파일설명		: 입장인원등록
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbo1010.activity;  

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveProfitMana extends SnisActivity {
	
	public SaveProfitMana(){}

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
        
        sDsName = "dsProfitMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//기본키 중복체크
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectProfitCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		
			            		String sRaceDt = (String)record.getAttribute("RACE_DT");
			            		String sGbn    = (String)record.getAttribute("GBN");
			            		
			            		Util.setSvcMsg(ctx, "[ " + sGbn                    + " ]" +
			            							"[ " + sRaceDt.substring(0, 4) + "-"  + 
					     				                   sRaceDt.substring(4, 6) + "-"  +
					     				                   sRaceDt.substring(6, 8) + " ](은)는 중복값이 존재합니다.");
			            		return;
			            	}
		        		}
		        	}

		        	if( selectTmsChk(record) == 0 ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sRaceDt = (String)record.getAttribute("RACE_DT");
		            		String sGbn    = (String)record.getAttribute("GBN");
		            		
		            		Util.setSvcMsg(ctx, "[ " + sGbn                    + " ]" +
		            							"[ " + sRaceDt.substring(0, 4) + "-"  + 
				     				                   sRaceDt.substring(4, 6) + "-"  +
				     				                   sRaceDt.substring(6, 8) + " ]에는 [ 회차 ]가 없습니다.");
		            		return;
		            	}
		        	} else {
		        		Double dTms = new Double(selectTms(record));
		        		record.setAttribute("TMS", dTms);
		        	}
		        	
		        	nTempCnt = saveProfitMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteProfitMana(record);	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 입장인원 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveProfitMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        //pk set 
        param.setValueParamter(i++, record.getAttribute("GBN"));				//구분
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));			//경주일자
        param.setValueParamter(i++, record.getAttribute("TMS"));				//회차
        param.setValueParamter(i++, record.getAttribute("PAY_PRSN_NUM"));		//유료인원수
        param.setValueParamter(i++, record.getAttribute("FREE_PRSN_NUM"));		//무료인원수
        
        param.setValueParamter(i++, record.getAttribute("ISSU_AMT"));			//발권금액
        param.setValueParamter(i++, record.getAttribute("SPC_TAX"));			//특소세         
        param.setValueParamter(i++, record.getAttribute("EDU_TAX"));			//교육세
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT"));			//공급가액
        param.setValueParamter(i++, record.getAttribute("ADD_TAX"));			//부가세
        
        param.setValueParamter(i++, record.getAttribute("RMK"));				//비고
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(수정자)

        int dmlcount = this.getDao("rbmdao").update("rbo1010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 입장인원 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteProfitMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GBN"));			//구분
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));        //경주일자
        
        int dmlcount = this.getDao("rbmdao").update("rbo1010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 수익관리 기본키 개수 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectProfitCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("GBN"));      //구분
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //경주일자
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s02", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 수익관리 회차 존재여부 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectTmsChk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //경주일자
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));        
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 수익관리 회차 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectTms(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //경주일자
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("TMS"));        
        
        return Integer.valueOf(rtnCnt).intValue();
    }
}
