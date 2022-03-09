/*================================================================================
 * 시스템			: 소모품관리 
 * 소스파일 이름	: snis.boa.equipment.e06050010.activity.SaveSuppCdList.java
 * 파일설명		: 소모품코드를 저장한다. 
 * 작성자			: 신재선 
 * 버전			: 1.0.0
 * 생성일자		: 2014-06-28
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06050010.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* 부품 출납일보를 조회한다. 
* @auther 유재은 
* @version 1.0
*/
public class SaveSuppCdList extends SnisActivity
{    
    public SaveSuppCdList()
    {
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
        saveState(ctx);
        
    	return PosBizControlConstants.SUCCESS;
    }

    /**
    *  <p> 소모품 코드 저장 </p>
    *  <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
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

        sDsName = "dsSuppCdList";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {	            	
		        	nDeleteCount = nDeleteCount + deleteSuppCdList(record);	   
		        	
	            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	            	
	        		nTempCnt = updateSuppCdList(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        }
	        }	        
        }

        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    
    /**
     * <p> 소모품코드 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateSuppCdList(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("SAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("boadao").update("e06050010_m01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 소모품코드 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteSuppCdList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SUPP_CD" ));
        
        int dmlcount = this.getDao("boadao").update("e06050010_d01", param);
        return dmlcount;
    }
    
}

