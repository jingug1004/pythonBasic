/*================================================================================
 * 시스템			: 장비 관리
 * 소스파일 이름	: snis.rbm.system.rsy0010.activity.SaveEquipMana.java
 * 파일설명		: 장비 관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-08-20
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.rbm.business.rbr2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEquipMana extends SnisActivity {
	
	public SaveEquipMana(){
		
		
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

        sDsName = "dsEquipMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateEquipMana(record)) == 0 ) {
		        		nTempCnt = insertEquipMana(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteEquipMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 장비  입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertEquipMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));	//자료구분코드
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));	//자료구분코드
        param.setValueParamter(i++, record.getAttribute("FLOOR_CD"));	//층구분

        param.setValueParamter(i++, record.getAttribute("DATA_A"));	//자료 A
        param.setValueParamter(i++, record.getAttribute("DATA_B"));	//자료 B
        param.setValueParamter(i++, record.getAttribute("DATA_C"));	//자료 C
        param.setValueParamter(i++, record.getAttribute("DATA_D"));	//자료 D
        param.setValueParamter(i++, record.getAttribute("DATA_E"));	//자료 E
        param.setValueParamter(i++, record.getAttribute("DATA_F"));	//자료 F
        param.setValueParamter(i++, record.getAttribute("DATA_G"));	//자료 G
        param.setValueParamter(i++, record.getAttribute("DATA_H"));	//자료 H
        param.setValueParamter(i++, record.getAttribute("DATA_I"));	//자료 I
        param.setValueParamter(i++, record.getAttribute("DATA_J"));	//자료 J
        param.setValueParamter(i++, record.getAttribute("DATA_K"));	//자료 K

        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);					//작성

        
        int dmlcount = this.getDao("rbmdao").update("rbr2010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 장비  수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateEquipMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("FLOOR_CD"));//층구분
        param.setValueParamter(i++, record.getAttribute("DATA_A"));	//자료 A
        param.setValueParamter(i++, record.getAttribute("DATA_B"));	//자료 B
        param.setValueParamter(i++, record.getAttribute("DATA_C"));	//자료 C
        param.setValueParamter(i++, record.getAttribute("DATA_D"));	//자료 D
        param.setValueParamter(i++, record.getAttribute("DATA_E"));	//자료 E
        param.setValueParamter(i++, record.getAttribute("DATA_F"));	//자료 F
        param.setValueParamter(i++, record.getAttribute("DATA_G"));	//자료 G
        param.setValueParamter(i++, record.getAttribute("DATA_H"));	//자료 H
        param.setValueParamter(i++, record.getAttribute("DATA_I"));	//자료 I
        param.setValueParamter(i++, record.getAttribute("DATA_J"));	//자료 J
        param.setValueParamter(i++, record.getAttribute("DATA_K"));	//자료 K
        param.setValueParamter(i++, record.getAttribute("RMK"));		//비고
        param.setValueParamter(i++, SESSION_USER_ID);      				//수정자
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//지점코드
        param.setWhereClauseParameter(i++, record.getAttribute("TYPE_CD"));	//자료분류코드
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		//순번

        int dmlcount = this.getDao("rbmdao").update("rbr2010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> 장비  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteEquipMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("TYPE_CD"));		//자료분류코드
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//순번
        
        
        int dmlcount = this.getDao("rbmdao").update("rbr2010_d01", param);

        return dmlcount;
    }
}
