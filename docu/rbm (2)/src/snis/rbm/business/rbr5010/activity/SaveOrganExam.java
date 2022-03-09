/*================================================================================
 * 시스템			: 사건  관리
 * 소스파일 이름	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * 파일설명		: 사건 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-07
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr5010.activity;

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

public class SaveOrganExam extends SnisActivity {
	
	public SaveOrganExam(){}
 
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
        
        sDsName = "dsOrganExamDelete";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	        	PosRecord record = ds.getRecord(i);
	            nDeleteCount = nDeleteCount + deleteOrganExam(record);
	        }	
        }
        
        sDsName = "dsOrganExamSave";
        if ( ctx.get(sDsName) != null ) {
	        ds   	   = (PosDataset) ctx.get(sDsName);
	        nSaveCount = saveOrganExam(ds);
        }

        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> 출주표 제작수량 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteOrganExam(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//기준년도
        param.setValueParamter(i++, record.getAttribute("STND_MM"));	//기준월
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//장소
        param.setValueParamter(i++, record.getAttribute("ORGAN_CD"));   //출주표코드

        int dmlcount = this.getDao("rbmdao").update("rbr5010_d01", param);
        
        return dmlcount;
    }
    

    /**
     * <p> 출주표 제작수량 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveOrganExam(PosDataset ds) 
    {
    	
        int nSize 		= ds.getRecordCount();
        int intResult 	= 0;
        String[] arrQuery = new String[nSize];
        
        RbmJdbcDao rbmjdbcdao = getRbmDao("rbmjdbcdao");
        for ( int i = 0; i < nSize; i++ ) {
        	PosRecord record = ds.getRecord(i);
        	arrQuery[i] = " " +
        			"      INSERT INTO TBRA_ORGAN_EXAM   /* rbo5010_i01 출주표 제작수량 입력*/ \n"+
        			"      ( 								\n"+
        			"              STND_YEAR -- 기준년도 	\n"+
        			"            , STND_MM   -- 기준월 		\n"+
        			"            , DIST_DT   -- 배포일자 	\n"+
        			"            , MEET_CD   -- 장소		\n"+	      
        			"            , BRNC_CD   -- 지점코드 	\n"+
        			"            , ORGAN_CD  -- 출주표 구분 \n"+                 
        			"            , DIST_CNT  -- 배포수량 	\n"+
        			"            , INST_ID   -- 작성자ID 	\n"+
        			"            , INST_DT   -- 작성일자 	\n"+
        			"      ) 								\n"+
        			"      VALUES							\n"+
        			"      (								\n"+
        			"      	  '"+record.getAttribute("STND_YEAR")+"'\n"+
        			"       , '"+record.getAttribute("STND_MM")	+"'\n"+
        			"      	, '"+record.getAttribute("DIST_DT")	+"'\n"+
					"      	, '"+record.getAttribute("MEET_CD")	+"'\n"+
					"      	, '"+record.getAttribute("BRNC_CD")	+"'\n"+
					"      	, '"+record.getAttribute("ORGAN_CD")	+"'\n"+
					"      	, '"+record.getAttribute("DIST_CNT")	+"'\n"+
					"       , '"+SESSION_USER_ID					+"'\n"+
			    	"       , SYSDATE							  \n"+
			    	"      ) ";

        }
        int[] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		int failure_count = 0;

		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				failure_count++;
			}
		}
		
		if (failure_count == 0) {
			intResult = ds.getRecordCount();
		} else {
			intResult = 0;
		}
        
		return intResult;
       
    }
    
}