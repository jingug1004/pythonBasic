/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquipDrwlt.java
 * 파일설명		: 모터 /보트 추첨 진행 상태 (완료)를 저장한다. 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;


import java.util.HashMap;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;


/**
* 모터 보트등 장비에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveEquipDrwlt extends SnisActivity
{    
	public SaveEquipDrwlt()
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
    	//사용자 정보 확인
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutEquipDrwlt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
            saveEquipDrwlt(ctx, record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipDrwlt(PosContext ctx, PosRecord record)
    {
//    	String stndYear = (String)record.getAttribute("STND_YEAR");
//    	String mbrCd = (String)record.getAttribute("MBR_CD" );
//    	Integer tms =  new Integer(((Double)record.getAttribute("TMS")).intValue());
    	/*
    	//저장시점회 회사 상태가 주선/장비추첨 이상이면 저장시 오류 메시지 출력 
    	PosRowSet prs = (new SearchEquipDrwlt()).getEquipDrwltRwoSet(stndYear, mbrCd, tms);
    	if(prs.hasNext()){
    		PosRow[] pr = prs.getAllRow();
    		if(pr!= null && pr.length>0){
    			pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN");
	    		String equipDrwltCmplYn = (String)pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN");
	    		String raceTmsStatCd = (String)pr[0].getAttribute("RACE_TMS_STAT_CD");
	    		String raceTmsStatCdNm = (String)pr[0].getAttribute("RACE_TMS_STAT_CD_NM");
	    		if(!(raceTmsStatCd.equals("001")|| raceTmsStatCd.equals("010"))){
	    			throw new RuntimeException(stndYear +"년도 " + tms.intValue() +"회차 상태는  " + raceTmsStatCdNm + " 입니다.\n 모터/보트 추점대상을 변경할 수 없습니다");
	    		}
    		}
    	}
    	*/
    	return updateEquipDrwlt(ctx, record);
    }
			
    /**
     * <p> 장비  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipDrwlt(PosContext ctx, PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String equipDrwltCmplYn = (String)record.getAttribute("EQUIP_DRWLT_CMPL_YN");
        if(equipDrwltCmplYn.equals("X"))return 0;
        String sStndYear = (String)record.getAttribute("STND_YEAR");
        String sMbrCd    = (String)record.getAttribute("MBR_CD");
        String sTms      = ((Double)record.getAttribute("TMS")).toString();

        // 현재상태 체크
        String sStatCd = "";
        SaveProcess sp = new SaveProcess();        
        sStatCd = sp.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

		if(!"010".equals(sStatCd) && !"001".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "모터/보트배정등록이 완료되어 확정취소를 할 수 없습니다!!!");
        		return 0;        		
        	}			
		}

        Integer tms = new Integer(((Double)record.getAttribute("TMS")).intValue());
        param.setValueParamter(i++, equipDrwltCmplYn);
        param.setValueParamter(i++, "010"); //장비 추첨 대상 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sStndYear);      
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));   
        param.setValueParamter(i++, tms);
	        
        int effectedRow = 0;
        effectedRow = this.getDao("boadao").update("tbeb_race_tms_uf001", param);
       
        //프로세스 현황 로그
//        if(equipDrwltCmplYn.equals("Y")){
//        	String title = stndYear+"년 " + tms +"회차  장비추점 대상 선정 완료";
//            String content = stndYear+"년 " + tms +"회차  장비추점 대상 선정 완료 되었습니다";
//            new SaveProcessProg().insertProcessProg("006", stndYear, tms.toString(), "", title, content); 
//        }

        //프로세스 진행 현황 로그(장비배정-장비추첨대상확정-완료/취소)
        if(Util.trim(equipDrwltCmplYn).equals("Y")){
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
            hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
            hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
            hmProcess.put("DUTY_CD"  , "006");
            hmProcess.put("WORK_CD"  , "015");
            hmProcess.put("PROG_STAT", "001");
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            //SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
        }
        else if(Util.trim(equipDrwltCmplYn).equals("N")){
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
            hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
            hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
            hmProcess.put("DUTY_CD"  , "006");
            hmProcess.put("WORK_CD"  , "015");
            hmProcess.put("PROG_STAT", "002");
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            //SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
        }
        
        return effectedRow;
    }
}
