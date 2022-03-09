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
package snis.rbm.business.rbr5030.activity;

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

public class SaveOrganExamBrnc extends SnisActivity {
	
	public SaveOrganExamBrnc(){}
 
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
        
        sDsName = "dsOrganExamBrnc";
        if ( ctx.get(sDsName) != null ) {
	        ds   	   = (PosDataset) ctx.get(sDsName);
	        nSaveCount = saveOrganExamBrnc(ds);
        }

        Util.setSaveCount  (ctx, nSaveCount  );

    }
    
    /**
     * <p> 출주표 제작수량 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveOrganExamBrnc(PosDataset ds) 
    {
        int nSize 		= ds.getRecordCount();
        int intResult 	= 0;
        String[] arrQuery = new String[nSize];
        
        RbmJdbcDao rbmjdbcdao = getRbmDao("rbmjdbcdao");
        for ( int i = 0; i < nSize; i++ ) {
        	PosRecord record = ds.getRecord(i);
        	arrQuery[i] = " " +
        			"   UPDATE TBRA_ORGAN_EXAM   /* rbo5010_i01 출주표 검수내역 저장*/ \n"+
        			"	SET VERI_YN 	= '"+record.getAttribute("VERI_YN")+"'  \n"+
        			"	WHERE MEET_CD 	= '"+record.getAttribute("MEET_CD")+"'  \n" +
        			"	AND STND_YEAR 	= '"+record.getAttribute("STND_YEAR")+"'  \n" +
        			"	AND STND_MM 	= '"+record.getAttribute("STND_MM")+"'  \n" +
        			"	AND DIST_DT 	= '"+record.getAttribute("DIST_DT")+"'  \n" +
        			"	AND BRNC_CD 	= '"+record.getAttribute("BRNC_CD")+"'  \n" +
        			"	AND ORGAN_CD 	= '"+record.getAttribute("ORGAN_CD")+"'  \n";
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