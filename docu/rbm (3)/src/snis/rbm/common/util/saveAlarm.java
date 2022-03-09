/*================================================================================
 * 시스템			: 공통
 * 소스파일 이름	: snis.rbm.common.util.saveAlarm.java
 * 파일설명		: 확정관리 정보 저장
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-31
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.common.util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 착순점/사고점를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class saveAlarm extends SnisActivity
{    
    public saveAlarm()
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

        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        sDsName = "dsAlarm";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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

        sDsName = "dsAlarm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (i==0) {
	            	nDeleteCount = deleteAlarm(record);
	            }

		        if (((String)record.getAttribute("CHK")).equals("1")) {
		        	nTempCnt = insertAlarm(ctx, record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    
    /**
     * <p> 확정관리 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertAlarm(PosContext ctx, PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, (String)ctx.get("ALARM_CD"));
        param.setValueParamter(i++, record.getAttribute("RECV_ID"));
        param.setValueParamter(i++, (String)ctx.get("SUBJECT"));
        param.setValueParamter(i++, (String)ctx.get("CONTENT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
                        
        int dmlcount = this.getDao("rbmdao").insert("common_Alarm_List_Insert", param);
        
        return dmlcount;
    }
       
    
    /**
     * <p> 확정관리 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteAlarm(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ALARM_CD" ));
        
        int dmlcount = this.getDao("rbmdao").delete("common_Alarm_List_Delete", param);
        
        return dmlcount;
    }
           
}

