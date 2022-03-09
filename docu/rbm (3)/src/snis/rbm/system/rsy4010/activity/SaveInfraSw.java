/*================================================================================
 * 시스템			: 응용시스템  관리
 * 소스파일 이름	: snis.rbm.business.rsy4010.activity.SaveInfraSw.java
 * 파일설명		: 응용시스템  관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.rbm.system.rsy4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveInfraSw extends SnisActivity {
	
	public SaveInfraSw(){
		
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

        sDsName = "dsInfraSw";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateInfraSw(record)) == 0 ) {
		        		nTempCnt = insertInfraSw(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraSw(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 인프라 SW 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertInfraSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SW_NM"));			//SW명
        param.setValueParamter(i++, record.getAttribute("BUILD_YEAR"));		//구축년도
        param.setValueParamter(i++, record.getAttribute("BUILD_AMT"));		//구축비용
        param.setValueParamter(i++, record.getAttribute("DEV_COMP"));		//개발업체
        param.setValueParamter(i++, record.getAttribute("SYS_DESC"));		//시스템 설명
        
        param.setValueParamter(i++, record.getAttribute("MANAGER"));		//관리자
        param.setValueParamter(i++, record.getAttribute("DEV_LANG"));		//개발언어
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));		//유지보수담당자
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));	//유지보수담당자연락처
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));		//제품명
        
        param.setValueParamter(i++, record.getAttribute("VER_INFO"));		//버전정보
        param.setValueParamter(i++, record.getAttribute("ETC"));			//기타
        param.setValueParamter(i++, SESSION_USER_ID);        				//작성자
  
        
        int dmlcount = this.getDao("rbmdao").update("rsy4010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 인프라 SW 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateInfraSw(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("SW_NM"));			//SW명
        param.setValueParamter(i++, record.getAttribute("BUILD_YEAR"));		//구축년도
        param.setValueParamter(i++, record.getAttribute("BUILD_AMT"));		//구축비용
        param.setValueParamter(i++, record.getAttribute("DEV_COMP"));		//개발업체
        param.setValueParamter(i++, record.getAttribute("SYS_DESC"));		//시스템 설명
        param.setValueParamter(i++, record.getAttribute("MANAGER"));		//관리자
        
        param.setValueParamter(i++, record.getAttribute("DEV_LANG"));		//개발언어
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));		//유지보수담당자
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));	//유지보수담당자연락처
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));		//제품명
        param.setValueParamter(i++, record.getAttribute("VER_INFO"));		//버전정보
        
        param.setValueParamter(i++, record.getAttribute("ETC"));			//기타
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SW_DIST_NO" ));

        int dmlcount = this.getDao("rbmdao").update("rsy4010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 인프라 SW  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteInfraSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"      ) );	//SW고유번호
        
        int dmlcount = this.getDao("rbmdao").update("rsy4010_d01", param);

        return dmlcount;
    }
}
