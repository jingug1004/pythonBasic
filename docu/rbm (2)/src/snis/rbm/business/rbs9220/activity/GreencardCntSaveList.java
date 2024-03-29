/*================================================================================
 * 시스템			: 지점별 전자카드 발급수
 * 소스파일 이름	: snis.rbm.business.rbS9310.activity.SaveList.java
 * 파일설명		: 지점별 전자카드 발급수
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-06-02
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbs9220.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class GreencardCntSaveList extends SnisActivity {
	
	public GreencardCntSaveList(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	int nDeleteCount = 0; 
    	
    	String sFlag = "N";	//메세지를 사용자에게 띄울지 말지를 정하는 Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
        String sDsName = "dsListMm";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
			        nSaveCount += saveList(record);			        
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);	            	
	            }	
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> 적발건수 저장 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	   	// 지점코드
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		// 발급년도
        param.setValueParamter(i++, record.getAttribute("STND_MM"));		// 발급월
        param.setValueParamter(i++, record.getAttribute("ISSUE_CNT"));		// 발급건수
        param.setValueParamter(i++, SESSION_USER_ID);					   	// 사용자ID(수정자)
        param.setValueParamter(i++, SESSION_USER_ID);					   	// 사용자ID(수정자)
                		
        int dmlcount = this.getDao("rbmdao").update("rbs9220_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 적발건수  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_MM" ));
        
        int dmlcount = this.getDao("rbmdao").update("rbs9220_d01", param);
		return dmlcount;
        
    }
}
