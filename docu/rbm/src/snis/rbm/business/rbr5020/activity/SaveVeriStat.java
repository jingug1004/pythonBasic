/*================================================================================
 * 시스템		: 출주표 검수내역 저장
 * 소스파일 이름: snis.rbm.business.rbr5030.activity.SaveOrganExamBrnc.java
 * 파일설명		: SaveOrganExamBrnc
 * 작성자		: 조성문
 * 버전			: 1.0.0
 * 생성일자		: 2016-03-24
 * 최종수정일자	: 
 * 최종수정자	: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveVeriStat extends SnisActivity {
	
	public SaveVeriStat(){}
 
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
    	int nSize        = 0;
    	int nTempCnt	 = 0;
    	String sDsName   = "";
    	
    	PosDataset ds;
        
        sDsName = "dsVeriProcStat";
       
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		        	
		        	nTempCnt = saveOrganExamVeri(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }

        Util.setSaveCount  (ctx, nSaveCount  );

    }
    
    /**
     * <p> 출주표 검수 확정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveOrganExamVeri(PosRecord record) 
    {
    	PosParameter param = new PosParameter();   
        
        int i = 0;

        String sMeetCd = (String)record.getAttribute("MEET_CD");
        
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(작성자)
        param.setValueParamter(i++, record.getAttribute("CFM_VALUE"));		//확정여부
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//기준년도
        param.setValueParamter(i++, record.getAttribute("STND_MM"));		//기준월                
      
        int dmlcount = 0;
        
        if("003".equals(sMeetCd)) dmlcount = this.getDao("rbmdao").update("rbr5020_m02", param); //경정관리자
        else dmlcount = this.getDao("rbmdao").update("rbr5020_m01", param);						//경륜관리자
        
        return dmlcount;
       
    }
    
}