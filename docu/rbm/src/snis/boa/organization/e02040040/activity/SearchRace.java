/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040040.activity.SearchRace.java
 * 파일설명		: 편성조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040040.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 편성를 조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRace extends SnisActivity
{    
	
    public SearchRace()
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
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	/* IWORK_SFR-008 경정 선수편성 메뉴 개선 수정 */
    	
    	String organType = (String)ctx.get("ORGAN_TYPE");
    	
        // 편성결과조회
        PosRowSet rowsetRace = null;
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRace = searchTempOrgan(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRace = searchOrgan(ctx);
        }
        
        String sResultKey = "dsOutRace";
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);

        // 해당일차의 필요한 선수수를 조회한다.
        int racerTotalCnt = getRacerTotalCnt(ctx);

        if ( rowsetRace .getAllRow().length < racerTotalCnt ) {
        	
        	// 출주배정조회
        	PosRowSet rowsetRacer = null;
            if("NEW".equals(organType) || "TEMP".equals(organType)){
            	rowsetRacer = searchTempOrganAlloc(ctx);
            }else if("REAL".equals(organType)){
            	rowsetRacer = searchOrganAlloc(ctx);
            }        	
	
	    	PosColumnDef[] columnDef = rowsetRacer.getColumnDefs();
	        PosRow rowRaces[] = rowsetRace .getAllRow();
	        PosRow rowRacer[] = rowsetRacer.getAllRow();
	        
	        
	        // 편성에서 제외된 선수 리스트 조회

	    	List   racerList = new java.util.ArrayList();
	    	/*
	    	 * 2회 출전자 컬럼 갯수를 2개로 만들어 준다.
	    	for ( int j = 0; j < rowRacer.length; j++ ) {
	    		int    nAllocCnt = ((BigDecimal) rowRacer[j].getAttribute("RACE_ALLOC_CNT")).intValue();
				for ( int k = 0; k < nAllocCnt; k++ ) {
					racerList.add(rowRacer[j]);
				}
	    	}
	    	*/
	    	for ( int j = 0; j < rowRacer.length; j++ ) {
				racerList.add(rowRacer[j]);
	    	}
	
	    	for ( int j = 0; j < rowRaces.length; j++ ) {
	    		String sRacerNo  = (String) rowRaces[j].getAttribute("RACER_NO");
	    		
	        	for ( int k = 0; k < racerList.size(); k++ ) {
	        		if ( sRacerNo.equals((String)((PosRow) racerList.get(k)).getAttribute("RACER_NO")) ) {
	        			racerList.remove(k);
	        			break;
	        		}
	        	}
	    	}
	
	    	//row생성
	    	rowsetRacer = new PosRowSetImpl(racerList);
	    	rowsetRacer.setColumnDefs(columnDef);
	        
	        sResultKey = "dsOutRacer";
	        ctx.put(sResultKey, rowsetRacer);
	        Util.addResultKey(ctx, sResultKey);
        }
    }
    
    
    /**
     * <p> 편성정보조회 </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrgan(PosContext ctx){
    	
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ""                  );

        return this.getDao("boadao").find("tbeb_organ_fb004", paramRace);
    }
    
    /**
     * <p> 가편성정보조회 </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrgan(PosContext ctx){
    	
    	// 편성결과조회
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));

        
        // 전일성적 - 성적이 없을 경우 전일 경주번호,정번
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        // 정번별 출주수
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        // 추가
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ""                  );
        
        return this.getDao("boadao").find("tbeb_temp_organ_fb001", paramRace);    	

    }  
    
    /**
     * <p> 편성 주선 정보조회 </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrganAlloc(PosContext ctx){
    	
    	// 주선선수 조회
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb003", paramRacer);    	
    }
    
    /**
     * <p> 가편성 주선 정보조회 </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrganAlloc(PosContext ctx){
    	
    	// 주선선수 조회
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // 전일성적 - 성적이 없을 경우 전일 경주번호,정번
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));    	
    	
        // 정번별 출주수
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));

    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));           	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // 추가
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
    	// 추가
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
        // 추가
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb010", paramRacer);    	    	
    }    
    
    /**
     * <p> 해당경주의 총선수수 조회 정보조회 </p>
     * @param ctx
     * @return
     */
    protected int getRacerTotalCnt(PosContext ctx){
    	
    	int    racerCnt = 0;
    	
        // 경주 조회
    	PosParameter param = new PosParameter();
    	int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);
        PosRow raceInfos[] = rowset.getAllRow();     	

    	for ( int j = 0; j < raceInfos.length; j++ ) {
    		racerCnt += ((BigDecimal) raceInfos[j].getAttribute("RACE_REG_NO_CNT")).intValue();
    	}        
    	
    	return racerCnt;
    }
}

