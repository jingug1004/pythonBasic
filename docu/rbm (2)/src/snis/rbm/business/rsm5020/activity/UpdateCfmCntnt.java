/*================================================================================
 * 시스템			: 지급조서내역
 * 소스파일 이름	: snis.rbm.business.rsm5020.activity.UpdateCfmCntnt
 * 파일설명		: 지급조서관리_확정내역 수정(확정된 사항을 미확정으로 수정할 수 있다)
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateCfmCntnt extends SnisActivity {
	
	public UpdateCfmCntnt(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsCfmCntnt";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if( i == 0 && getValidCnt(record) > 0 ) {
	            	try { 
            			throw new Exception(); 
	            	} catch(Exception e) {
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsgCode(ctx, "SNIS_RBM_D006");
	            		
	            		return;
	            	}
		        }
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	nTempCnt = updateCfmCntnt(record);
			        nSaveCount = nSaveCount + nTempCnt; 
	            }
	        }	        
        }       
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 지급조서관리_확정내역 수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int updateCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("FST_CFM_CD"));	//1차확정코드
        param.setValueParamter(i++, SESSION_USER_ID);

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD"));	 //경륜장코드
        param.setWhereClauseParameter(i++, record.getAttribute("SELL_CD"));	 //운영처코드
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR")); //지급년도
        // 광명은 여러개의 지점을 하나로 보기때문에 정규식으로 조건을 건...
        String strCommNo =  record.getAttribute("BRNC_CD" ).toString();        
        if( strCommNo.equals("00") || strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") ){
        	strCommNo = "^(0[123468])$";    	
        }   
        
        param.setWhereClauseParameter(i++, strCommNo);	 //발매지점번호
        param.setWhereClauseParameter(i++, strCommNo);	 //발매지점번호
        param.setWhereClauseParameter(i++, strCommNo);	 //발매지점번호

        int dmlcount = this.getDao("rbmdao").update("rsm5020_u01", param);

        return dmlcount;
    }
    
    /**
     * <p> 지급조서관리 검증여부 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			0 : 확정가능 0초과 : 확정불가능 
     * @throws  none
     */
    protected int getValidCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//지급년도      
        // 광명은 여러개의 지점을 하나로 보기때문에 정규식으로 조건을 건다.
        String strCommNo =  record.getAttribute("BRNC_CD" ).toString();        
        if( strCommNo.equals("00") || strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") ){
        	strCommNo = "^(0[12348])$";    	
        }     
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        param.setWhereClauseParameter(i++, strCommNo);  	//지점코드
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5020_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.parseInt(rtnKey);
    }
}