/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquipLot.java
 * 파일설명		: 모터 /보트 추첨 대상을 저장한다.  
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-01
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 모터 보트등 장비에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 김성희 
* @version 1.0
*/
public class SaveEquipLot extends SnisActivity
{    
	private String equipDrwltCmplYn  = "N";
    public SaveEquipLot()
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
    	saveEquipDrwlt(ctx);		//1. 상태값 변경
    	saveEquipStatLost(ctx);		//2. 확정장비 상태 삭제
        saveState(ctx);				//3. 자료 처리
        return PosBizControlConstants.SUCCESS;
    }
    
    
    //확정검사 장비 상태 삭제 
    public void saveEquipStatLost(PosContext ctx){
    	PosDataset ds    = (PosDataset) ctx.get("dsOutEquipDrwlt");
        int nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            equipDrwltCmplYn  = (String)record.getAttribute("EQUIP_DRWLT_CMPL_YN");
            if(equipDrwltCmplYn.equals("Y")) deleteEquipLotStat(record);
        }
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	saveMotLot( ctx);			//3.1 모터 추첨대상 저장
    	saveBoatLot( ctx);			//3.2 보트 추첨대상 저장
    	savePropellerLot(ctx);		//3.3 프로펠라 추첨대상 저장
    }
    
    /**
     * 3.1 모터 추첨 현황
     * @param ctx
     */
    protected void saveMotLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutMotLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            String lot_yn = (String)record.getAttribute("LOT_YN")== null ? "" : (String)record.getAttribute("LOT_YN");
            String repare_yn = (String)record.getAttribute("PREPAR_YN")==null ? "" : (String)record.getAttribute("PREPAR_YN");
            String repare_add1_yn = (String)record.getAttribute("PREPAR_ADD1_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD1_YN");
            String repare_add2_yn = (String)record.getAttribute("PREPAR_ADD2_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD2_YN");
            if (lot_yn.equals("0") && repare_yn.equals("0") && repare_add1_yn.equals("0") && repare_add2_yn.equals("0")){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }
        //모터 장비 상태 저장 
        if(equipDrwltCmplYn.equals("Y"))saveEquipLotStat(ds);
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 3.2 보트 추첨 현황
     * @param ctx
     */
    protected void saveBoatLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutBoatLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            String lot_yn = (String)record.getAttribute("LOT_YN")== null ? "" : (String)record.getAttribute("LOT_YN");
            String repare_yn = (String)record.getAttribute("PREPAR_YN")==null ? "" : (String)record.getAttribute("PREPAR_YN");
            String repare_add1_yn = (String)record.getAttribute("PREPAR_ADD1_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD1_YN");
            String repare_add2_yn = (String)record.getAttribute("PREPAR_ADD2_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD2_YN");
            if (lot_yn.equals("0") && repare_yn.equals("0") && repare_add1_yn.equals("0") && repare_add2_yn.equals("0")){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }
        //보트  장비 상태 저장 
        if(equipDrwltCmplYn.equals("Y"))saveEquipLotStat(ds);
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 3.3 프로펠러 추첨 현황
     * @param ctx
     */
    protected void savePropellerLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPropellerLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 3.1.2 모터 보트 추첨 대상  저장  Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipLot(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeIntoEquipLot(record);
    	return effectedRowCnt;
    }

    
    /**
     * <p>3.1.2.1 장비 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int mergeIntoEquipLot(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String preparValue = "N";
    	String preparYn = (String)record.getAttribute("PREPAR_YN");
    	String preparAdd1Yn = (String)record.getAttribute("PREPAR_ADD1_YN");
    	String preparAdd2Yn = (String)record.getAttribute("PREPAR_ADD2_YN");
    	String lotOrd = "0";
    	if(preparYn != null && preparYn.equals("1"))preparValue = "Y";
    	if(preparAdd1Yn.equals("1"))lotOrd="1";
    	if(preparAdd2Yn.equals("1"))lotOrd="2";
    	if(preparAdd1Yn.equals("1")|| preparAdd2Yn.equals("1")) preparValue = "Y";
    	
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));
        param.setValueParamter(i++, preparValue);
        param.setValueParamter(i++, lotOrd);
        param.setValueParamter(i++, record.getAttribute("EXCL_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_equip_lot_mf001", param);
    }

    /**
     * <p>3.1.1 장비 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteEquipLot(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO" ));
        
        return  this.getDao("boadao").update("tbef_equip_lot_df001", param);
    }
    
    /**
     * 확정 여부 저장 
     * @param ctx
     * @return
     */
    protected String saveEquipDrwlt(PosContext ctx){
    	SaveEquipDrwlt equipDrwlt = new SaveEquipDrwlt();
    	return equipDrwlt.runActivity(ctx);
    }
   
    
    /**
     *  장비 상태 저장
     * @param ds
     */
    protected void saveEquipLotStat(PosDataset ds) 
    {
        int nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            insertEquipLotStat(record);
        }

    }
    
    /**
     * 2.1 장비상태 삭제 
     * @param record
     * @return
     */
    protected int deleteEquipLotStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        return  this.getDao("boadao").update("tbef_equip_lot_stat_df001", param);
    }
    
    
    /**
     * 3.1.3.1 장비 상태 등록
     * @param record
     * @return
     */
    protected int insertEquipLotStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_STAT_CD" ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return  this.getDao("boadao").update("tbef_equip_lot_stat_if001", param);
    }
}
