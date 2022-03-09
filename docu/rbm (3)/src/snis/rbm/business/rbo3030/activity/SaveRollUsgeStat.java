/*
 * ================================================================================
 * 시스템 : 용지마감 소스
 * 파일 이름 : snis.rbm.business.rbo3030.activity.SaveRollUsgeStat.java 
 * 파일설명 : 용지마감  관리 
 * 작성자 : 김은정
 * 버전 : 1.0.0 생성일자 : 2011- 09 - 16
 * 최종수정일자 : 
 * 최종수정자 : 
 * 최종수정내용 :
 * =================================================================================
 */
package snis.rbm.business.rbo3030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRollUsgeStat extends SnisActivity {
	public SaveRollUsgeStat(){
		
		
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
    	int nSaveCount     = 0;
    	int nDeleteCount     = 0; 
    	String sDsName     = "";
    	
    	PosRowSet ds;

        int nSize        = 0;
        int nTempCnt     = 0;
     
        
        //String STND_YEAR,ROLL_GBN,END_GBN
        
        String sStndYear = (String) ctx.get("STND_YEAR");
        String sRollGbn = (String) ctx.get("ROLL_GBN");
        String sEndGbn = (String) ctx.get("END_GBN");
        
        if(sEndGbn != null && !sEndGbn.equals("")){
        	if(sEndGbn.equals("Y")){ // 마감
        		 getRollEnd(ctx);
        		
        		 sDsName = "dsRollEnd";
  
    	        if ( ctx.get(sDsName) != null ) {
    	        	ds   		 = (PosRowSet) ctx.get(sDsName);
    		        nSize 		 = ds.count();

    				PosRow pr[] = ds.getAllRow();
    				PosRow record ;
    		        for ( int i = 0; i < nSize; i++ ) {
    		            record = pr[i];
    		           
    		        	nTempCnt = saveRollEnd(record);
    			        
    			        nSaveCount = nSaveCount + nTempCnt;
    		        	continue;
    	        
    		        }	        
    	        }
        	}else if(sEndGbn.equals("N")){ //마감취소 
        		nSaveCount = deleteRollEnd(sStndYear,sRollGbn);
        	}
        	
        }
      
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> 용지마감 취소(삭제) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteRollEnd(String sStndYear, String sRollGbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sStndYear );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3030_u02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> 용지마감 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveRollEnd(PosRow record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
     
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//기준년도
		
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));			//용지종류
		param.setValueParamter(i++, record.getAttribute("END_STK_CNT"));		//마감재고수량
		
		   
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID
        param.setValueParamter(i++, SESSION_USER_ID);							//사용자ID

        int dmlcount = this.getDao("rbmdao").update("rbo3030_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 마감대상 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	private void getRollEnd(PosContext ctx)  {
		
        String sStndYear = (String) ctx.get("STND_YEAR");        
        
		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sStndYear);
		param.setWhereClauseParameter(iParam++, sStndYear);
		param.setWhereClauseParameter(iParam++, SESSION_USER_ID);
		param.setWhereClauseParameter(iParam++, SESSION_USER_ID);
		
        

		PosRowSet prs = null;

	
		prs = this.getDao("rbmdao").find("rbo3030_s05",param);

		ctx.put("dsRollEnd", prs);


	}

}
