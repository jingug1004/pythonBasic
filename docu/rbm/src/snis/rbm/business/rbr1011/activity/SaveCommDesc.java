/*================================================================================
 * 시스템			: 일반사항  관리
 * 소스파일 이름	: snis.rbm.business.rbr1011.activity.SaveCommDesc.java
 * 파일설명		: 일반사항과 층관리 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-16
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCommDesc extends SnisActivity {
	
	public SaveCommDesc(){}

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

        sDsName = "dsCommDesc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		        	
		        	nTempCnt = saveCommDesc(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }

        sDsName = "dsFloorMana";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            Double dFloor = (Double)record.getAttribute("FLOOR");
	            int nFloor = dFloor.intValue();
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//기본키 중복체크
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectFloorCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "[ " + nFloor + " ](은)는 같은 층이 존재합니다.");
			            		
			            		return;
			            	}
		        		}
		        	}
		        	
		        	nTempCnt = saveFloorMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//장비관리에서 층이 사용 중일 경우
	            	if( selectEquipCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "층 [ " + nFloor + " ](은)는 장비관리에서 사용 중이므로 삭제하실 수 없습니다.");
		            		
		            		return;
		            	}
	            	}
	            	//시설현황에서 층이 사용 중일 경우
	            	if( selectFacCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "층 [ " + nFloor + " ](은)는 시설현황에서 사용 중이므로 삭제하실 수 없습니다.");
		            		
		            		return;
		            	}
	            	}
	            	
		        	nDeleteCount = nDeleteCount + deleteFloorMana(record);	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 지점관리_일반사항 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveCommDesc(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//년도
        param.setValueParamter(i++, record.getAttribute("OP_GOAL"));			//운영목표	
        param.setValueParamter(i++, record.getAttribute("POST_CD"));			//우편번호
        param.setValueParamter(i++, record.getAttribute("ADDR"));				//주소

        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));			//상세주소
        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));	//설치허가일자                
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));			//개장일자
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));		//입장정원
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_CRA"));		//매출목표경륜
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_MRA"));		//매출목표경정
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_CRA_GREEN"));		//매출목표경륜(그린카드)
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_MRA_GREEN"));		//매출목표경정(그린카드)
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_NORMAL"));	//일반실 좌석수
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_FIXED"));		//지정좌석실 좌석수
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_GREEN"));		//그린카드존 좌석수
        
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(수정자)
      
        int dmlcount = this.getDao("rbmdao").update("rbr1011_m01", param);
        
        return dmlcount;
    }
     
    /**
     * <p> 지점관리_층관리 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveFloorMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        //pk set 
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//년도
        param.setValueParamter(i++, record.getAttribute("FLOOR"));				//층
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));		//공용면적(㎡)	
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));		//공용면적(평)
        
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));		//전용면적(㎡)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));		//전용면적(평)
        param.setValueParamter(i++, record.getAttribute("RELEA_COT_MANNED"));	//발매창구(유인)                
        param.setValueParamter(i++, record.getAttribute("RELEA_COT_MANLESS"));	//발매창구(무인)
        param.setValueParamter(i++, record.getAttribute("RELEA_MACH_MANNED"));	//발매기(유인)

        param.setValueParamter(i++, record.getAttribute("RELEA_MACH_MANLESS"));	//발매기(무인)
        param.setValueParamter(i++, record.getAttribute("GRN_CRD_LOC"));		//그린카드존
        param.setValueParamter(i++, record.getAttribute("RMK"));				//비고        
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID(수정자)
        
        int dmlcount = this.getDao("rbmdao").update("rbr1011_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 지점관리_층관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteFloorMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//년도
        param.setValueParamter(i++, record.getAttribute("FLOOR"));			//층
        
        int dmlcount = this.getDao("rbmdao").update("rbr1011_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 층관리 기본키 개수 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectFloorCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));    //지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));  //년도
        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //층
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 층관리 장비관리에서 층을 사용하는지 여부 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectEquipCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //층
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 층관리 장비관리에서 층을 사용하는지 여부 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectFacCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //층
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s05", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
}
