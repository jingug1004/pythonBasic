/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.racer.e02040055.activity.SearchRacerVerify.java
 * 파일설명		: 출주표검증조회
 * 작성자			: 김영철
 * 버전			: 1.0.0
 * 생성일자		: 2007-12-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040055.activity;

import java.util.ArrayList;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 출주표검증조회하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRacerVerify extends SnisActivity
{    
	
    public SearchRacerVerify()
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
    
    	// 기수 리스트 조회
        PosRowSet rowsetPerid = this.getDao("boadao").find("tbeb_racer_perio_no_reg_term_fb003");    
        
    	/* IWORK_SFR-008 경정 선수편성 메뉴 개선 수정 */
    	String organType = (String)ctx.get("ORGAN_TYPE");
    	
    	// 출주표검증 조회
        PosRowSet rowset = null;
        PosRowSet rowsetPeridCnt = null;       
        
        if("TEMP".equals(organType)){
        	
            // 출주표검증 조회
        	PosParameter param = new PosParameter();
            
            int i = 0;
            // 년도별 정번수
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            // 회차별 정번수
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));            
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowset = this.getDao("boadao").find("tbeb_temp_organ_fb003", param);    

            // 기수별 출주횟수 조회
        	PosParameter paramPeridCnt = new PosParameter();
            
            i = 0;
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowsetPeridCnt = this.getDao("boadao").find("tbeb_temp_organ_fb004", paramPeridCnt); 
            
        }else if("REAL".equals(organType)){
            // 출주표검증 조회
        	PosParameter param = new PosParameter();
            
            int i = 0;
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowset = this.getDao("boadao").find("tbeb_organ_fb010", param);    

            // 기수별 출주횟수 조회
        	PosParameter paramPeridCnt = new PosParameter();
            
            i = 0;
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowsetPeridCnt = this.getDao("boadao").find("tbeb_organ_fb011", paramPeridCnt); 
        }

        PosRow peridCnts[] = rowsetPeridCnt.getAllRow();

        // column정의
        PosRow rowPerid[] = rowsetPerid.getAllRow();
        PosColumnDef columnDefTemp[] = rowset.getColumnDefs();
        
        PosColumnDef columnDef[] = new PosColumnDef[columnDefTemp.length + rowPerid.length];
        
        for ( int j = 0; j < columnDefTemp.length; j++ ) {
        	columnDef[j] = columnDefTemp[j];
        }
        
        for ( int j = columnDefTemp.length, k = 0; j < columnDefTemp.length + rowPerid.length; j++ ) {
        	columnDef[j] = new PosColumnDef("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), 12, 2);
        	k++;
        }

        // 기수별 출주횟수 입력
        PosRow datarows[] = rowset.getAllRow();
        boolean isSet = false;
	    for ( int j = 0; j < datarows.length; j++ ) {
	    	for ( int k = 0; k < rowPerid.length; k++ ) {
	    		isSet = false;
	    		for(int m = 0; m < peridCnts.length; m++){
	    			if(datarows[j].getAttribute("RACE_NO").equals(peridCnts[m].getAttribute("RACE_NO")) 
	    					&& k+1 == Integer.parseInt((String)peridCnts[m].getAttribute("RACER_PERIO_NO"))){
	    				datarows[j].setAttribute("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), peridCnts[m].getAttribute("CNT"));
	    				isSet = true;
	    				break;
	    			}
	    		}
	    		if(!isSet) datarows[j].setAttribute("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), "0");
	    	}
	    }

        // Dataset 생성
		ArrayList alRows  = new ArrayList();
        for ( int j = 0; j < datarows.length; j++ ) {
        	alRows.add(datarows[j]);
        }
        
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);

		rowset.setColumnDefs(columnDef);

        String sResultKey = "dsOutRaceVerify";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
        
        // 편성 최종저장자 조회
        if("TEMP".equals(organType)){
        	searchTempOrganLastUpdate(ctx);
        }else if("REAL".equals(organType)){
        	searchOrganLastUpdate(ctx);
        }

    }
    
    /**
     * <p> 가편성 최종 저장자정보조회 </p>
     * @param ctx
     */
    protected void searchTempOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_temp_organ_fb005", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }  
    
    /**
     * <p> 편성 최종 저장자정보조회 </p>
     * @param ctx
     */    
    protected void searchOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_organ_fb027", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }      
}
