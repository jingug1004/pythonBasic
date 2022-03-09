/*================================================================================
 * 시스템			: 임대 관리
 * 소스파일 이름	: snis.rbm.business.rbr3010.activity.SaveLeaseMana.java
 * 파일설명		: 임대 관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveLeaseMana extends SnisActivity {
	
	public SaveLeaseMana(){
		
		
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

        sDsName = "dsLeaseMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateLeaseMana(record)) == 0 ) {
		        		nTempCnt = insertLeaseMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteLeaseMana(record);	
		        	
		        	deleteLeaseBondAll(record);
		        	
	            }		        
	        }	        
        }


        //채권         
        sDsName = "dsLeaseBond";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateLeaseBond(record)) == 0 ) {
		        		nTempCnt = insertLeaseBond(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteLeaseBond(record);	            	
	            }		        
	        }	        
        }

        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 임대  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertLeaseMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));	//계약시작일자
        param.setValueParamter(i++, record.getAttribute("CNTR_END_DT"));	//계약종료일자
        param.setValueParamter(i++, record.getAttribute("POST_CD"));		//우편번호
        param.setValueParamter(i++, record.getAttribute("ADDR"));			//주소
        
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));		//상세주소
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));	//공용면적
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));	//공용면적(평)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));	//전용면적
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));	//전용면적(평)

        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));	//설치허가일자
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));			//개장일자
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));		//입장정원
        param.setValueParamter(i++, record.getAttribute("LEASE_RATE"));			//전월세비율
        param.setValueParamter(i++, record.getAttribute("SQMT_AMT"));			//㎡당금액

        param.setValueParamter(i++, record.getAttribute("CNTR_TOT_AMT"));		//계약총액
        param.setValueParamter(i++, record.getAttribute("LEASE"));				//월세
        param.setValueParamter(i++, record.getAttribute("DEPOSIT"));			//보증금
        param.setValueParamter(i++, record.getAttribute("INTEREST_RATE"));		//이자율
        param.setValueParamter(i++, record.getAttribute("LEASE_COMP"));			//임대업체

        param.setValueParamter(i++, record.getAttribute("LEASE_FIX_AMT"));		//임료결정금액
        param.setValueParamter(i++, record.getAttribute("CORP_AMT"));			//공단금액
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_AMT"));		//건물주금액
        param.setValueParamter(i++, record.getAttribute("CHAG_RATE"));			//부담비율
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_RANK"));		//채권확보순위
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_OBJ"));		//채권확보물권
        
        
        
        param.setValueParamter(i++, record.getAttribute("RMK"));				//비고
        param.setValueParamter(i++, record.getAttribute("BOND_OBJ"));			//설정목적물
        param.setValueParamter(i++, record.getAttribute("CORP_ESTMR"));			//공단 평가업체 
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_ESTMR"));		//건물주 평가업체

        
        param.setValueParamter(i++, record.getAttribute("M2_MANA_COST"));		//제곱미터당 관리비
        param.setValueParamter(i++, record.getAttribute("MM_AVG_MANA_COST"));	//월평균관리비
        param.setValueParamter(i++, record.getAttribute("WTR_HEAT_COST"));		//수도광열비
        param.setValueParamter(i++, record.getAttribute("YEAR_AVG_MANA_COST"));	//연간관리비
        param.setValueParamter(i++, record.getAttribute("MANA_CORP"));			//관리업체명
        
      
        param.setValueParamter(i++, SESSION_USER_ID);
        
        

        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 임대  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateLeaseMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("CNTR_END_DT"));	//계약종료일자
        param.setValueParamter(i++, record.getAttribute("POST_CD"));		//우편번호
        param.setValueParamter(i++, record.getAttribute("ADDR"));			//주소
        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));		//상세주소
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));	//공용면적(㎡)
        
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));	//공용면적(평)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));	//전용면적
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));	//전용면적(평)
        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));//설치허가일자
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));		//개장일자
        
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));	//입장정원
        param.setValueParamter(i++, record.getAttribute("LEASE_RATE"));		//전월세비율
        param.setValueParamter(i++, record.getAttribute("SQMT_AMT"));		//㎡당금액
        param.setValueParamter(i++, record.getAttribute("CNTR_TOT_AMT"));	//계약총액
        param.setValueParamter(i++, record.getAttribute("LEASE"));			//월세
        
        param.setValueParamter(i++, record.getAttribute("DEPOSIT"));		//보증금
        param.setValueParamter(i++, record.getAttribute("INTEREST_RATE"));	//이자율
        param.setValueParamter(i++, record.getAttribute("LEASE_COMP"));		//임대업체
        
        param.setValueParamter(i++, record.getAttribute("LEASE_FIX_AMT"));	//임료결정금액
        param.setValueParamter(i++, record.getAttribute("CORP_AMT"));		//공단금액
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_AMT"));	//건물주금액
        param.setValueParamter(i++, record.getAttribute("CHAG_RATE"));		//부담비율
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_RANK"));	//채권확보순위
        param.setValueParamter(i++, record.getAttribute("BOND_SECU_OBJ"));	//채권확보물권
        
        
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고        
        param.setValueParamter(i++, record.getAttribute("BOND_OBJ"));			//설정목적물
        param.setValueParamter(i++, record.getAttribute("CORP_ESTMR"));			//공단 평가업체 
        param.setValueParamter(i++, record.getAttribute("LANDLOAD_ESTMR"));		//건물주 평가업체

        
        param.setValueParamter(i++, record.getAttribute("M2_MANA_COST"));		//제곱미터당 관리비
        param.setValueParamter(i++, record.getAttribute("MM_AVG_MANA_COST"));	//월평균관리비
        param.setValueParamter(i++, record.getAttribute("WTR_HEAT_COST"));		//수도광열비
        param.setValueParamter(i++, record.getAttribute("YEAR_AVG_MANA_COST"));	//연간관리비
        param.setValueParamter(i++, record.getAttribute("MANA_CORP"));			//관리업체명        
        
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
        
        
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));	//계약시작일자 

        int dmlcount = this.getDao("rbmdao").update("rbr3010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 임대  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteLeaseMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));//계약시작일자
    
        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d01", param);

        return dmlcount;
    }
    
    
    
    /**
     * <p> 임대 채권  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertLeaseBond(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));		//계약시작일자
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));		//계약시작일자
        
        param.setValueParamter(i++, record.getAttribute("BANK_NM"));			//은행명
        
        param.setValueParamter(i++, record.getAttribute("SECU_AMT"));			//담보금액
        param.setValueParamter(i++, record.getAttribute("ACPT_DT"));			//접수일자
      
        
      
        param.setValueParamter(i++, SESSION_USER_ID);
        

        int dmlcount = this.getDao("rbmdao").update("rbr3010_i02", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 임대 채권  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateLeaseBond(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BANK_NM"));			//은행명
        
        param.setValueParamter(i++, record.getAttribute("SECU_AMT"));			//담보금액
        param.setValueParamter(i++, record.getAttribute("ACPT_DT"));			//접수일자
        
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
        
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//지점
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));	//계약일자 
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));	//일련번호

        int dmlcount = this.getDao("rbmdao").update("rbr3010_u02", param);
        return dmlcount;
    }
    
    /**
     * <p> 임대 채권 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteLeaseBond(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_STR_DT"));//계약시작일자
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ_NO"));//계약시작일자
    
        
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> 임대 채권 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteLeaseBondAll(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("CNTR_STR_DT"));//계약시작일자
            
        int dmlcount = this.getDao("rbmdao").update("rbr3010_d03", param);

        return dmlcount;
    }

}
