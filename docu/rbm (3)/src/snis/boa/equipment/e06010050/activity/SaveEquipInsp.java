/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquipInsp.java
 * 파일설명		: 모터보트 확정검사 결과를 저장한다. 
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-22
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010050.activity;


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
* 모터 보트 확정검사 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveEquipInsp extends SnisActivity
{    
    public SaveEquipInsp()
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
        //상세 현황 저장
        saveDetailState(ctx);
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
        
        ds    = (PosDataset) ctx.get("dsOutEquipInsp");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipInsp(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipInsp(record);
            }
        }

        String inspPrsStatCd = (String)ctx.get("INSP_PRS_STAT_CD");
        String orgInspPrsStatCd = (String)ctx.get("ORG_INSP_PRS_STAT_CD");
        
    	//프로세스 진행 현황 로그(장비확정검사-완료/취소)
        if(!orgInspPrsStatCd.equals(inspPrsStatCd)) {
        	if(Util.trim(inspPrsStatCd).equals("002")){	// 장비확정검사 확정
        		HashMap hmProcess = new HashMap();
        		hmProcess.put("STND_YEAR", (String)ctx.get("STND_YEAR"));
        		hmProcess.put("MBR_CD"   , (String)ctx.get("MBR_CD"));
        		hmProcess.put("TMS"      , (String)ctx.get("TMS"));
        		hmProcess.put("DAY_ORD"  , (String)ctx.get("DAY_ORD"));
        		hmProcess.put("DUTY_CD"  , "006");
        		hmProcess.put("WORK_CD"  , "030");
        		hmProcess.put("PROG_STAT", "001");
        		hmProcess.put("USER_ID",   SESSION_USER_ID);
        		
        		SaveProcess sp = new SaveProcess();
        		sp.saveProcess(hmProcess);		
        	} else if(Util.trim(inspPrsStatCd).equals("001")){	// 장비확정검사 확정 취소
        		HashMap hmProcess = new HashMap();
        		hmProcess.put("STND_YEAR", (String)ctx.get("STND_YEAR"));
        		hmProcess.put("MBR_CD"   , (String)ctx.get("MBR_CD"));
        		hmProcess.put("TMS"      , (String)ctx.get("TMS"));
        		hmProcess.put("DAY_ORD"  , (String)ctx.get("DAY_ORD"));
        		hmProcess.put("DUTY_CD"  , "006");
        		hmProcess.put("WORK_CD"  , "030");
        		hmProcess.put("PROG_STAT", "002");
        		hmProcess.put("USER_ID",   SESSION_USER_ID);
	
        		SaveProcess sp = new SaveProcess();
        		sp.saveProcess(hmProcess);		
        	}
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  none
     * @throws  none
     */
     protected void saveDetailState(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	//int nDeleteCount = 0; 

     	PosDataset ds;
         int nSize        = 0;
         
         ds    = (PosDataset) ctx.get("dsOutEquipInspDetail");
         nSize = ds.getRecordCount();

         for ( int i = 0; i < nSize; i++ ){
             PosRecord record = ds.getRecord(i);
             nSaveCount = nSaveCount + mergeEquipInspDetail(record);
         }

         Util.setSaveCount  (ctx, nSaveCount     );
         //Util.setDeleteCount(ctx, nDeleteCount   );
     }

     /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipInsp(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateEquipInsp(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertEquipInsp(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p>확정검사 결과 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipInsp(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_RACER_CNT")); 	
        param.setValueParamter(i++, record.getAttribute("ALTER_MOT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_BOAT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RACER_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_MOT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_BOAT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_TOT_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 

        int dmlCount = this.getDao("boadao").update("tbef_equip_insp_uf001", param);

        /* -- 저장시에 계속 취소 프로세스가 생성되어 주석 처리 2008.07.25, 강민수 saveState로 로직 이동
        //프로세스 진행 현황 로그(장비확정검사-완료/취소)
        if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("002")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "001");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        } else if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("001")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "002");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        }
        */
        
        return dmlCount;
    }

    /**
     * <p>확정검사 결과 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertEquipInsp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_PRS_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_RACER_CNT"));  
        param.setValueParamter(i++, record.getAttribute("ALTER_MOT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("ALTER_BOAT_CNT")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RACER_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_MOT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_BOAT_RMK")); 
        param.setValueParamter(i++, record.getAttribute("INSP_TOT_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlCount =  this.getDao("boadao").update("tbef_equip_insp_if001", param);
        
        /* -- 저장시에 계속 취소 프로세스가 생성되어 주석 처리 2008.07.25, 강민수 saveState로 로직 이동
        //프로세스 진행 현황 로그(장비확정검사-완료)
        if(Util.trim(record.getAttribute("INSP_PRS_STAT_CD")).equals("002")){
	        HashMap hmProcess = new HashMap();
	        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
	        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
	        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
	        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
	        hmProcess.put("DUTY_CD"  , "006");
	        hmProcess.put("WORK_CD"  , "030");
	        hmProcess.put("PROG_STAT", "001");
	
	        SaveProcess sp = new SaveProcess();
	        sp.saveProcess(hmProcess);		
        }
        */
        
        return dmlCount;
    }

    /**
     * <p>확정검사 결과 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteEquipInsp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        
        int dmlCount =  this.getDao("boadao").update("tbef_equip_insp_df001", param);

        /* -- 저장시에 계속 취소 프로세스가 생성되어 주석 처리 2008.07.25, 강민수 saveState로 로직 이동
        //프로세스 진행 현황 로그(장비확정검사-취소)
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
        hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
        hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
        hmProcess.put("DAY_ORD"  , record.getAttribute("DAY_ORD"  ));
        hmProcess.put("DUTY_CD"  , "006");
        hmProcess.put("WORK_CD"  , "030");
        hmProcess.put("PROG_STAT", "002");

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);		
        */
        
        return dmlCount;
    }

    /**
     * <p>확정검사 결과 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeEquipInspDetail(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("TMS")); 	
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 
        param.setValueParamter(i++, record.getAttribute("SEQ")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NO")); 
        param.setValueParamter(i++, record.getAttribute("MOT_NO")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_NO")); 
        param.setValueParamter(i++, record.getAttribute("MOT_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_REG_NO")); 
        param.setValueParamter(i++, record.getAttribute("RACER_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("MOT_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("BOAT_INSP_STAT_CD")); 
        param.setValueParamter(i++, record.getAttribute("INSP_RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS")); 	
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")); 

        int dmlCount = this.getDao("boadao").update("tbef_equip_insp_detail_mf001", param);
        
        return dmlCount;
    }
   
}
