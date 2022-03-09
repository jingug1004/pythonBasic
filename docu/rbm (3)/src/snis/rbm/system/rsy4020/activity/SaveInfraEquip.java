package snis.rbm.system.rsy4020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveInfraEquip extends SnisActivity {
	
	public SaveInfraEquip(){
		
		
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

        
        //인프라 장비
        sDsName = "dsInfraEquip";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveInfraEquip(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraEquip(record);	            	
	            }		        
	        }	        
        }

        
        
        //인프라 장비 SW 매핑        
        sDsName = "dsInfraMapp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveInfraEquipSw(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraEquipSw(record);	            	
	            }		        
	        }	        
        }
        
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


  
    /**
     * <p> 인프라 장비  입력/수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveInfraEquip(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        
        param.setValueParamter(i++, record.getAttribute("DIST_NO"));	    //고유번호
        param.setValueParamter(i++, record.getAttribute("TPE_1"));	     	//대분류
        param.setValueParamter(i++, record.getAttribute("TPE_2"));	     	//중분류
        param.setValueParamter(i++, record.getAttribute("EQUIP_NM"));	    //장비명
        param.setValueParamter(i++, record.getAttribute("EQUIP_ID"));	    //장비ID

        param.setValueParamter(i++, record.getAttribute("INSTALL_LOC"));    //설치위치
        param.setValueParamter(i++, record.getAttribute("DTL_LOC"));	    //세부위치
        param.setValueParamter(i++, record.getAttribute("ADOPT_DT"));	    //도입일자
        param.setValueParamter(i++, record.getAttribute("PRHS_AMT"));	    //구입금액
        param.setValueParamter(i++, record.getAttribute("MODEL_NM"));	    //모델명

        param.setValueParamter(i++, record.getAttribute("DTL_SPEC"));	    //세부사양
        param.setValueParamter(i++, record.getAttribute("CPU"));	     	//CPU
        param.setValueParamter(i++, record.getAttribute("Memory"));	     	//memory
        param.setValueParamter(i++, record.getAttribute("HDD"));	     	//HDD
        param.setValueParamter(i++, record.getAttribute("OS_TYP"));	     	//OSType

        param.setValueParamter(i++, record.getAttribute("OS"));		     	//OS
        param.setValueParamter(i++, record.getAttribute("IN_IP"));	     	//내부IP
        param.setValueParamter(i++, record.getAttribute("OUT_IP"));	    	//외부IP
        param.setValueParamter(i++, record.getAttribute("ETC_IP"));	     	//기타IP
        param.setValueParamter(i++, record.getAttribute("CONN_TYP"));	    //접속형태

        param.setValueParamter(i++, record.getAttribute("CONSOLE_ID"));	    //콘솔유저
        param.setValueParamter(i++, record.getAttribute("CONSOLE_PSWD"));   //콘솔암호
        param.setValueParamter(i++, record.getAttribute("WEB_ID"));	     	//웹접근유저
        param.setValueParamter(i++, record.getAttribute("WEB_PSWD"));	    //웹접근암호
        param.setValueParamter(i++, record.getAttribute("EQUIP_STR_WAY"));  //장비시작방법

        param.setValueParamter(i++, record.getAttribute("EQUIP_END_WAY"));   //장비종료방법
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));	     //유지보수담당자
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));  //유지보수담당자연락처
        param.setValueParamter(i++, record.getAttribute("ETC"));	     	//기타
        param.setValueParamter(i++, record.getAttribute("INST_ID"));	    //작성자
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));	    //수정자




        int dmlcount = this.getDao("rbmdao").update("rsy4020_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 인프라장비  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteInfraEquip(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DIST_NO"      ) );
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 인프라장비SW매핑 입력/ 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveInfraEquipSw(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          

        param.setValueParamter(i++, record.getAttribute("DIST_NO"));		//고유번호
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"));		//SW고유번호
        param.setValueParamter(i++, record.getAttribute("RMK"));			//기타
        param.setValueParamter(i++, SESSION_USER_ID);						//작성
        param.setValueParamter(i++, SESSION_USER_ID);						//수정자
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_u02", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 인프라장비SW매핑  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteInfraEquipSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("DIST_NO"      ) );			//고유번호
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"      ) );		//SW고유번호
        
        int dmlcount = this.getDao("rbmdao").update("rsy4020_d02", param);

        return dmlcount;
    }
}
