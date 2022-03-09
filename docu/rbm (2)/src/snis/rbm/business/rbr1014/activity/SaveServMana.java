/*================================================================================
 * 시스템			: 용역 관리
 * 소스파일 이름	: snis.rbm.business.rbr1014.activity.SaveServMana.java
 * 파일설명		: 용역 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-18
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1014.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
//import com.posdata.glue.dao.vo.PosRow;
//import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveServMana extends SnisActivity {
	
	public SaveServMana(){}

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
    	int nDeleteCount   = 0; 
    	String sDsName     = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsServMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//기본키 중복체크
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectServCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {
			            		
			            		String sServCd    = (String)record.getAttribute("SERV_CD");
			            		String sJobTitCd  = (String)record.getAttribute("JOB_TIT_CD");
			            		String sWorkTpyCd = (String)record.getAttribute("WORK_TPY_CD");

			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "[ " + selectNm("1", sServCd)    + " ]" +
			            							"[ " + selectNm("2", sWorkTpyCd) + " ]" +
			            							"[ " + selectNm("3", sJobTitCd)  + " ]" + "(은)는 중복되는 값입니다.");

			            		return;
			            	}
		        		}
		        	}
		        	
		        	nTempCnt   = saveHistMana(record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteHistMana(record);	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 용역 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//년도
        param.setValueParamter(i++, record.getAttribute("SERV_CD"));		//용역구분코드
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//직책코드
        param.setValueParamter(i++, record.getAttribute("WORK_TPY_CD"));	//근무형태코드
        
        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));		//인원수
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고        
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(수정자)

        int dmlcount = this.getDao("rbmdao").update("rbr1014_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 용역 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//년도
        param.setValueParamter(i++, record.getAttribute("SERV_CD"));		//용역구분코드
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//직책코드
        
        int dmlcount = this.getDao("rbmdao").update("rbr1014_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 용역관리 기본키 개수 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int selectServCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));     //지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   //년도
        param.setWhereClauseParameter(i++, record.getAttribute("SERV_CD"));     //용역구분코드
        param.setWhereClauseParameter(i++, record.getAttribute("JOB_TIT_CD"));  //직책코드
        param.setWhereClauseParameter(i++, record.getAttribute("WORK_TPY_CD")); //근무형태코드
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1014_s02", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> 코드 이름 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String selectNm(String sGrpCd, String sCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sGrpCd);     //GRP_CD
        param.setWhereClauseParameter(i++, sCd);        //CD
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1014_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CD_NM"));
        
        return rtnCnt;
    }
}
