/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040050.activity.SearchRaceConfirm.java
 * 파일설명		: 편성결과조회
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.organization.e02040050.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class SearchRaceConfirm extends SnisActivity
{    
	
    public SearchRaceConfirm()
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
    	int i =0;
    	PosRowSet rowsetRaces = null;
        String organType = (String)ctx.get("ORGAN_TYPE");
        
        //ORGAN_TYPE - NEW REAL TEMP
        //SAVE_TYPE - CONFIRM SAVE_TO_ORGAN SAVE
        
        // 편성결과조회
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRaces = searchTempOrgan(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRaces = searchOrgan(ctx);
        }
        
    	PosColumnDef[] columnDef = rowsetRaces.getColumnDefs();
        PosRow rowRaces[] = rowsetRaces.getAllRow();

        // 편성이 이루어 졌다면
		String sRaceNo  = "0";
    	if ( rowsetRaces.hasNext() ) {
        	List   raceList = new java.util.ArrayList();

    		for ( int j = 0; j < rowRaces.length; j++ ) {
    			PosRow rowRace = rowRaces[j];
    			
    			// 각 경주의 정번에 해당 선수 배정
    			if ( sRaceNo.equals(rowRace.getAttribute("RACE_NO")) ) {
					raceList.add(rowRace);
    			} else {
    				// 경주번호가 변경된 경우 해당 선수로 해당 경주 Dataset생성
    				if ( !sRaceNo.equals("0") ) {
    			    	PosRowSet rowsetRace = new PosRowSetImpl(raceList);
    			    	rowsetRace.setColumnDefs(columnDef);

    			    	String sResultKey = "dsOutRace_" + Integer.parseInt(sRaceNo);
    		            ctx.put(sResultKey, rowsetRace);
    		            Util.addResultKey(ctx, sResultKey);
    				}

    				sRaceNo = (String) rowRace.getAttribute("RACE_NO");
    				raceList = new java.util.ArrayList();
					raceList.add(rowRace);
    			}
    		}

    		// 마지막 경주 설정
	    	PosRowSet rowsetRace = new PosRowSetImpl(raceList);
	    	rowsetRace.setColumnDefs(columnDef);

	    	String sResultKey = "dsOutRace_" + Integer.parseInt(sRaceNo);
            ctx.put(sResultKey, rowsetRace);
            Util.addResultKey(ctx, sResultKey);
    	}

        // 경주 조회
    	PosParameter param = new PosParameter();
    	i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);

        PosRow rows[] = rowset.getAllRow();
        
        // 경주마다 비어있는 정번을 찾아서 정번에 해당하는 recode생성
    	if ( rowset.hasNext() ) {
        	for ( int k = 1; k <= rows.length; k++ ) {
        		
            	String sTempRaceNo   =  (String)     rows[k - 1].getAttribute("RACE_NO");
            	int    nRaceRegNoCnt = ((BigDecimal) rows[k - 1].getAttribute("RACE_REG_NO_CNT")).intValue();
            	
            	PosRowSet rowsetRace = null;
            	if ( ctx.get("dsOutRace_" + k) != null ) {
            		try {
            			rowsetRace = (PosRowSet) ctx.get("dsOutRace_" + k);
            		} catch ( Exception e ) {
        	        	List rowList = new java.util.ArrayList();
        	        	for ( int j = 0; j < nRaceRegNoCnt; j++ ) {
        	        		addRaceRegNo(rowList, sTempRaceNo, j + 1);
        	        	}
            	    	rowsetRace = new PosRowSetImpl(rowList);
            	    	rowsetRace.setColumnDefs(columnDef);

            	    	String sResultKey = "dsOutRace_" + k;
                        ctx.put(sResultKey, rowsetRace);
                        Util.addResultKey(ctx, sResultKey);
                        continue;
            		}
            		
            		PosRow temprows[] = rowsetRace.getAllRow();
            		if ( temprows.length == nRaceRegNoCnt) continue;
            		
            		int nRaceRegNo = 1;
            		int nRow       = 0;
    	        	List rowList = new java.util.ArrayList();
            		while(nRaceRegNo <= nRaceRegNoCnt) {
            			if ( temprows.length <= nRow ) {
        	        		addRaceRegNo(rowList, sTempRaceNo, nRaceRegNo);
            			} else if ( nRaceRegNo == Integer.parseInt((String) temprows[nRow].getAttribute("RACE_REG_NO"))) {
            				rowList.add(temprows[nRow]);
            				nRow++;
            			} else {
        	        		addRaceRegNo(rowList, sTempRaceNo, nRaceRegNo);
            			}
            			nRaceRegNo++;
            		}
            		
        	    	rowsetRace = new PosRowSetImpl(rowList);
        	    	rowsetRace.setColumnDefs(columnDef);

        	    	String sResultKey = "dsOutRace_" + k;
                    ctx.put(sResultKey, rowsetRace);
                    Util.addResultKey(ctx, sResultKey);
            	} else {
    	        	List rowList = new java.util.ArrayList();
    	        	for ( int j = 0; j < nRaceRegNoCnt; j++ ) {
    	        		addRaceRegNo(rowList, sTempRaceNo, j + 1);
    	        	}
        	    	rowsetRace = new PosRowSetImpl(rowList);
        	    	rowsetRace.setColumnDefs(columnDef);

        	    	String sResultKey = "dsOutRace_" + k;
                    ctx.put(sResultKey, rowsetRace);
                    Util.addResultKey(ctx, sResultKey);
            	}
        	}
    	}
    	
        // 주선선수 조회
    	PosRowSet rowsetRacer = null;
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRacer = searchTempOrganAlloc(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRacer = searchOrganAlloc(ctx);
        }

        // 주선선수중 배정되지 않은 선수 Dataset생성
        PosRow rowRacer[] = rowsetRacer.getAllRow();
        
    	List   racerList = new java.util.ArrayList();
    	for ( int j = 0; j < rowRacer.length; j++ ) {
    		int    nAllocCnt = ((BigDecimal) rowRacer[j].getAttribute("RACE_ALLOC_CNT")).intValue();
			for ( int k = 0; k < nAllocCnt; k++ ) {
				racerList.add(rowRacer[j]);
			}
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
        
        String sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
        
        // 편성 최종저장자 조회
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	searchTempOrganLastUpdate(ctx);
        }else if("REAL".equals(organType)){
        	searchOrganLastUpdate(ctx);
        }
        
    }
    
    /**
     * <p> 경주에 정번생성 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected void addRaceRegNo(List raceList, String sRaceNo, int nRaceRegNo) 
    {
    	if ( sRaceNo.equals("0") ) sRaceNo = "01";
    		
    	Map rowMap = new HashMap();
		rowMap.put("RACE_NO"    , sRaceNo);
		rowMap.put("RACE_REG_NO", Integer.toString(nRaceRegNo));
    	PosRow row = new PosRowImpl(rowMap);
    	raceList.add(row);
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
