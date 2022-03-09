/*================================================================================
 * 시스템			: 직원현황 
 * 소스파일 이름	: snis.rbm.business.rbr1013.activity.SaveEmpStat.java
 * 파일설명		: 기존에 존재하는 올해의 직원현황 자료를 삭제하고, 새로 Insert해준다. 
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1013.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
//import com.posdata.glue.dao.vo.PosRow;
//import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEmpStat extends SnisActivity {
	
	public SaveEmpStat(){}

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
    	
    	getEmpStatCurr(ctx);
    	
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
    	String sDsName     = "";
    	
    	PosRowSet ds;

        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sBrncCd   = (String)ctx.get("BRNC_CD");
        String sStndYear = (String)ctx.get("STND_YEAR");
        
        //기존 데이터 삭제
        deleteEmpStat(sBrncCd, sStndYear);

        sDsName = "dsEmpStat";
               
        if ( ctx.get(sDsName) != null ) {
        	ds   		 = (PosRowSet) ctx.get(sDsName);
	        nSize 		 = ds.count();
	        


			PosRow pr[] = ds.getAllRow();
			PosRow record ;
	        for ( int i = 0; i < nSize; i++ ) {
	            record = pr[i];
		        	
	        	nTempCnt = saveEmpStat(record, sBrncCd, sStndYear);
		        nSaveCount = nSaveCount + nTempCnt;
	        	continue;
        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 직원 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveEmpStat(PosRow record, String sBrncCd, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sBrncCd);								//지점코드
        param.setValueParamter(i++, sStndYear);								//년도
        param.setValueParamter(i++, sBrncCd);								//순번(where : 1.지점코드)
        param.setValueParamter(i++, record.getAttribute("JOB_GRP_NM"));		//직책코드
        param.setValueParamter(i++, record.getAttribute("WORK_GBN_NM"));	//근무형태코드
		
        param.setValueParamter(i++, record.getAttribute("JOB_NM"));			//직무명
		param.setValueParamter(i++, record.getAttribute("CAP_NM"));			//직급명
		param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));	//보직명
		param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));		//인원수   
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID

        int dmlcount = this.getDao("rbmdao").update("rbr1013_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 기존의 존재하는 올해 직원현황 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEmpStat(String sBrncCd, String sStndYear) 
    {	
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, sBrncCd);      // 지점코드
        param.setValueParamter(i++, sStndYear);    // 현재년도
             
        int dmlcount = this.getDao("rbmdao").update("rbr1013_d01", param);

        return dmlcount;
    }
    
    
	private void getEmpStatCurr(PosContext ctx)  {
		String sSearchValue = Util.getCtxStr(ctx, "VW_BRNC_CD");
		

		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sSearchValue);

		PosRowSet prs = null;

	
		prs = this.getDao("venisrbmdao").find("rbr1013_s02",param);

		ctx.put("dsEmpStat", prs);


	}
}
