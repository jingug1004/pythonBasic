/*
 * ================================================================================
 * 시스템 : 구매수량산출 관리 소스파일 
 * 이름 : snis.rbm.business.rbo3040.activity..java 
 * 파일설명 : 구매수량산출관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 
 * 생성일자 : 2011-09-17
 * 최종수정일자 : 
 * 최종수정자 : 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRollPrhsCacu extends SnisActivity {
	public SaveRollPrhsCacu(){
		
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
        
        
        //String STND_YEAR,ROLL_GBN,END_GBN
        
        String sStndYear 	= (String) ctx.get("STND_YEAR");
        String sRollGbn 	= (String) ctx.get("ROLL_GBN");
        String sBrncCd 		= (String) ctx.get("BRNC_CD");
        String sUnitPrice	= (String)ctx.get("UNIT_PRICE");
        
        String sCfmYn 		= (String) ctx.get("CFM_YN");
        
        //01 : 생성, 02 : 확정, 03: 확정취소, 04: 수정
        String sJobGbn 		= (String) ctx.get("JOB_GBN");
        if(sJobGbn == null )sJobGbn = "";
        
        
        if(sJobGbn.equals("01")){	// 구매수량 산출 신규생성
        	deleteRollPrhs(sStndYear, sRollGbn);
        	
        	nSaveCount = insertRollPrhsCacu(sStndYear, sRollGbn, sUnitPrice);
        	
        	
        	
        }else if(sJobGbn.equals("02") || sJobGbn.equals("03") ){	//구매수량 산출 확정/ 확정취소
        	
        	nSaveCount = updateRollPrhsCacuCfm(sStndYear, sRollGbn, sBrncCd, sCfmYn);
        	
        	
        }else if(sJobGbn.equals("04")){	//구매수량 산출 내역 수정 
        	
            sDsName = "dsRollPrhsCacu";
            
            if ( ctx.get(sDsName) != null ) {
    	        ds   		 = (PosDataset) ctx.get(sDsName);
    	        nSize 		 = ds.getRecordCount();

    	        for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);

    	            nSaveCount = updateRollPrhsCacu(record);
    	            
    	        }	        
            }
        	
        }
        

      
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


   
    /**
     * <p> 구매수량 산출  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertRollPrhsCacu(String sStndYear, String sRollGbn, String sUnitPrice) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
         
        param.setValueParamter(i++, sStndYear);		//기준년도
        param.setValueParamter(i++, sRollGbn);		//용지구분
        param.setValueParamter(i++, sUnitPrice);		//기준년도
        param.setValueParamter(i++, sUnitPrice);		//용지구분
        param.setValueParamter(i++, sStndYear);		//용지구분
        
        param.setValueParamter(i++, sRollGbn);								//용지구분
        param.setValueParamter(i++, sStndYear);								//용지구분
        param.setValueParamter(i++, sRollGbn);						//작성자
        param.setValueParamter(i++, sStndYear);						//작성자
        param.setValueParamter(i++, sRollGbn);						//작성자
        param.setValueParamter(i++, sStndYear);						//작성자
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 구매수량 산출 수정  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRollPrhsCacu(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("INC_DEC_RATE"));	//증감율
        param.setValueParamter(i++, record.getAttribute("NECE_CNT"));		//소요량
        param.setValueParamter(i++, record.getAttribute("QUAR_USGE_CNT"));	//1/4사용량
        param.setValueParamter(i++, record.getAttribute("MAKE_CNT"));		//제작수량
        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));		//단가
        param.setValueParamter(i++, record.getAttribute("NECE_BUDG"));		//소요예산
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));		//수정자

        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//기준년도
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));		//용지구분
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 구매수량산출 확정  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateRollPrhsCacuCfm(String sStndYear, String sRollGbn, String sBrncCd, String sCfmYn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sCfmYn );
        param.setValueParamter(i++, SESSION_USER_ID );
        
        
        param.setValueParamter(i++, sStndYear );
        param.setValueParamter(i++, sRollGbn );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_u02", param);

        return dmlcount;
    }
    
    /**
     * <p> 구매수량산출 삭제  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRollPrhs(String sStndYear, String sRollGbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        
        param.setValueParamter(i++, sStndYear );
        param.setValueParamter(i++, sRollGbn );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_d01", param);

        return dmlcount;
    }
}
